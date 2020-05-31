package br.com.snack.apiservice.data.dto.order;


import br.com.snack.apiservice.data.dto.customer.AddressResponse;
import br.com.snack.apiservice.data.dto.customer.CustomerResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderResponse.OrderResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "items", "total", "enderecoEntrega", "cliente"})
public class OrderResponse {

    private final UUID id;

    private final CustomerResponse cliente;

    @JsonProperty("endereco_entrega")
    private final AddressResponse enderecoEntrega;

    private final String status;

    private final BigDecimal total;

    @JsonProperty("data_criacao")
    private final ZonedDateTime dataCriacao;

    @JsonProperty("ultima_atualizacao")
    private final ZonedDateTime ultimaAtualizacao;

    private final List<OrderSnackResponse> items;

    private final List<OrderAppliedStrategyResponse> promocoes;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderResponseBuilder {

    }
}
