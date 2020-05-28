package br.com.snack.apiservice.data.dto.customer;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "PhoneRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = PhoneRequest.PhoneRequestBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneRequest {

    @JsonProperty("cod_internacional")
    @NotNull
    private final String codigoPais;

    @JsonProperty("cod_area")
    @NotNull
    private final Integer codigoArea;

    @NotNull
    private final Long numero;


    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PhoneRequestBuilder {

    }
}
