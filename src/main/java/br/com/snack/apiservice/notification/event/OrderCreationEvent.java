package br.com.snack.apiservice.notification.event;

import br.com.snack.apiservice.data.entity.order.Order;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
public class OrderCreationEvent {

    private final Order order;

}
