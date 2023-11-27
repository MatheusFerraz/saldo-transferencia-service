package br.itau.saldotransferenciaservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class Recepcao implements Serializable {

    public Integer identificadorRecepcao;
    public String hashTransacao;
    public Integer codigoBancoOrigem;
}
