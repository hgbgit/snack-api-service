package br.com.snack.apiservice.api.customer;

import br.com.snack.apiservice.data.dto.customer.AddressRequest;
import br.com.snack.apiservice.data.dto.customer.AddressResponse;
import br.com.snack.apiservice.data.dto.customer.CustomerRequest;
import br.com.snack.apiservice.data.dto.customer.CustomerResponse;
import br.com.snack.apiservice.data.dto.customer.PhoneRequest;
import br.com.snack.apiservice.data.dto.customer.PhoneResponse;
import br.com.snack.apiservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @GetMapping("/search")
    public List<CustomerResponse> findByName(@RequestParam("nome") String name) {
        logger.info("Received request for searching customers by firstName.");
        return customerService.findByName(name);
    }

    @GetMapping("/{id}")
    public CustomerResponse findById(@PathVariable UUID id) {
        logger.info("Received request for finding customer by id: {}", id);
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        logger.info("Received request for creating customer: {}", customerRequest.getEmail());
        return customerService.createCustomer(customerRequest);
    }

    @PostMapping(value = "/{id}/addresses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse createCustomerAddress(@PathVariable UUID id, @RequestBody @Valid AddressRequest addressRequest) {
        logger.info("Received request for creating customer address: {}", addressRequest);
        return customerService.createCustomerAddress(id, addressRequest);
    }

    @PostMapping(value = "/{id}/phones", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public PhoneResponse createCustomerAddress(@PathVariable UUID id, @RequestBody @Valid PhoneRequest phoneRequest) {
        logger.info("Received request for creating customer address: {}", phoneRequest);
        return customerService.createCustomerPhone(id, phoneRequest);
    }

}
