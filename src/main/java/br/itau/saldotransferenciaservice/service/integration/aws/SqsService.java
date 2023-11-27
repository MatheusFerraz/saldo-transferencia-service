package br.itau.saldotransferenciaservice.service.integration.aws;

import br.itau.saldotransferenciaservice.payload.RecepcaoRequest;
import org.springframework.stereotype.Service;

@Service
public class SqsService {

    public void enviarMensagem(RecepcaoRequest recepcaoRequest) {
        // Aqui seria feita a chamada ao sqs client para envio da mensagem
    }
}
