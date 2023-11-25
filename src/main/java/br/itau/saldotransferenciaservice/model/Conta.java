package br.itau.saldotransferenciaservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "TB_CONTA")
@Entity
@NoArgsConstructor
@Getter @Setter
public class Conta implements Serializable {

    @Id
    @Column(name = "CPF_CLIENTE")
    public String cpfCliente;

    @Column(name = "NR_CONTA_CORRENTE")
    public String numeroContaCorrente;

    @Column(name = "SALDO_ATUAL", precision = 10, scale = 2)
    public Double saldoAtual;
}
