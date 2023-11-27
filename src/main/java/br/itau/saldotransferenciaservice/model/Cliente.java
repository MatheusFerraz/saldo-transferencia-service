package br.itau.saldotransferenciaservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter @Setter
public class Cliente implements Serializable {

    public String cpf;
    public String nome;
    public String email;
    public String telefone;
    public String numeroContaCorrente;
    public int statusConta;
}
