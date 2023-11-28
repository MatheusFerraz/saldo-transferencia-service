package br.itau.saldotransferenciaservice.repository;

import br.itau.saldotransferenciaservice.model.Transferencia;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = TransferenciaRepository.class)
@EntityScan(basePackageClasses = Transferencia.class)
public class TransferenciaRepositoryTest {

    @Autowired
    private TransferenciaRepository transferenciaRepository;

    private Transferencia transferencia;
    private Date dataTransacao;

    @Before
    public void setUp() {
        this.dataTransacao = new Date();

        this.transferencia = new Transferencia();
        this.transferencia.setDataTransferencia(new Date());
        this.transferencia.setValorTransferencia(Double.valueOf("100.00"));
        this.transferencia.setIdentificadorBacenTransferencia("hash");
        this.transferencia.setNumeroContaDestino("22436-9");
        this.transferencia.setCpfCliente("67677264000");
    }

    @Test
    public void testDeveRecuperarListaTransacoesPeloCpfEDataCorretamente() {
        transferenciaRepository.save(this.transferencia);

        List<Transferencia> listaTransferencias =
                transferenciaRepository.findByCpfClienteAndDataTransferencia("67677264000", this.dataTransacao);

        Assert.assertNotNull(listaTransferencias);
        Assert.assertEquals(1, listaTransferencias.size());
    }
}
