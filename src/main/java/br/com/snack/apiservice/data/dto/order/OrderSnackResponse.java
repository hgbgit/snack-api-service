package br.com.snack.apiservice.data.dto.order;


import br.com.snack.apiservice.data.dto.food.SnackResponse;
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
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderSnackResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderSnackResponse.OrderSnackResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSnackResponse {

    private final SnackResponse lanche;

    private final Integer quantidade;

    private final BigDecimal valor;

    private final List<OrderSnackExtraIngredientResponse> adicionais;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderSnackResponseBuilder {

    }
}
