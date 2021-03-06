package br.com.snack.apiservice.data.dto.food;


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

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "SnackResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = IngredientResponse.SnackResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientResponse {

    private final UUID id;

    private final String nome;

    private final Integer quantidade;

    private final BigDecimal valor;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SnackResponseBuilder {

    }
}
