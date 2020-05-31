package br.com.snack.apiservice.service.strategy;

import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LotsOfMeatSalesStrategy extends IngredientBasedSalesStrategy {

    private static final String DESCRIPTION = "Muita carne (Pague 2 hamburgers, leve 3!)";
    private static final String TARGET_INGREDIENT = "hamburger";
    private static final Integer MIN_TO_HAVE = 3;

    private final IngredientRepository ingredientRepository;

    @Autowired
    public LotsOfMeatSalesStrategy(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public boolean hasFit(Order order) {
        Integer ingredientCount = discoverTargetIngredient(order, TARGET_INGREDIENT);

        return ingredientCount >= MIN_TO_HAVE;
    }

    @Override
    public Order applyDiscount(Order order) {
        return super.applyDiscount(order, TARGET_INGREDIENT, MIN_TO_HAVE, ingredientRepository);
    }


    @Override
    public String getDescription() {
        return DESCRIPTION;
    }
}
