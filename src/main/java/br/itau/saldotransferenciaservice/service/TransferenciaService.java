package br.itau.saldotransferenciaservice.service;

import br.itau.saldotransferenciaservice.exception.ApiSaldoTransferenciaException;
import br.itau.saldotransferenciaservice.model.Transferencia;
import br.itau.saldotransferenciaservice.repository.TransferenciaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TransferenciaService {

    @Autowired
    TransferenciaRepository transferenciaRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransferenciaService.class);

    public List<Transferencia> obterTransferenciasPeloCpfEData(String cpfCliente, Date dataTransferencia) throws ApiSaldoTransferenciaException {

        try {
            List<Transferencia> listaTransferencias =
                    transferenciaRepository.findByCpfClienteAndDataTransferencia(cpfCliente, dataTransferencia);

            return listaTransferencias;
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao recuperar transferências: " + e.getMessage(), e);
            throw new ApiSaldoTransferenciaException("Ocorreu um erro ao recuperar transferências: " + e.getMessage());
        }
    }

    public Transferencia salvarTransferencia(Transferencia transferencia) throws ApiSaldoTransferenciaException {

        try {
            Transferencia transferenciaPersistida = transferenciaRepository.saveAndFlush(transferencia);

            return transferenciaPersistida;
        } catch (Exception e) {
            logger.error("Ocorreu um erro ao salvar dados da transferência: " + e.getMessage(), e);
            throw new ApiSaldoTransferenciaException("Ocorreu um erro ao salvar dados da transferência: " + e.getMessage());
        }
    }
}
