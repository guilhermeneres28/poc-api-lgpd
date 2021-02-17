package br.com.dev.lgpd.domain.entities;

import br.com.dev.lgpd.domain.enums.Gender;
import br.com.dev.lgpd.services.requests.CustomerRequest;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "TB_CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String name;
    private Integer age;
    private Boolean isSubscribed;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    protected Customer() {
    }

    private Customer(Gender gender, String name, Integer age, Boolean isSubscribed) {
        this.gender = (Gender) gender;
        this.name = name;
        this.age = age;
        this.isSubscribed = isSubscribed;
        this.createdAt = LocalDateTime.now();
    }

    public static Customer from(CustomerRequest customerRequest) {
        return new Customer(customerRequest.getGender(), customerRequest.getName(),
                customerRequest.getAge(), customerRequest.getIsSubscribed());
    }
}
