package br.com.dev.lgpd.services.responses;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerDeleteResponse {

    public static final String DELETE_ACCOUNT_MESSAGE = "Hope to see you soon, for future contact signup again";
    private LocalDateTime deletedAt;
    private String message;

    public CustomerDeleteResponse(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
        this.message = DELETE_ACCOUNT_MESSAGE;
    }
}
