package br.com.snack.apiservice.data.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "AddressResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = AddressResponse.AddressResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressResponse {

    private final UUID id;

    private final String rua;

    private final Long numero;

    private final String cep;

    private final String cidade;

    private final String estado;

    private final String complemento;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressResponseBuilder {

    }
}
