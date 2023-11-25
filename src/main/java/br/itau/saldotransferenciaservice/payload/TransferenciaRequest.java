package br.itau.saldotransferenciaservice.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class TransferenciaRequest implements Serializable {

    public String cpfCliente;
    public Double valorTransferencia;
    public String numeroContaDestino;
}
