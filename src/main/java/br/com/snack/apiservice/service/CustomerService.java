package br.com.snack.apiservice.service;

import br.com.snack.apiservice.data.dto.customer.AddressRequest;
import br.com.snack.apiservice.data.dto.customer.AddressResponse;
import br.com.snack.apiservice.data.dto.customer.CustomerRequest;
import br.com.snack.apiservice.data.dto.customer.CustomerResponse;
import br.com.snack.apiservice.data.dto.customer.PhoneRequest;
import br.com.snack.apiservice.data.dto.customer.PhoneResponse;
import br.com.snack.apiservice.data.entity.customer.Address;
import br.com.snack.apiservice.data.entity.customer.Customer;
import br.com.snack.apiservice.data.entity.customer.CustomerAddress;
import br.com.snack.apiservice.data.entity.customer.CustomerAddressId;
import br.com.snack.apiservice.data.entity.customer.CustomerPhone;
import br.com.snack.apiservice.data.entity.customer.CustomerPhoneId;
import br.com.snack.apiservice.data.entity.customer.Phone;
import br.com.snack.apiservice.data.mapper.customer.AddressMapper;
import br.com.snack.apiservice.data.mapper.customer.CustomerMapper;
import br.com.snack.apiservice.data.mapper.customer.PhoneMapper;
import br.com.snack.apiservice.data.repository.AddressRepository;
import br.com.snack.apiservice.data.repository.CustomerAddressRepository;
import br.com.snack.apiservice.data.repository.CustomerPhoneRepository;
import br.com.snack.apiservice.data.repository.CustomerRepository;
import br.com.snack.apiservice.data.repository.PhoneRepository;
import br.com.snack.apiservice.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private static final TimeZone SAO_PAULO_TIME_ZONE = TimeZone.getTimeZone("America/Sao_Paulo");

    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final CustomerPhoneRepository customerPhoneRepository;
    private final AddressRepository addressRepository;
    private final PhoneRepository phoneRepository;
    private final CustomerMapper customerMapper;
    private final AddressMapper addressMapper;
    private final PhoneMapper phoneMapper;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository,
                           CustomerPhoneRepository customerPhoneRepository, AddressRepository addressRepository,
                           PhoneRepository phoneRepository, CustomerMapper customerMapper,
                           AddressMapper addressMapper, PhoneMapper phoneMapper) {
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.customerPhoneRepository = customerPhoneRepository;
        this.addressRepository = addressRepository;
        this.phoneRepository = phoneRepository;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.phoneMapper = phoneMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findAll() {
        List<Customer> all = customerRepository.findAll();

        return all.stream()
                  .map(c -> customerMapper.targetToSource(c, SAO_PAULO_TIME_ZONE))
                  .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> findByName(String name) {
        List<Customer> customers = customerRepository.findByFirstNameContainingIgnoreCase(name);

        return customers.stream()
                        .map(c -> customerMapper.targetToSource(c, SAO_PAULO_TIME_ZONE))
                        .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(UUID id) {
        Customer customer = this.find(id);

        return customerMapper.targetToSource(customer, SAO_PAULO_TIME_ZONE);
    }

    @Transactional(readOnly = true)
    public List<PhoneResponse> findAllPhonesByCustomer(UUID id) {
        Customer customer = this.find(id);

        return customerMapper.targetToSource(customer, SAO_PAULO_TIME_ZONE)
                             .getTelefones();
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> findAllAdressesByCustomer(UUID id) {
        Customer customer = this.find(id);

        return customerMapper.targetToSource(customer, SAO_PAULO_TIME_ZONE)
                             .getEnderecos();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRepository.save(customerMapper.sourceToTarget(customerRequest));

        return customerMapper.targetToSource(customer, SAO_PAULO_TIME_ZONE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AddressResponse createCustomerAddress(UUID customerId, AddressRequest addressRequest) {
        Customer customer = this.find(customerId);

        Optional.ofNullable(addressRequest.getPadrao())
                .filter(Boolean::booleanValue)
                .ifPresent(padrao -> customer.getAddresses()
                                             .forEach(a -> a.setIsDefault(false)));

        Address address = addressMapper.sourceToTarget(addressRequest);
        addressRepository.save(address);

        CustomerAddressId customerAddressId = new CustomerAddressId(customer, address);
        CustomerAddress customerAddress = new CustomerAddress(customerAddressId, addressRequest.getPadrao());
        customerAddressRepository.save(customerAddress);

        return addressMapper.sourceToTarget(customerAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public PhoneResponse createCustomerPhone(UUID customerId, PhoneRequest phoneRequest) {
        Customer customer = find(customerId);

        Optional.ofNullable(phoneRequest.getPadrao())
                .filter(Boolean::booleanValue)
                .ifPresent(padrao -> customer.getPhones()
                                             .forEach(p -> p.setIsDefault(false)));

        Phone phone = phoneMapper.sourceToTarget(phoneRequest);
        phoneRepository.save(phone);

        CustomerPhoneId customerPhoneId = new CustomerPhoneId(customer, phone);
        CustomerPhone customerPhone = new CustomerPhone(customerPhoneId, phoneRequest.getPadrao());
        customerPhoneRepository.save(customerPhone);

        return phoneMapper.targetToSource(customerPhone);
    }

    private Customer find(UUID id) {
        return customerRepository.findById(id)
                                 .orElseThrow(() -> new EntityNotFoundException(String.format("Customer[id= %s] nao encontrado.", id)));
    }
}
