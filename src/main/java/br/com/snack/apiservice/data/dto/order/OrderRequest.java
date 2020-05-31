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
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "OrderRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = OrderRequest.OrderRequestBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {

    @NotNull
    @Size(min = 1)
    private final List<OrderSnackRequest> items;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OrderRequestBuilder {

    }
}
