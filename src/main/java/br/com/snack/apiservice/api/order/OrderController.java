package br.com.snack.apiservice.api.order;

import br.com.snack.apiservice.data.dto.order.OrderRequest;
import br.com.snack.apiservice.data.dto.order.OrderResponse;
import br.com.snack.apiservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/customers/{customerId}/")
    public OrderResponse createOrder(@PathVariable UUID customerId, @RequestBody OrderRequest orderRequest) {
        logger.info("Received request for creating order for customer: {} and delivery in its default address. Order: {}", customerId, orderRequest);

        //TODO Implementar método para criacao de order usando endereço default do customer
        return null;
    }

    @PostMapping("/customers/{customerId}/addresses/{addressId}")
    public OrderResponse createOrder(@PathVariable UUID customerId, @PathVariable UUID addressId, @RequestBody @Valid OrderRequest orderRequest) {
        logger.info("Received request for creating order for customer: {} and delivery in address: {}. Order: {}", customerId, addressId, orderRequest);

        return orderService.createOrder(customerId, addressId, orderRequest);
    }
}
