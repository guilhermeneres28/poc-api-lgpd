package br.com.dev.lgpd.services.requests;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class SensitiveRequest {
    @NotNull
    private Long id;
    @NotNull
    @NotBlank
    private String secretKey;
}
