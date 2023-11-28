package br.itau.saldotransferenciaservice.service;

import br.itau.saldotransferenciaservice.exception.ApiSaldoTransferenciaException;
import br.itau.saldotransferenciaservice.model.Conta;
import br.itau.saldotransferenciaservice.repository.ContaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    private Conta conta;

    @Before
    public void setUp() {
        this.conta = new Conta();
        this.conta.setCpfCliente("67677264000");
        this.conta.setNumeroContaCorrente("22430-5");
        this.conta.setSaldoAtual(Double.valueOf("100.00"));
    }

    @Test
    public void testDeveRecuperarContaPeloCpfClienteCorretamente() throws ApiSaldoTransferenciaException {
        when(contaRepository.findByCpfCliente(anyString())).thenReturn(Optional.of(this.conta));

        Conta contaRecuperada = contaService.buscarContaPeloCpfCliente("67677264000");

        Assert.assertNotNull(contaRecuperada);
        Assert.assertEquals("67677264000", contaRecuperada.getCpfCliente());
        Assert.assertEquals("22430-5", contaRecuperada.getNumeroContaCorrente());
        Assert.assertEquals(String.valueOf(100.00), contaRecuperada.getSaldoAtual().toString());
    }

    @Test
    public void testDeveAtualizarSaldoContaCorretamente() throws ApiSaldoTransferenciaException {
        when(contaRepository.saveAndFlush(any(Conta.class))).thenReturn((this.conta));

        Conta contaAtualizada = contaService.atualizarSaldoConta(this.conta);

        Assert.assertNotNull(contaAtualizada);
        Assert.assertEquals("67677264000", contaAtualizada.getCpfCliente());
        Assert.assertEquals("22430-5", contaAtualizada.getNumeroContaCorrente());
        Assert.assertEquals(String.valueOf(100.00), contaAtualizada.getSaldoAtual().toString());
    }
}
