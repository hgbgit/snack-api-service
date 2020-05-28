package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.dto.customer.CustomerResponse;
import br.com.snack.apiservice.data.entity.customer.Customer;
import br.com.snack.apiservice.data.mapper.customer.CustomerMapper;
import br.com.snack.apiservice.data.repository.CustomerRepository;
import br.com.snack.apiservice.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private static final TimeZone SAO_PAULO_TIME_ZONE = TimeZone.getTimeZone("America/Sao_Paulo");

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        List<Customer> all = customerRepository.findAll();

        return all.stream()
                  .map(c -> customerMapper.targetToSource(c, SAO_PAULO_TIME_ZONE))
                  .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        Customer customer = customerRepository.findById(id)
                                              .orElseThrow(() -> new EntityNotFoundException(String.format("Customer[id= %s] nao encontrado.", id)));

        return customerMapper.targetToSource(customer, SAO_PAULO_TIME_ZONE);
    }
}
