package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.entity.order.OrderAppliedStrategy;
import br.com.snack.apiservice.data.entity.order.OrderAppliedStrategyId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class LightSalesStrategy extends SalesStrategy {

    private static final Logger logger = LoggerFactory.getLogger(LightSalesStrategy.class);

    private static final String MUST_HAVE = "alface";

    private static final String MUST_NOT_HAVE = "bacon";

    private static final String DESCRIPTION = "Promoção Light (-10%)";

    @Override
    public boolean hasFit(Order order) {
        Integer mustHave = discoverTargetIngredient(order, MUST_HAVE);
        Integer mustNotHave = discoverTargetIngredient(order, MUST_NOT_HAVE);

        return mustHave >= 1 && mustNotHave == 0;
    }

    @Override
    public Order applyDiscount(Order order) {
        BigDecimal totalPaid = order.getTotalPaid();
        BigDecimal orderWithDiscount = totalPaid.multiply(BigDecimal.valueOf(0.9))
                                                .setScale(2, RoundingMode.HALF_UP);

        order.setTotalPaid(orderWithDiscount);
        OrderAppliedStrategyId orderAppliedStrategyId = new OrderAppliedStrategyId(order, this.getDescription());
        OrderAppliedStrategy orderAppliedStrategy = new OrderAppliedStrategy(orderAppliedStrategyId, totalPaid.subtract(orderWithDiscount));

        Set<OrderAppliedStrategy> orderAppliedStrategies = Optional.ofNullable(order.getAppliedStrategies())
                                                                   .orElse(new HashSet<>());
        orderAppliedStrategies.add(orderAppliedStrategy);
        order.setAppliedStrategies(orderAppliedStrategies);

        logger.info("Order has fit with {}. Order total: {}, Order with discount: {}.", this.getClass()
                                                                                            .getCanonicalName(), totalPaid, orderWithDiscount);

        return order;
    }


    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
