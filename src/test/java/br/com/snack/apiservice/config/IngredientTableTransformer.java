package br.com.snack.apiservice.config;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class IngredientTableTransformer extends EntityTableTransformer<Ingredient> {

    public IngredientTableTransformer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public final Ingredient transform(Map<String, String> entry) throws Throwable {
        return super.transform(entry);
    }
}
