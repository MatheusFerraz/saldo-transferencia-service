package br.itau.saldotransferenciaservice.service.integration.microservices;

import br.itau.saldotransferenciaservice.model.Cliente;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ClienteServiceIntegration {

    @GetMapping(value = "/api/cliente/{cpfCliente}")
    Cliente buscarClientePorCpf(@PathVariable("cpfCliente") String cpfCliente);
}
