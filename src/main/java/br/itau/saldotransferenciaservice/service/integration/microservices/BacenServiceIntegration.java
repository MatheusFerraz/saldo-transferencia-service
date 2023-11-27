package br.itau.saldotransferenciaservice.service.integration.microservices;

import br.itau.saldotransferenciaservice.model.Recepcao;
import br.itau.saldotransferenciaservice.payload.RecepcaoRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BacenServiceIntegration {

    @PostMapping(value = "/api/bacen")
    Recepcao persistirRecepcaoTransferencia(@RequestBody RecepcaoRequest recepcaoRequest);
}
