package br.com.snack.apiservice.data.mapper.order;

import br.com.snack.apiservice.data.dto.order.OrderSnackResponse;
import br.com.snack.apiservice.data.entity.order.OrderSnack;
import br.com.snack.apiservice.data.mapper.food.SnackMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {OrderSnackExtraIngredientMapper.class, SnackMapper.class})
public interface OrderSnackMapper {

    @Mapping(target = "adicionais", source = "orderSnackExtraIngredients")
    @Mapping(target = "lanche", source = "snack")
    @Mapping(target = "quantidade", source = "amount")
    @Mapping(target = "valor", source = "value")
    OrderSnackResponse sourceToTarget(OrderSnack orderSnack);
}
