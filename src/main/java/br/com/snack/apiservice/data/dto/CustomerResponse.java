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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "CustomerResponseBuilder", toBuilder = true)
@JsonDeserialize(builder = CustomerResponse.CustomerResponseBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {

    private final UUID id;

    @JsonProperty("primeiro_nome")
    private final String primeiroNome;

    private final String sobreNome;

    private final String email;

    private final String aniversario;

    @JsonProperty("data_criacao")
    private final ZonedDateTime dataCriacao;

    @JsonProperty("data_atualizacao")
    private final ZonedDateTime dataAtualizacao;

    private final List<PhoneResponse> telefones;

    private final List<AddressResponse> enderecos;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerResponseBuilder {

    }
}
