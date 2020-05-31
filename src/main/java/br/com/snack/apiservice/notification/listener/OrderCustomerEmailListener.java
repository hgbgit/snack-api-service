package br.com.snack.apiservice.notification.listener;

import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.notification.event.OrderStatusEvent;
import br.com.snack.apiservice.notification.event.OrderCreationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Classe FAKE, simula um envio de e-email ao cliente
 */

@Component
public class OrderCustomerEmailListener {

    private final static Logger logger = LoggerFactory.getLogger(OrderCustomerEmailListener.class);

    @EventListener
    public void orderCreationListener(OrderCreationEvent orderCreationEvent) {
        Order order = orderCreationEvent.getOrder();
        logger.info("Sending email to client {}, to notify its sucessfully created order: {}.", order.getCustomer()
                                                                                                     .getEmail(), order.getId());

        //TODO implement here email sending...
    }

    @EventListener
    public void orderCreationListener(OrderStatusEvent orderStatusEvent) {
        Order order = orderStatusEvent.getOrder();
        logger.info("Sending email to client {}, to notify order: {} update to status: {}.", order.getCustomer()
                                                                                                  .getEmail(), order.getId(), orderStatusEvent.getNewStatus());

        //TODO implement here email sending...
    }

}
