package br.com.dev.lgpd.services;

import br.com.dev.lgpd.domain.entities.Customer;
import br.com.dev.lgpd.domain.entities.SecretCustomerKey;
import br.com.dev.lgpd.repositories.CustomerRespository;
import br.com.dev.lgpd.repositories.SecretCustomerKeyRepository;
import br.com.dev.lgpd.services.exceptions.CryptographyServiceException;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import br.com.dev.lgpd.services.responses.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Optional;

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
    public void delete(Long id) throws CryptographyServiceException {
        Customer customer = findCustomerById(id);

        SecretKey secretKey = CryptographyService.generateKey();

        customer.softDeleteCustomer(secretKey);

        saveSecretCustomerKey(customer, secretKey);
    }

    private void saveSecretCustomerKey(Customer customer, SecretKey secretKey) {
        String secretKeyText = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        SecretCustomerKey secretCustomerKey = SecretCustomerKey.create(customer.getId(), secretKeyText);
        secretKeyCustomerRepository.save(secretCustomerKey);
    }

    private Customer findCustomerById(Long id) {
        Optional<Customer> possibleCustomer = customerRespository.findById(id);
        if(possibleCustomer.isEmpty()) {
            throw new RuntimeException("Could not find customer");
        }
        return possibleCustomer.get();
    }

    public CustomerResponse createCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .email(customer.getEmail())
                .name(customer.getEmail())
                .build();
    }
}
