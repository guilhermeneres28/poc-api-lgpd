package br.com.dev.lgpd.services.responses;

import lombok.Builder;

@Builder
public class CustomerResponse {
    private final String email;
    private final String name;
}
