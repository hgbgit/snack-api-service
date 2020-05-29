package br.com.snack.apiservice.data.mapper.customer;

import br.com.snack.apiservice.data.dto.customer.PhoneRequest;
import br.com.snack.apiservice.data.dto.customer.PhoneResponse;
import br.com.snack.apiservice.data.entity.customer.CustomerPhone;
import br.com.snack.apiservice.data.entity.customer.Phone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(target = "countryCode", source = "codigoPais")
    @Mapping(target = "areaCode", source = "codigoArea")
    @Mapping(target = "number", source = "numero")
    Phone sourceToTarget(PhoneRequest phoneRequest);

    @Mapping(target = "codigoPais", source = "id.phone.countryCode")
    @Mapping(target = "codigoArea", source = "id.phone.areaCode")
    @Mapping(target = "numero", source = "id.phone.number")
    @Mapping(target = "padrao", source = "isDefault")
    PhoneResponse targetToSource(CustomerPhone customerPhone);
}
