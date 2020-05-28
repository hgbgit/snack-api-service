package br.com.snack.apiservice.data.dto.order;


import br.com.snack.apiservice.data.dto.customer.AddressResponse;
import br.com.snack.apiservice.data.dto.customer.CustomerResponse;
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
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderRequest.OrderRequestBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {

    @NotNull
    private final List<OrderSnackRequest> items;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderRequestBuilder {

    }
}
