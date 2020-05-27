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

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderResponse.OrderResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {

    private final UUID id;

    private final CustomerResponse cliente;

    @JsonProperty("endereco_entrega")
    private final AddressResponse enderecoEntrega;

    private final String status;

    private final BigDecimal total;

    @JsonProperty("data_criacao")
    private final ZonedDateTime data_criacao;

    @JsonProperty("ultima_atualizacao")
    private final ZonedDateTime ultimaAtualizacao;



    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderResponseBuilder {

    }
}
