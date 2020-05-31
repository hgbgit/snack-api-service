package br.com.snack.apiservice;

import br.com.snack.apiservice.data.repository.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
