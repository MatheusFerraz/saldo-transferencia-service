package br.itau.saldotransferenciaservice.repository;

import br.itau.saldotransferenciaservice.model.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, String> {

    List<Transferencia> findByCpfClienteAndDataTransferencia(String cpfCliente, Date dataTransferencia);
}
