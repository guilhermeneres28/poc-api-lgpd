package br.com.dev.lgpd.services;

import br.com.dev.lgpd.domain.entities.Customer;
import br.com.dev.lgpd.domain.entities.SecretCustomerKey;
import br.com.dev.lgpd.repositories.CustomerRespository;
import br.com.dev.lgpd.repositories.SecretCustomerKeyRepository;
import br.com.dev.lgpd.repositories.SecretManagerRepository;
import br.com.dev.lgpd.services.exceptions.CryptographyServiceException;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import br.com.dev.lgpd.services.responses.CustomerDeleteResponse;
import br.com.dev.lgpd.services.responses.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static br.com.dev.lgpd.services.CryptographyService.convertSecretKey;
import static br.com.dev.lgpd.services.CryptographyService.generateSecretKey;

@Service
@AllArgsConstructor(onConstructor_={@Autowired})
public class CustomerService {

    private final CustomerRespository customerRespository;
    private final SecretCustomerKeyRepository secretKeyCustomerRepository;

    @Transactional
    public CustomerResponse save(CustomerRequest customerRequest){
        Customer customer = Customer.from(customerRequest);
        Customer response = customerRespository.save(customer);
        return createCustomerResponse(response);
    }

    @Transactional
    public CustomerDeleteResponse delete(Long id) throws CryptographyServiceException {
        Customer customer = findCustomerById(id);

        SecretKey secretKey = generateSecretKey();

        LocalDateTime deletedAt = customer.softDeleteCustomer(secretKey);

        saveSecretCustomerKey(customer, secretKey);

        String awsRn = SecretManagerRepository.save(convertSecretKey(secretKey), customer.getId());
        return createCustomerDeleteResponse(deletedAt, awsRn);
    }

    private void saveSecretCustomerKey(Customer customer, SecretKey secretKey) {
        String secretKeyText = convertSecretKey(secretKey);
        SecretCustomerKey secretCustomerKey = SecretCustomerKey.create(customer.getId(), secretKeyText);
        secretKeyCustomerRepository.save(secretCustomerKey);
    }

    public Customer findByEmail(String email) {
        Optional<Customer> possibleCustomer = customerRespository.findByEmail(email);
        if(possibleCustomer.isEmpty()) {
            throw new RuntimeException("Could not find customer");
        }
        return possibleCustomer.get();
    }

    public Customer findCustomerById(Long id) {
        Optional<Customer> possibleCustomer = customerRespository.findById(id);
        if(possibleCustomer.isEmpty()) {
            throw new RuntimeException("Could not find customer");
        }
        return possibleCustomer.get();
    }

    private CustomerResponse createCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .email(customer.getEmail())
                .name(customer.getName())
                .build();
    }

    public CustomerDeleteResponse createCustomerDeleteResponse(LocalDateTime deleteAt, String awsRn) {
        return new CustomerDeleteResponse(deleteAt, awsRn);
    }
}
