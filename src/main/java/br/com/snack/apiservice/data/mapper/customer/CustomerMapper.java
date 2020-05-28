package br.com.snack.apiservice.data.mapper.customer;

import br.com.snack.apiservice.data.dto.customer.CustomerRequest;
import br.com.snack.apiservice.data.dto.customer.CustomerResponse;
import br.com.snack.apiservice.data.entity.customer.Customer;
import br.com.snack.apiservice.data.mapper.DateMapper;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.TimeZone;

@Mapper(componentModel = "spring", uses = {DateMapper.class, PhoneMapper.class, AddressMapper.class})
public interface CustomerMapper {

    @Mapping(target = "firstName", source = "primeiroNome")
    @Mapping(target = "lastName", source = "ultimoNome")
    @Mapping(target = "birthDate", source = "aniversario", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer sourceToTarget(CustomerRequest customerRequest);

    @Mapping(target = "aniversario", source = "birthDate", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "dataAtualizacao", source = "updatedAt")
    @Mapping(target = "dataCriacao", source = "createdAt")
    @Mapping(target = "enderecos", source = "addresses", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "telefones", source = "phones", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "primeiroNome", source = "firstName")
    @Mapping(target = "sobreNome", source = "lastName")
    CustomerResponse targetToSource(Customer customer, @Context TimeZone timeZone);
}
