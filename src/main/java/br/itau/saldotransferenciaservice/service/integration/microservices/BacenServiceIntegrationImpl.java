package br.itau.saldotransferenciaservice.service.integration.microservices;

import br.itau.saldotransferenciaservice.model.Recepcao;
import br.itau.saldotransferenciaservice.payload.RecepcaoRequest;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BacenServiceIntegrationImpl implements BacenServiceIntegration {

    @Autowired
    @LoadBalanced
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "retornarRecepcaoDefault")
    public Recepcao persistirRecepcaoTransferencia(RecepcaoRequest recepcaoRequest) {
        // A URL se encontra no código mas poderia ser recuperada de algum serviço tal como o SSM Parameter Store
        return restTemplate.postForObject("http://BACEN-SERVICE/api/bacen", recepcaoRequest, Recepcao.class);
    }

    private Recepcao retornarRecepcaoDefault(RecepcaoRequest recepcaoRequest) {
        return new Recepcao();
    }
}
