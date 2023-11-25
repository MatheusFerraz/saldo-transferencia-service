package br.itau.saldotransferenciaservice.service;

import br.itau.saldotransferenciaservice.exception.ApiSaldoTransferenciaException;
import br.itau.saldotransferenciaservice.model.Cliente;
import br.itau.saldotransferenciaservice.model.Conta;
import br.itau.saldotransferenciaservice.model.Transferencia;
import br.itau.saldotransferenciaservice.service.integration.ClienteServiceIntegration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

@Service
public class EfetivadorTransferenciaService {

    @Autowired
    ContaService contaService;

    @Autowired
    TransferenciaService transferenciaService;

    @Autowired
    ClienteServiceIntegration clienteServiceIntegration;

    private static final Logger logger = LoggerFactory.getLogger(EfetivadorTransferenciaService.class);

    private static final String TRANSFERENCIA_OK = "Transferência concluída com sucesso!";

    public String efetuarTransferencia(String cpfCliente, Double valorTransferencia) throws ApiSaldoTransferenciaException {
        try {
            Cliente dadosCliente = clienteServiceIntegration.buscarClientePorCpf(cpfCliente);

            if (dadosCliente == null) {
                throw new ApiSaldoTransferenciaException("Dados do cliente não encontrados.");
            } else {
                if (dadosCliente.getCpf() == null || dadosCliente.getCpf().isEmpty()) {
                    throw new ApiSaldoTransferenciaException("Não será possível prosseguir com a transferência por falta de dados do cliente.");
                }

                Conta dadosContaCliente = contaService.buscarContaPeloCpfCliente(dadosCliente.getCpf());

                if (dadosCliente.statusConta == 0) {
                    throw new ApiSaldoTransferenciaException("A conta do cliente não está ativa.");
                }

                if (dadosContaCliente.getSaldoAtual() < valorTransferencia) {
                    throw new ApiSaldoTransferenciaException("Não será possível prosseguir com a transferência por insuficiência de saldo.");
                }

                Date dataAtual = new Date();

                List<Transferencia> listaTransferenciasDiaCliente =
                        transferenciaService.obterTransferenciasPeloCpfEData(dadosCliente.getCpf(), dataAtual);

                Double totalTransferenciasDia = 0.0;

                for (Transferencia transferencia : listaTransferenciasDiaCliente) {
                    totalTransferenciasDia += transferencia.getValorTransferencia();
                }

                if (totalTransferenciasDia >= 1000.00) {
                    throw new ApiSaldoTransferenciaException("Não será possível prosseguir com a transferência, pois o limite diário de R$ 1000,00 foi atingido.");
                }

                Transferencia transferenciaEfetivar =
                        preencherDadosTransferencia(dadosCliente, valorTransferencia, dataAtual);

                // Em um serviço real de integração poderia haver mais algum passo para efetivar a transferência
                Transferencia transferenciaRetornada = transferenciaService.salvarTransferencia(transferenciaEfetivar);

                if (transferenciaRetornada != null) {
                    Double saldoAtualizado = dadosContaCliente.getSaldoAtual() - valorTransferencia;

                    dadosContaCliente.setSaldoAtual(saldoAtualizado);
                    contaService.atualizarSaldoConta(dadosContaCliente);
                }

                return TRANSFERENCIA_OK;
            }
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao efetuar transferência bancária: " + e.getMessage(), e);
            throw new ApiSaldoTransferenciaException("Ocorreu um erro ao efetuar transferência bancária: " + e.getMessage());
        }
    }

    private Transferencia preencherDadosTransferencia(Cliente dadosCliente,
                                                      Double valorTransferencia,
                                                      Date dataTransferencia) throws NoSuchAlgorithmException {
        Transferencia transferencia = new Transferencia();

        transferencia.setValorTransferencia(valorTransferencia);
        transferencia.setCpfCliente(dadosCliente.getCpf());
        transferencia.setDataTransferencia(dataTransferencia);

        StringBuffer bufferIdentificador = new StringBuffer();
        bufferIdentificador.append(dadosCliente.getCpf());
        bufferIdentificador.append(dataTransferencia.toString());
        bufferIdentificador.append(valorTransferencia.toString());

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(bufferIdentificador.toString().getBytes(StandardCharsets.UTF_8));

        transferencia.setIdentificadorBacenTransferencia(messageDigest.toString());

        return transferencia;
    }
}
