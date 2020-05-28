package br.com.snack.apiservice.data.dto.customer;


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

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "PhoneResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = PhoneResponse.PhoneResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"codigoPais", "codigoArea", "numero", "padrao"})
public class PhoneResponse {

    @JsonProperty("cod_internacional")
    private String codigoPais;

    @JsonProperty("cod_area")
    private Integer codigoArea;

    private Long numero;

    private boolean padrao;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PhoneResponseBuilder {

    }
}
