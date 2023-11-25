package br.itau.saldotransferenciaservice.service;

import br.itau.saldotransferenciaservice.exception.ApiSaldoTransferenciaException;
import br.itau.saldotransferenciaservice.model.Conta;
import br.itau.saldotransferenciaservice.repository.ContaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ContaService.class);

    public Conta buscarContaPeloCpfCliente(String cpfCliente) throws ApiSaldoTransferenciaException {
        Conta conta = contaRepository.findByCpfCliente(cpfCliente)
                .orElseThrow(() -> new ApiSaldoTransferenciaException("Conta não encontrada a partir dos dados informados."));

        return conta;
    }

    public Conta atualizarSaldoConta(Conta conta) throws ApiSaldoTransferenciaException {

        try {
            Conta contaPersistida = contaRepository.saveAndFlush(conta);

            return contaPersistida;
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao atualizar dados da conta: " + e.getMessage(), e);
            throw new ApiSaldoTransferenciaException("Ocorreu um erro ao atualizar dados da conta: " + e.getMessage());
        }
    }
}
