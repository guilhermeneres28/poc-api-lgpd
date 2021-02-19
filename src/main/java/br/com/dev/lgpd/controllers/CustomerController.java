package br.com.dev.lgpd.controllers;

import br.com.dev.lgpd.domain.entities.Customer;
import br.com.dev.lgpd.services.CustomerService;
import br.com.dev.lgpd.services.exceptions.CryptographyServiceException;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import br.com.dev.lgpd.services.requests.SensitiveRequest;
import br.com.dev.lgpd.services.responses.CustomerDeleteResponse;
import br.com.dev.lgpd.services.responses.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Customer findById(@PathVariable Long id) {
        return customerService.findCustomerById(id);
    }

    @GetMapping
    public Customer findByEmail(@RequestParam("email") String email) {
        return customerService.findByEmail(email);
    }

    /*
        TODO: Impl Restrict Access to this endpoint
     */
    @PostMapping(value = "/sensitive")
    public Customer getSensitiveData(@RequestBody @Valid SensitiveRequest sensitiveRequest) throws Exception {
        return customerService.getCustomerDescryptSensitiveData(
                sensitiveRequest.getId(),
                sensitiveRequest.getSecretKey());
    }
}
