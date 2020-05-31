package br.com.snack.apiservice.data.mapper.order;

import br.com.snack.apiservice.data.dto.order.OrderSnackExtraIngredientResponse;
import br.com.snack.apiservice.data.entity.order.OrderSnackExtraIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderSnackExtraIngredientMapper {

    @Mapping(target = "id", source = "id.ingredient.id")
    @Mapping(target = "adicional", source = "id.ingredient.name")
    @Mapping(target = "valor", source = "id.ingredient.value")
    @Mapping(target = "quantidade", source = "amount")
    OrderSnackExtraIngredientResponse sourceToTarget(OrderSnackExtraIngredient orderSnackExtraIngredient);

}
