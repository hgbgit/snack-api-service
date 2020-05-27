package br.com.snack.apiservice.data.dto;


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

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "PhoneResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = PhoneResponse.PhoneResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhoneResponse {

    private UUID id;

    @JsonProperty("cod_internacional")
    private String codigoPais;

    @JsonProperty("cod_area")
    private Integer coidgoArea;

    private Long numero;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PhoneResponseBuilder {

    }
}
