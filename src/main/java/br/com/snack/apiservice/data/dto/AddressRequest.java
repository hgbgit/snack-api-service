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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "AddressRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = AddressRequest.AddressRequestBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressRequest {

    @NotNull
    @Size(min = 5)
    private final String rua;

    @NotNull
    private final Long numero;

    @NotNull
    private final String cep;

    @NotNull
    private final String cidade;

    @NotNull
    private final String estado;

    private final String complemento;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressRequestBuilder {

    }
}
