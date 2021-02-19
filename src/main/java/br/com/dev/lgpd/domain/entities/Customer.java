package br.com.dev.lgpd.domain.entities;

import br.com.dev.lgpd.domain.enums.Gender;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import lombok.Getter;

import javax.crypto.SecretKey;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static br.com.dev.lgpd.services.CryptographyService.*;
import static java.lang.Boolean.FALSE;

@Getter
@Entity(name = "TB_CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;
    private Integer age;
    private String email;
    private Boolean isSubscribed;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    protected Customer() {
    }

    private Customer(Gender gender, String name, Integer age, Boolean isSubscribed, String email) {
        this.gender = gender;
        this.name = name;
        this.age = age;
        this.isSubscribed = isSubscribed;
        this.createdAt = LocalDateTime.now();
        this.email = email;
    }

    public static Customer from(CustomerRequest customerRequest) {
        return new Customer(customerRequest.getGender(), customerRequest.getName(),
                customerRequest.getAge(), customerRequest.getIsSubscribed(), customerRequest.getEmail());
    }

    public LocalDateTime softDeleteCustomer(SecretKey secretKey) {
        LocalDateTime deletedDate = LocalDateTime.now();
        this.name = encrypt(name, secretKey);
        this.email = encrypt(email, secretKey);
        this.deletedAt = deletedDate;
        this.isSubscribed = FALSE;
        return deletedDate;
    }

    public Customer createCustomerWithDecryptSensitveData(String secretkey) {
        return new Customer(gender, decrypt(name, secretkey),
                age, isSubscribed, decrypt(email, secretkey));
    }
}
