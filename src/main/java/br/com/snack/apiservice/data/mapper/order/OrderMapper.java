package br.com.snack.apiservice.data.mapper.order;

import br.com.snack.apiservice.data.dto.order.OrderResponse;
import br.com.snack.apiservice.data.entity.order.Order;
import br.com.snack.apiservice.data.mapper.DateMapper;
import br.com.snack.apiservice.data.mapper.customer.AddressMapper;
import br.com.snack.apiservice.data.mapper.customer.CustomerMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.TimeZone;

@Mapper(componentModel = "spring", uses = {OrderSnackMapper.class, AddressMapper.class, CustomerMapper.class, DateMapper.class})
public interface OrderMapper {

    @Mapping(target = "cliente", source = "customer")
    @Mapping(target = "enderecoEntrega", source = "address")
    @Mapping(target = "total", source = "totalPaid")
    @Mapping(target = "dataCriacao", source = "createdAt")
    @Mapping(target = "ultimaAtualizacao", source = "updatedAt")
    @Mapping(target = "items", source = "orderSnacks")
    OrderResponse sourceToTarget(Order order, @Context TimeZone timeZone);
}
