package br.com.snack.apiservice.service;

import br.com.snack.apiservice.data.dto.order.OrderRequest;
import br.com.snack.apiservice.data.dto.order.OrderResponse;
import br.com.snack.apiservice.data.dto.order.OrderSnackExtraIngredientRequest;
import br.com.snack.apiservice.data.dto.order.OrderSnackRequest;
import br.com.snack.apiservice.data.entity.customer.Customer;
import br.com.snack.apiservice.data.entity.customer.CustomerAddress;
import br.com.snack.apiservice.data.entity.food.Ingredient;
import br.com.snack.apiservice.data.entity.food.Snack;
import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.entity.order.OrderSnack;
import br.com.snack.apiservice.data.entity.order.OrderSnackExtraIngredient;
import br.com.snack.apiservice.data.entity.order.OrderSnackExtraIngredientId;
import br.com.snack.apiservice.data.entity.order.OrderStatus;
import br.com.snack.apiservice.data.mapper.order.OrderMapper;
import br.com.snack.apiservice.data.repository.CustomerAddressRepository;
import br.com.snack.apiservice.data.repository.CustomerRepository;
import br.com.snack.apiservice.data.repository.IngredientRepository;
import br.com.snack.apiservice.data.repository.OrderRepository;
import br.com.snack.apiservice.data.repository.SnackRepository;
import br.com.snack.apiservice.exception.BadRequestException;
import br.com.snack.apiservice.exception.EntityNotFoundException;
import br.com.snack.apiservice.notification.event.OrderCreationEvent;
import br.com.snack.apiservice.notification.event.OrderStatusEvent;
import br.com.snack.apiservice.service.strategy.SalesStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final TimeZone SAO_PAULO_TIME_ZONE = TimeZone.getTimeZone("America/Sao_Paulo");

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final SnackRepository snackRepository;
    private final IngredientRepository ingredientRepository;

    private final OrderMapper orderMapper;
    private final List<SalesStrategy> salesStrategies;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, CustomerAddressRepository customerAddressRepository,
                        SnackRepository snackRepository, IngredientRepository ingredientRepository, OrderMapper orderMapper,
                        List<SalesStrategy> salesStrategies, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.snackRepository = snackRepository;
        this.ingredientRepository = ingredientRepository;
        this.orderMapper = orderMapper;
        this.salesStrategies = salesStrategies;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAll() {
        List<Order> all = orderRepository.findAll();

        return all.stream()
                  .map(o -> orderMapper.sourceToTarget(o, SAO_PAULO_TIME_ZONE))
                  .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findByCustomerId(UUID customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);

        return orders.stream()
                     .map(o -> orderMapper.sourceToTarget(o, SAO_PAULO_TIME_ZONE))
                     .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public OrderResponse findById(UUID id) {
        Order order = orderRepository.findById(id)
                                     .orElseThrow(() -> new EntityNotFoundException(String.format("Nao existe pedido com id: %s", id)));

        return orderMapper.sourceToTarget(order, SAO_PAULO_TIME_ZONE);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrderStatus(UUID orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                                     .orElseThrow(() -> new EntityNotFoundException(String.format("Pedido id: %s nao existe.", orderId)));

        boolean contains = Arrays.asList(OrderStatus.values())
                                 .contains(OrderStatus.valueOf(newStatus));

        if (!contains) {
            throw new BadRequestException(String.format("Status: %s invalido.", newStatus));
        }

        order.setStatus(OrderStatus.valueOf(newStatus));
        eventPublisher.publishEvent(OrderStatusEvent.builder()
                                                    .order(order)
                                                    .newStatus(OrderStatus.valueOf(newStatus))
                                                    .build());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderResponse createOrderCustomerDefaultAddress(UUID customerId, OrderRequest orderRequest) {
        Customer customer = validateAndGetCustomer(customerId);
        CustomerAddress customerAddress = customer.getAddresses()
                                                  .stream()
                                                  .filter(CustomerAddress::getIsDefault)
                                                  .findFirst()
                                                  .orElseThrow(() -> new BadRequestException(String.format("Customer [id=%s] nao possui um endereco padrao.", customerId)));

        return processOrder(customer, customerAddress, orderRequest);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderResponse createOrder(UUID customerId, UUID addressId, OrderRequest orderRequest) {
        Customer customer = validateAndGetCustomer(customerId);
        CustomerAddress customerAddress = validateAndGetCustomerAddress(customerId, addressId);

        return processOrder(customer, customerAddress, orderRequest);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OrderResponse processOrder(Customer customer, CustomerAddress customerAddress, OrderRequest orderRequest) {

        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(customerAddress.getId()
                                        .getAddress());
        order.setStatus(OrderStatus.CREATED);
        order.setTotalPaid(BigDecimal.ZERO);

        logger.info("Performing snacks value calculation..");
        Set<OrderSnack> orderSnacks = calculateOrderSnacks(orderRequest, order);

        BigDecimal orderTotalValue = calculateOrderTotal(orderSnacks);
        if (orderTotalValue.compareTo(BigDecimal.ZERO) == 0) {
            throw new BadRequestException("Seu lanche personalizado deve conter pelo menos um item.");
        }

        order.setTotalPaid(orderTotalValue);
        order.setOrderSnacks(orderSnacks);

        salesStrategies.stream()
                       .filter(s -> s.hasFit(order))
                       .forEach(salesStrategy -> salesStrategy.applyDiscount(order));
        orderRepository.save(order);

        //Padrao observer: acoplamento 0 com outros componentes, publicar em fila(SQS, Rabbit...), mandar e-mails, etc..
        eventPublisher.publishEvent(OrderCreationEvent.builder()
                                                      .order(order)
                                                      .build());

        return orderMapper.sourceToTarget(order, SAO_PAULO_TIME_ZONE);
    }

    private BigDecimal calculateOrderTotal(Set<OrderSnack> orderSnacks) {
        return orderSnacks.stream()
                          .map(snk -> snk.getValue()
                                         .multiply(BigDecimal.valueOf(snk.getAmount()))
                                         .setScale(2, RoundingMode.HALF_UP))
                          .reduce(BigDecimal.ZERO, BigDecimal::add)
                          .setScale(2, RoundingMode.HALF_UP);
    }

    private Set<OrderSnack> calculateOrderSnacks(OrderRequest orderRequest, Order order) {
        return orderRequest.getItems()
                           .stream()
                           .map(snackRequest -> {
                               Snack snack = validateAndGetSnack(snackRequest);

                               OrderSnack orderSnack = new OrderSnack();
                               orderSnack.setOrder(order);
                               orderSnack.setSnack(snack);
                               orderSnack.setAmount(snackRequest.getQuantidade());

                               BigDecimal snackTotal = calculateSnackTotal(snack);
                               orderSnack.setValue(snackTotal);

                               Optional.ofNullable(snackRequest.getAdicionais())
                                       .filter(adicionais -> !adicionais.isEmpty())
                                       .ifPresent(adicionais -> {
                                           logger.info("Snack {} have extra ingredients, processing it...", orderSnack.getSnack()
                                                                                                                      .getName());
                                           Map<UUID, Integer> adicionaisQuantidadesMap = adicionais.stream()
                                                                                                   .collect(Collectors.toMap(OrderSnackExtraIngredientRequest::getId,
                                                                                                                             OrderSnackExtraIngredientRequest::getQuantidade));

                                           List<Ingredient> ingredients = validateAndGetIngredients(orderSnack, adicionaisQuantidadesMap.keySet());

                                           Set<OrderSnackExtraIngredient> extraIngredients = calculateSnackExtraIngredients(orderSnack, adicionaisQuantidadesMap, ingredients);
                                           orderSnack.setOrderSnackExtraIngredients(extraIngredients);

                                           BigDecimal ingredientsTotal = calculateIngredientsTotal(adicionaisQuantidadesMap, ingredients);

                                           orderSnack.setValue(snackTotal.add(ingredientsTotal)
                                                                         .multiply(BigDecimal.valueOf(snackRequest.getQuantidade()))
                                                                         .setScale(2, RoundingMode.HALF_UP));
                                       });
                               return orderSnack;
                           })
                           .collect(Collectors.toSet());
    }

    private BigDecimal calculateIngredientsTotal(Map<UUID, Integer> adicionaisQuantidadesMap, List<Ingredient> ingredients) {
        return ingredients.stream()
                          .map(i -> i.getValue()
                                     .multiply(BigDecimal.valueOf(adicionaisQuantidadesMap.get(i.getId()))))
                          .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderSnackExtraIngredient> calculateSnackExtraIngredients(OrderSnack orderSnack, Map<UUID, Integer> adicionaisQuantidadesMap, List<Ingredient> ingredients) {
        return ingredients.stream()
                          .map(i -> {
                              OrderSnackExtraIngredientId extraIngredientId = new OrderSnackExtraIngredientId(orderSnack, i);
                              return new OrderSnackExtraIngredient(extraIngredientId, adicionaisQuantidadesMap.get(i.getId()));
                          })
                          .collect(Collectors.toSet());
    }

    private List<Ingredient> validateAndGetIngredients(OrderSnack orderSnack, Set<UUID> adicionais) {
        List<Ingredient> ingredients = ingredientRepository.findAllById(adicionais);

        if (ingredients.size() != adicionais.size()) {
            throw new BadRequestException(String.format("Um ou mais adicionais no lanche[id=%s], nao existem.", orderSnack.getSnack()
                                                                                                                          .getId()));
        }

        return ingredients;
    }

    private BigDecimal calculateSnackTotal(Snack snack) {
        return Optional.ofNullable(snack.getSnackIngredients())
                       .orElse(Collections.emptySet())
                       .stream()
                       .map(snackIngredient -> snackIngredient.getId()
                                                              .getIngredient()
                                                              .getValue()
                                                              .multiply(BigDecimal.valueOf(snackIngredient.getAmount())))
                       .reduce(BigDecimal.ZERO, BigDecimal::add)
                       .setScale(2, RoundingMode.HALF_UP);
    }

    private Snack validateAndGetSnack(OrderSnackRequest snackRequest) {
        return snackRepository.findById(snackRequest.getLanche())
                              .orElseThrow(() -> new EntityNotFoundException(String.format("Lanche [id= %s] nao encontrado.", snackRequest.getLanche())));
    }

    private CustomerAddress validateAndGetCustomerAddress(UUID customerId, UUID addressId) {
        return customerAddressRepository.findByIdCustomerIdAndIdAddressId(customerId, addressId)
                                        .orElseThrow(() -> new EntityNotFoundException(String.format("Address[id= %s] nao encontrado para o customer[id= %s]", addressId, customerId)));
    }

    private Customer validateAndGetCustomer(UUID customerId) {
        return customerRepository.findById(customerId)
                                 .orElseThrow(() -> new EntityNotFoundException(String.format("Customer[id= %s] nao encontrado.", customerId)));
    }

}
