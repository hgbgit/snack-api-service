package br.com.snack.apiservice.service;

import br.com.snack.apiservice.data.dto.order.OrderRequest;
import br.com.snack.apiservice.data.dto.order.OrderResponse;
import br.com.snack.apiservice.data.dto.order.OrderSnackRequest;
import br.com.snack.apiservice.data.entity.customer.Customer;
import br.com.snack.apiservice.data.entity.customer.CustomerAddress;
import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.repository.CustomerAddressRepository;
import br.com.snack.apiservice.data.repository.CustomerRepository;
import br.com.snack.apiservice.data.repository.OrderRepository;
import br.com.snack.apiservice.data.repository.SnackRepository;
import br.com.snack.apiservice.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final SnackRepository snackRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository,
                        SnackRepository snackRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.snackRepository = snackRepository;
    }

    public OrderResponse createOrder(UUID customerId, UUID addressId, OrderRequest orderRequest) {
        Customer customer = customerRepository.findById(customerId)
                                              .orElseThrow(() -> new EntityNotFoundException(String.format("Customer[id= %s] nao encontrado.", customerId)));

        CustomerAddress customerAddress = customerAddressRepository.findByIdCustomerIdAndIdAddressId(customerId, addressId)
                                                                   .orElseThrow(() -> new EntityNotFoundException(
                                                                           String.format("Address[id= %s] nao encontrado para o customer[id= %s]", addressId, customerId)));

        boolean snacksExists = snackRepository.countByIdIn(orderRequest.getItems()
                                                                       .stream()
                                                                       .map(OrderSnackRequest::getLanche)
                                                                       .collect(Collectors.toList())) == orderRequest.getItems()
                                                                                                                     .size();
        if (!snacksExists) {
            throw new EntityNotFoundException("Um ou mais lanches informados nao foram encontrados.");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(customerAddress.getId()
                                        .getAddress());


        return null;
    }


}
