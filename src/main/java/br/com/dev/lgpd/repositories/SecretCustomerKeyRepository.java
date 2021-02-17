package br.com.dev.lgpd.repositories;

import br.com.dev.lgpd.domain.entities.SecretCustomerKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecretCustomerKeyRepository extends JpaRepository<SecretCustomerKey, Long> {
}
