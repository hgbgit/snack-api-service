package br.com.snack.apiservice.data.mapper.food;

import br.com.snack.apiservice.data.dto.food.SnackResponse;
import br.com.snack.apiservice.data.entity.food.Snack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = SnackIngredientMapper.class)
public interface SnackMapper {

    @Mapping(target = "nome", source = "name")
    @Mapping(target = "ingredientes", source = "snackIngredients")
    SnackResponse targetToSource(Snack snack);
}
