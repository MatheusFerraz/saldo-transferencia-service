package br.itau.saldotransferenciaservice.repository;

import br.itau.saldotransferenciaservice.model.Conta;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaRepositories(basePackageClasses = ContaRepository.class)
@EntityScan(basePackageClasses = Conta.class)
public class ContaRepositoryTest {

    @Autowired
    private ContaRepository contaRepository;

    private Conta conta;

    @Before
    public void setUp() {
        this.conta = new Conta();
        this.conta.setCpfCliente("67677264000");
        this.conta.setNumeroContaCorrente("22430-5");
        this.conta.setSaldoAtual(Double.valueOf("100.00"));
    }

    @Test
    public void testDeveRecuperarContaPeloCpfClienteCorretamente() {
        contaRepository.save(this.conta);

        Conta contaRecuperada = contaRepository.findByCpfCliente("67677264000").get();

        Assert.assertNotNull(contaRecuperada);
        Assert.assertEquals("67677264000", contaRecuperada.getCpfCliente());
        Assert.assertEquals("22430-5", contaRecuperada.getNumeroContaCorrente());
        Assert.assertEquals(String.valueOf(100.00), contaRecuperada.getSaldoAtual().toString());
    }
}
