package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.entity.order.OrderAppliedStrategy;
import br.com.snack.apiservice.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class LotsOfCheeseSalesStrategy extends SalesStrategy {

    private static final Logger logger = LoggerFactory.getLogger(SalesStrategy.class);

    private static final String DESCRIPTION = "Muito quejio! (Pague 2 queijos, leve 3)";
    private static final String TARGET_INGREDIENT = "queijo";
    private static final Integer MIN_TO_HAVE = 3;

    @Override
    public boolean hasFit(Order order) {
        List<Ingredient> targetSet = filterAllIngredients(order, TARGET_INGREDIENT);

        return targetSet.size() >= MIN_TO_HAVE;
    }

    @Override
    public Order applyDiscount(Order order) {
        BigDecimal totalPaid = order.getTotalPaid();
        List<Ingredient> ingredients = filterAllIngredients(order, TARGET_INGREDIENT);
        BigDecimal ingredientValue = ingredients.stream()
                                                .findFirst()
                                                .orElseThrow(() -> new BusinessException("Strategy tem fit, porém nao possui ingredientes."))
                                                .getValue();

        BigDecimal cheeseCount = BigDecimal.valueOf(ingredients
                                                            .size());
        BigDecimal cheesesToDisccount = cheeseCount.divide(BigDecimal.valueOf(MIN_TO_HAVE), 2, BigDecimal.ROUND_DOWN);
        BigDecimal valueToDiscount = cheesesToDisccount.subtract(cheeseCount.multiply(ingredientValue));

        BigDecimal orderWithDiscount = order.getTotalPaid()
                                            .subtract(valueToDiscount)
                                            .setScale(2, RoundingMode.HALF_UP);

        order.setTotalPaid(orderWithDiscount);

        OrderAppliedStrategy orderAppliedStrategy = new OrderAppliedStrategy();
        orderAppliedStrategy.setDescription(this.getDescription());
        orderAppliedStrategy.setOrder(order);
        orderAppliedStrategy.setDiscountValue(totalPaid.subtract(orderWithDiscount));

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
