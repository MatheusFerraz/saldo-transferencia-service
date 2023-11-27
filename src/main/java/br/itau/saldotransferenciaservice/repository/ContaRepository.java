package br.itau.saldotransferenciaservice.repository;

import br.itau.saldotransferenciaservice.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, String> {

    Optional<Conta> findByCpfCliente(String cpfCliente);
}
