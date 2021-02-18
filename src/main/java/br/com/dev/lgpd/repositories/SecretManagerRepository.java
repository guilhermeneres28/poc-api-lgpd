package br.com.dev.lgpd.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretRequest;
import software.amazon.awssdk.services.secretsmanager.model.CreateSecretResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class SecretManagerRepository {

    public static String save(String secret, Long customerId) throws SecretsManagerException {
        try {
            String name = UUID.randomUUID().toString();
            CreateSecretRequest secretRequest = CreateSecretRequest.builder()
                    .name(name)
                    .description("Customer Secret Test")
                    .secretString(createSecretCustomerJson(customerId, secret))
                    .build();

            CreateSecretResponse response = createSecretManagerClient().createSecret(secretRequest);
            return response.arn();
        } catch (SecretsManagerException exception) {
            log.error("Error to save secretKey for customerId" + customerId);
            throw new RuntimeException(exception.awsErrorDetails().errorMessage());
        }
    }

    private static String createSecretCustomerJson(Long customerId, String secretKey) {
        Map<String, Object> secretMap = new HashMap<>();
        secretMap.put("customerId", customerId);
        secretMap.put("secretKey", secretKey);
        try {
            return new ObjectMapper().writer().writeValueAsString(secretMap);
        } catch (JsonProcessingException e) {
            log.error("Error parsing object map to json");
            throw new RuntimeException("Error parsing object map to json");
        }
    }

    private static SecretsManagerClient createSecretManagerClient() {
        return SecretsManagerClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
