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

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderSnackExtraIngredientRequest", toBuilder = true)
@JsonDeserialize(builder = OrderSnackExtraIngredientRequest.OrderSnackExtraIngredientRequestBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderSnackExtraIngredientRequest {

    @NotNull
    private final UUID id;

    @NotNull
    private final Integer quantidade;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderSnackExtraIngredientRequestBuilder {

    }
}
