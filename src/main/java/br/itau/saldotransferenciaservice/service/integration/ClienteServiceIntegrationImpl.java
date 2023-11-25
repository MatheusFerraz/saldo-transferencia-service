package br.itau.saldotransferenciaservice.service.integration;

import br.itau.saldotransferenciaservice.model.Cliente;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ClienteServiceIntegrationImpl implements ClienteServiceIntegration {

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "retornarClienteDefault")
    public Cliente buscarClientePorCpf(String cpfCliente) {
        return restTemplate.getForObject("http://CADASTRO-CLIENTE-SERVICE/api/cliente/{cpfCliente}", Cliente.class, cpfCliente);
    }

    private Cliente retornarClienteDefault(String cpfCliente) {
        return new Cliente();
    }
}
