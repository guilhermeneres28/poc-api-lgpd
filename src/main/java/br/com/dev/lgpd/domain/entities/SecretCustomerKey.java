package br.com.dev.lgpd.domain.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class SecretCustomerKey {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long customerId;
    private String secretKey;
    private LocalDateTime createdAt;

    protected SecretCustomerKey() {
    }

    private SecretCustomerKey(Long customerId, String secretKey) {
        this.customerId = customerId;
        this.secretKey = secretKey;
        this.createdAt = LocalDateTime.now();
    }

    public static SecretCustomerKey create(Long customerId, String secret) {
        return new SecretCustomerKey(customerId, secret);
    }
}
