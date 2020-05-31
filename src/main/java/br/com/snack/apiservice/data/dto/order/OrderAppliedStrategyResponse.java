package br.com.snack.apiservice.data.dto.order;


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

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderAppliedStrategyResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderAppliedStrategyResponse.OrderAppliedStrategyResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderAppliedStrategyResponse {

    private final String descricao;

    @JsonProperty("desconto")
    private final BigDecimal valorDescontado;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderAppliedStrategyResponseBuilder {

    }
}
