package br.com.snack.apiservice.data.dto.customer;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@Builder(builderClassName = "AddressResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = AddressResponse.AddressResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"rua", "numero", "complemento", "cidade", "estado", "cep", "padrao"})
public class AddressResponse {

    private final String rua;

    private final Long numero;

    private final String cep;

    private final String cidade;

    private final String estado;

    private final String complemento;

    private final Boolean padrao;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressResponseBuilder {

    }
}
