package br.com.snack.apiservice.config;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.DataTableType;

import java.util.Map;

public class TableTransformer {

    private final ObjectMapper objectMapper;

    public TableTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @DataTableType
    public Ingredient defineIngredient(Map<String, String> entry) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(entry), Ingredient.class);
    }

}
