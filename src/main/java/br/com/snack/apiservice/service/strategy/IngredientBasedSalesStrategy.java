package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import br.com.snack.apiservice.data.entity.order.OrderAppliedStrategyId;
import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.entity.order.OrderAppliedStrategy;
import br.com.snack.apiservice.data.repository.IngredientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class IngredientBasedSalesStrategy extends SalesStrategy {

    private final static Logger logger = LoggerFactory.getLogger(IngredientBasedSalesStrategy.class);

    public Order applyDiscount(Order order, String targetIngredient, Integer minToHave, IngredientRepository ingredientRepository) {
        BigDecimal ingredientCount = BigDecimal.valueOf(discoverTargetIngredient(order, targetIngredient));
        Optional<Ingredient> ingredient = ingredientRepository.findTop1ByNameContainingIgnoreCase(targetIngredient)
                                                              .filter(i -> i.getValue()
                                                                            .compareTo(BigDecimal.ZERO) > 0);
        if (!ingredient.isPresent()) {
            logger.info("Discarding sales strategy: {}, because it fits with order but does not contain an ingredient with value in database.", this.getClass()
                                                                                                                                                    .getCanonicalName());
            return order;
        }

        BigDecimal ingredientValue = ingredient.get()
                                               .getValue();

        BigDecimal ingredientsToDiscount = ingredientCount.divide(BigDecimal.valueOf(minToHave), 2, BigDecimal.ROUND_DOWN);
        BigDecimal valueToDiscount = ingredientsToDiscount.multiply(ingredientValue)
                                                          .setScale(2, BigDecimal.ROUND_HALF_UP);

        BigDecimal totalPaid = order.getTotalPaid();
        BigDecimal orderWithDiscount = totalPaid.subtract(valueToDiscount)
                                                .setScale(2, RoundingMode.HALF_UP);

        order.setTotalPaid(orderWithDiscount);

        OrderAppliedStrategyId orderAppliedStrategyId = new OrderAppliedStrategyId(order, this.getDescription());
        OrderAppliedStrategy orderAppliedStrategy = new OrderAppliedStrategy(orderAppliedStrategyId, valueToDiscount);

        Set<OrderAppliedStrategy> orderAppliedStrategies = Optional.ofNullable(order.getAppliedStrategies())
                                                                   .orElse(new HashSet<>());
        orderAppliedStrategies.add(orderAppliedStrategy);
        order.setAppliedStrategies(orderAppliedStrategies);

        logger.info("Order has fit with {}. Order total: {}, Order with discount: {}.", this.getClass()
                                                                                            .getCanonicalName(), totalPaid, orderWithDiscount);

        return order;
    }

}
