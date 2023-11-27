package br.itau.saldotransferenciaservice.payload;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class RecepcaoRequest implements Serializable {

    public String hashTransacao;
    public Integer codigoBancoOrigem;
}
