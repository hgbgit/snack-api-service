package br.com.snack.apiservice.data.dto.order;


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

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderSnackRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderSnackRequest.OrderSnackRequestBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSnackRequest {

    @NotNull
    private final UUID lanche;

    @NotNull
    @Min(1)
    private final Integer quantidade;

    private final List<OrderSnackExtraIngredientRequest> adicionais;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderSnackRequestBuilder {

    }
}
