package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.entity.food.Snack;
import br.com.snack.apiservice.data.entity.food.SnackIngredient;
import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.entity.order.OrderSnack;
import br.com.snack.apiservice.data.entity.order.OrderSnackExtraIngredient;

import java.util.Set;

public abstract class SalesStrategy {

    abstract public boolean hasFit(Order order);

    abstract public Order applyDiscount(Order order);

    abstract public String getDescription();

    Integer discoverTargetIngredient(Order order, String ingredientName) {
        Integer filteredIngredientCount = order.getOrderSnacks()
                                               .stream()
                                               .map(OrderSnack::getSnack)
                                               .map(Snack::getSnackIngredients)
                                               .flatMap(Set::stream)
                                               .filter(snackIngredient -> snackIngredient.getId()
                                                                                         .getIngredient()
                                                                                         .getName()
                                                                                         .toLowerCase()
                                                                                         .contains(ingredientName.toLowerCase()))
                                               .map(SnackIngredient::getAmount)
                                               .reduce(0, Integer::sum);

        Integer filteredExtraIngredientCount = order.getOrderSnacks()
                                                    .stream()
                                                    .map(OrderSnack::getOrderSnackExtraIngredients)
                                                    .flatMap(Set::stream)
                                                    .filter(orderSnackExtraIngredient -> orderSnackExtraIngredient.getId()
                                                                                                                  .getIngredient()
                                                                                                                  .getName()
                                                                                                                  .toLowerCase()
                                                                                                                  .contains(ingredientName.toLowerCase()))
                                                    .map(OrderSnackExtraIngredient::getAmount)
                                                    .reduce(0, Integer::sum);

        return Integer.sum(filteredIngredientCount, filteredExtraIngredientCount);
    }

}
