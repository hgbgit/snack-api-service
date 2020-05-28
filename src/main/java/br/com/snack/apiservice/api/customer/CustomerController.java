package br.com.snack.apiservice.api.customer;

import br.com.snack.apiservice.data.dto.customer.AddressResponse;
import br.com.snack.apiservice.data.dto.customer.CustomerResponse;
import br.com.snack.apiservice.data.dto.customer.PhoneResponse;
import br.com.snack.apiservice.service.strategy.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/customers", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponse> findAll() {
        logger.info("Received request for listing all customers.");
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable UUID id) {
        logger.info("Received request for find customer by id: {}", id);
        return customerService.findById(id);
    }

    @GetMapping("/{id}/phones")
    public List<PhoneResponse> listAllPhonesByCustomer(@PathVariable UUID id) {
        logger.info("Received request for listing all customer id: {}, phones.", id);
        return customerService.findAllPhonesByCustomer(id);
    }

    @GetMapping("/{id}/addresses")
    public List<AddressResponse> listAllAddressesByCustomer(@PathVariable UUID id) {
        logger.info("Received request for listing all customer id: {}, addresses.", id);
        return customerService.findAllAdressesByCustomer(id);
    }

}
