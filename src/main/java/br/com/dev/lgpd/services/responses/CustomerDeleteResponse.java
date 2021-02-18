package br.com.dev.lgpd.services.responses;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerDeleteResponse {

    public static final String DELETE_ACCOUNT_MESSAGE = "Hope to see you soon, for future contact signup again";
    private LocalDateTime deletedAt;
    private String message;
    private String awsRn;

    public CustomerDeleteResponse(LocalDateTime deletedAt, String awsRn) {
        this.deletedAt = deletedAt;
        this.message = DELETE_ACCOUNT_MESSAGE;
        this.awsRn = awsRn;
    }
}
