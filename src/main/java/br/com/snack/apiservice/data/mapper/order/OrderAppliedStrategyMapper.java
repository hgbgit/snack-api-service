package br.com.snack.apiservice.data.mapper.order;

import br.com.snack.apiservice.data.dto.order.OrderAppliedStrategyResponse;
import br.com.snack.apiservice.data.entity.order.OrderAppliedStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderAppliedStrategyMapper {

    @Mapping(target = "descricao", source = "description")
    @Mapping(target = "valorDescontado", source = "discountValue")
    OrderAppliedStrategyResponse sourceToTarget(OrderAppliedStrategy orderAppliedStrategy);
}
