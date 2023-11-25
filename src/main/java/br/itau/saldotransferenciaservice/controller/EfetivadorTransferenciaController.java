package br.itau.saldotransferenciaservice.controller;

import br.itau.saldotransferenciaservice.exception.ApiSaldoTransferenciaException;
import br.itau.saldotransferenciaservice.exception.EndpointException;
import br.itau.saldotransferenciaservice.payload.TransferenciaRequest;
import br.itau.saldotransferenciaservice.service.EfetivadorTransferenciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/transferencia")
public class EfetivadorTransferenciaController {

    @Autowired
    EfetivadorTransferenciaService efetivadorTransferenciaService;

    private static final Logger logger = LoggerFactory.getLogger(EfetivadorTransferenciaController.class);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> efetivarTransferencia(@RequestBody TransferenciaRequest transferenciaRequest) throws EndpointException {

        try {
            String resultadoTransferencia =
                    efetivadorTransferenciaService.efetuarTransferencia(
                            transferenciaRequest.getCpfCliente(),
                            transferenciaRequest.getValorTransferencia());

            return ResponseEntity.ok(resultadoTransferencia);
        } catch (ApiSaldoTransferenciaException e) {
            logger.error("Erro: " + e.getMessage(), e);
            throw new EndpointException("Erro: " + e.getMessage());
        }
    }
}
