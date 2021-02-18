package br.com.dev.lgpd.controllers;

import br.com.dev.lgpd.domain.entities.Customer;
import br.com.dev.lgpd.services.CustomerService;
import br.com.dev.lgpd.services.exceptions.CryptographyServiceException;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import br.com.dev.lgpd.services.responses.CustomerDeleteResponse;
import br.com.dev.lgpd.services.responses.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
@AllArgsConstructor(onConstructor_={@Autowired})
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> save(@RequestBody @Valid CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.save(customerRequest));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CustomerDeleteResponse> delete(@PathVariable Long id) throws CryptographyServiceException {
        return ResponseEntity.ok(customerService.delete(id));
    }

    @GetMapping(value = "/{id}")
    public Customer find(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping
    public Customer findByEmail(@RequestParam("email") String email) {
        return customerService.findByEmail(email);
    }
}
