package br.com.snack.apiservice.data.mapper.food;

import br.com.snack.apiservice.data.dto.food.IngredientResponse;
import br.com.snack.apiservice.data.entity.food.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    @Mapping(target = "nome", source = "name")
    @Mapping(target = "valor", source = "value")
    @Mapping(target = "quantidade", ignore = true)
    IngredientResponse sourceToTarget(Ingredient ingredient);
}
