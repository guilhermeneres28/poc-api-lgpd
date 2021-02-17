package br.com.dev.lgpd.services;

import br.com.dev.lgpd.domain.entities.Customer;
import br.com.dev.lgpd.repositories.CustomerRespository;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import br.com.dev.lgpd.services.responses.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private CustomerRespository customerRespository;

    public CustomerResponse save(CustomerRequest customerRequest){
        Customer customer = Customer.from(customerRequest);
        Customer response = customerRespository.save(customer);
        return null;
    }
}
