package br.itau.saldotransferenciaservice.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "TB_TRANSFERENCIA")
@Entity
@NoArgsConstructor
@Getter @Setter
public class Transferencia implements Serializable {

    @Id
    @Column(name = "CPF_CLIENTE")
    public String cpfCliente;

    @Column(name = "VALOR", precision = 10, scale = 2)
    public Double valorTransferencia;

    @Column(name = "DATA")
    @Temporal(TemporalType.DATE)
    public Date dataTransferencia;

    @Column(name = "IDENTIFICADOR_BACEN")
    public String identificadorBacenTransferencia;
}
