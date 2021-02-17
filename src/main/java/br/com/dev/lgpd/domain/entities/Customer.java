package br.com.dev.lgpd.domain.entities;

import br.com.dev.lgpd.domain.enums.Gender;
import br.com.dev.lgpd.services.CryptographyService;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.SecretKey;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter @Setter
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
        this.gender = (Gender) gender;
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

    public void softDeleteCustomer(SecretKey secretKey) {
        this.name = CryptographyService.encrypt(name, secretKey);
        this.email = CryptographyService.encrypt(email, secretKey);
        this.updatedAt = LocalDateTime.now();
        this.deletedAt = LocalDateTime.now();
    }
}
