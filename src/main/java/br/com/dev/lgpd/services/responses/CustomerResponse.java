package br.com.dev.lgpd.services.responses;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerResponse {
    private final String email;
    private final String name;
}
