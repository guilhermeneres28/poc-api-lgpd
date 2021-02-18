package br.com.dev.lgpd.repositories;

import br.com.dev.lgpd.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRespository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);
}
