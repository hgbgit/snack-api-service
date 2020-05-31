package br.com.snack.apiservice.data.mapper.food;

import br.com.snack.apiservice.data.dto.food.IngredientResponse;
import br.com.snack.apiservice.data.entity.food.SnackIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SnackIngredientMapper {

    @Mapping(target = "nome", source = "id.ingredient.name")
    @Mapping(target = "valor", source = "id.ingredient.value")
    @Mapping(target = "id", source = "id.ingredient.id")
    IngredientResponse sourceToTarget(SnackIngredient snackIngredient);
}
