package br.com.snack.apiservice.config;

import br.com.snack.apiservice.data.entity.food.Ingredient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.Locale;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN;

@SuppressWarnings("unused")
public class EntitiesConfigurer implements TypeRegistryConfigurer {

    public EntitiesConfigurer() {

    }

    @Override
    public Locale locale() {
        return Locale.ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                                                               .featuresToEnable(WRITE_BIGDECIMAL_AS_PLAIN)
                                                               .modules(new JSR310Module())
                                                               .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                                                               .build();

        typeRegistry.defineDataTableType(
                new DataTableType(Ingredient.class, new IngredientTableTransformer(objectMapper)));

    }
}
