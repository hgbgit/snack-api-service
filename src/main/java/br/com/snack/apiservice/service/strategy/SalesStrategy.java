package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import br.com.snack.apiservice.data.entity.food.Snack;
import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.entity.order.OrderSnack;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class SalesStrategy {

    abstract public boolean hasFit(Order order);

    abstract public Order applyDiscount(Order order);

    abstract public String getDescription();

    List<Ingredient> filterAllIngredients(Order order, String ingredientName) {
        Predicate<Ingredient> ingredientPredicate = ingredient -> ingredient.getName()
                                                                            .toLowerCase()
                                                                            .contains(ingredientName.toLowerCase());
        Stream<Ingredient> filteredIngredient = order.getOrderSnacks()
                                                     .stream()
                                                     .map(OrderSnack::getSnack)
                                                     .map(Snack::getSnackIngredients)
                                                     .flatMap(Set::stream)
                                                     .map(snackIngredient -> snackIngredient.getId()
                                                                                            .getIngredient())
                                                     .filter(ingredientPredicate);

        Stream<Ingredient> extraIngredientFiltered = order.getOrderSnacks()
                                                          .stream()
                                                          .map(OrderSnack::getOrderSnackExtraIngredients)
                                                          .flatMap(Set::stream)
                                                          .map(orderSnackExtraIngredient -> orderSnackExtraIngredient.getId()
                                                                                                                     .getIngredient())
                                                          .filter(ingredientPredicate);

        return Stream.concat(filteredIngredient, extraIngredientFiltered)
                     .collect(Collectors.toList());
    }

}
