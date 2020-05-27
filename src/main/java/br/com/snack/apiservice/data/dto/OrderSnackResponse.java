package br.com.snack.apiservice.data.dto;


import br.com.snack.apiservice.data.entity.order.OrderSnackExtraIngredient;
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

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderSnackResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderSnackResponse.OrderSnackResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSnackResponse {

    private final UUID id;

    private final SnackResponse lanche;

    private final List<OrderSnackExtraIngredientResponse> adicionais;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderSnackResponseBuilder {

    }
}
