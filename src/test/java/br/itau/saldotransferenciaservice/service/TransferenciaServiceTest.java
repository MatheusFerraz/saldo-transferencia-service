package br.itau.saldotransferenciaservice.service;

import br.itau.saldotransferenciaservice.exception.ApiSaldoTransferenciaException;
import br.itau.saldotransferenciaservice.model.Transferencia;
import br.itau.saldotransferenciaservice.repository.TransferenciaRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransferenciaServiceTest {

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @InjectMocks
    private TransferenciaService transferenciaService;

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
    public void testDeveRecuperarListaTransacoesPeloCpfEDataCorretamente() throws ApiSaldoTransferenciaException {
        List<Transferencia> listaTransferenciasMock = Arrays.asList(this.transferencia);

        when(transferenciaRepository.findByCpfClienteAndDataTransferencia(anyString(), any(Date.class)))
                .thenReturn(listaTransferenciasMock);

        List<Transferencia> listaTransferenciasRecuperada =
                transferenciaService.obterTransferenciasPeloCpfEData("67677264000", this.dataTransacao);

        Assert.assertNotNull(listaTransferenciasRecuperada);
        Assert.assertEquals(1, listaTransferenciasRecuperada.size());
    }

    @Test
    public void testDevePersistirTransferenciaCorretamente() throws ApiSaldoTransferenciaException {
        when(transferenciaRepository.saveAndFlush(any(Transferencia.class))).thenReturn(this.transferencia);

        Transferencia transferenciaPersistida = transferenciaService.salvarTransferencia(this.transferencia);

        Assert.assertNotNull(transferenciaPersistida);
        Assert.assertEquals("67677264000", transferenciaPersistida.getCpfCliente());
    }
}
