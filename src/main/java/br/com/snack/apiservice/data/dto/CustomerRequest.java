package br.com.snack.apiservice.data.dto;


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

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "CustomerRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = CustomerRequest.CustomerRequestBuilder.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRequest {

    @NotNull(message = "O primeiro nome nao pode ser vazio.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    private final String primeiroNome;

    @NotNull(message = "O sobrenome nao pode ser vazio.")
    @Size(min = 3, message = "O sobre nome deve ter no mínimo 3 caracteres.")
    private final String ultimoNome;

    @NotNull(message = "O email nao pode ser vazio.")
    @Email(message = "O email deve ser valido.")
    private final String email;

    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "A data de aniversário deve estar no formato dd/MM/AAAA.")
    private final String aniversario;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerRequestBuilder {

    }
}
