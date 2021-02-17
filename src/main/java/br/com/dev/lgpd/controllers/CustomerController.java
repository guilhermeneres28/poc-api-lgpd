package br.com.dev.lgpd.controllers;

import br.com.dev.lgpd.services.CustomerService;
import br.com.dev.lgpd.services.exceptions.CryptographyServiceException;
import br.com.dev.lgpd.services.requests.CustomerRequest;
import br.com.dev.lgpd.services.responses.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;


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
    public void delete(@PathVariable Long id) throws CryptographyServiceException {
        customerService.delete(id);
    }
}
