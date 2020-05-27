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
@Builder(builderClassName = "OrderSnackResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderSnackExtraIngredientResponse.OrderSnackResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSnackExtraIngredientResponse {

    private final UUID id;

    private final SnackResponse lanche;

    private final List<OrderSnackExtraIngredientResponse> adicionais;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderSnackResponseBuilder {

    }
}
