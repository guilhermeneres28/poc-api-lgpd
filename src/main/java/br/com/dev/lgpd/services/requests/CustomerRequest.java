package br.com.dev.lgpd.services.requests;

import br.com.dev.lgpd.domain.enums.Gender;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CustomerRequest {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private Boolean isSubscribed;

    @NotNull
    private Gender gender;

    @NotNull
    @NotBlank
    private String email;
}
