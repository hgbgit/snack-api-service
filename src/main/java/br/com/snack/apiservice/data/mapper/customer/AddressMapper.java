package br.com.snack.apiservice.data.mapper.customer;

import br.com.snack.apiservice.data.dto.customer.AddressRequest;
import br.com.snack.apiservice.data.dto.customer.AddressResponse;
import br.com.snack.apiservice.data.entity.customer.Address;
import br.com.snack.apiservice.data.entity.customer.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Mapping(target = "street", source = "rua")
    @Mapping(target = "estate", source = "estado")
    @Mapping(target = "city", source = "cidade")
    @Mapping(target = "zipCode", source = "cep")
    @Mapping(target = "number", source = "numero")
    @Mapping(target = "complement", source = "complemento", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address sourceToTarget(AddressRequest addressRequest);

    @Mapping(target = "id", source = "id.address.id")
    @Mapping(target = "rua", source = "id.address.street")
    @Mapping(target = "estado", source = "id.address.estate")
    @Mapping(target = "cidade", source = "id.address.city")
    @Mapping(target = "cep", source = "id.address.zipCode")
    @Mapping(target = "numero", source = "id.address.number")
    @Mapping(target = "complemento", source = "id.address.complement", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "padrao", source = "isDefault")
    AddressResponse sourceToTarget(CustomerAddress customerAddress);
}
