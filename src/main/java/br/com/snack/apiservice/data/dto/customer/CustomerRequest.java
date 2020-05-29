package br.com.snack.apiservice.data.dto.customer;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(builderClassName = "CustomerRequestBuilder", toBuilder = true)
@JsonDeserialize(builder = CustomerRequest.CustomerRequestBuilder.class)
public class CustomerRequest {

    @NotNull(message = "O primeiro nome nao pode ser vazio.")
    @Size(min = 3, message = "O nome deve ter no mínimo 3 caracteres.")
    @JsonProperty("primeiro_nome")
    private final String primeiroNome;

    @NotNull(message = "O sobrenome nao pode ser vazio.")
    @Size(min = 3, message = "O sobre nome deve ter no mínimo 3 caracteres.")
    @JsonProperty("ultimo_nome")
    private final String ultimoNome;

    @NotNull(message = "O email nao pode ser vazio.")
    @Email(message = "O email deve ser valido.")
    private final String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private final Date aniversario;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerRequestBuilder {

    }
}
