package br.com.snack.apiservice;

import br.com.snack.apiservice.data.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN;

@Configuration
@MockBeans({@MockBean(IngredientRepository.class),
            @MockBean(AddressRepository.class),
            @MockBean(CustomerAddressRepository.class),
            @MockBean(CustomerPhoneRepository.class),
            @MockBean(CustomerRepository.class),
            @MockBean(OrderRepository.class),
            @MockBean(OrderSnackExtraIngredientRepository.class),
            @MockBean(OrderSnackRepository.class),
            @MockBean(PhoneRepository.class),
            @MockBean(SnackRepository.class)})
public class SnackApiServiceTestConfig {

    @Bean
    public SnackApiServiceTestData apiServiceTestData() {
        return new SnackApiServiceTestData();
    }

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.featuresToEnable(WRITE_BIGDECIMAL_AS_PLAIN)
                      .modules(new JSR310Module())
                      .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                      .build();
    }
}
