package br.com.snack.apiservice.api.order;

import br.com.snack.apiservice.data.dto.order.OrderRequest;
import br.com.snack.apiservice.data.dto.order.OrderResponse;
import br.com.snack.apiservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderResponse> findAll() {
        logger.info("Received request for listing all orders.");

        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable UUID id) {
        logger.info("Received request for finding an order by id: {}.", id);

        return orderService.findById(id);
    }

    @GetMapping("/customers/{customerId}")
    public List<OrderResponse> findByCustomerId(@PathVariable UUID customerId) {
        logger.info("Received request for listing orders by customer id: {}.", customerId);

        return orderService.findByCustomerId(customerId);
    }


    @PostMapping(value = "/customers/{customerId}/", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@PathVariable UUID customerId, @RequestBody OrderRequest orderRequest) {
        logger.info("Received request for creating order for customer id: {} and delivery in its default address. Order: {}", customerId, orderRequest);

        return orderService.createOrderCustomerDefaultAddress(customerId, orderRequest);
    }

    @PostMapping(value = "/customers/{customerId}/addresses/{addressId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrder(@PathVariable UUID customerId, @PathVariable UUID addressId, @RequestBody @Valid OrderRequest orderRequest) {
        logger.info("Received request for creating order for customer id: {} and delivery in address: {}. Order: {}", customerId, addressId, orderRequest);

        return orderService.createOrder(customerId, addressId, orderRequest);
    }

    @PatchMapping(value = "/{id}/status/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateOrderStatus(@PathVariable UUID id, @PathVariable @Valid @NotNull String status) {
        logger.info("Received request for updating order id:{} to status: {}.", id, status);

        orderService.updateOrderStatus(id, status);
    }
}
