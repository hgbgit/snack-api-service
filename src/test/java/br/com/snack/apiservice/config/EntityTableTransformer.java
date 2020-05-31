package br.com.snack.apiservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.TableEntryTransformer;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public abstract class EntityTableTransformer<T> implements TableEntryTransformer<T> {

    protected final ObjectMapper objectMapper;
    private final Type type;

    protected EntityTableTransformer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.type = this.loadType();
    }

    private Type loadType() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) { // sanity check, should never happen
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }

        return ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }

    @SuppressWarnings("unchecked")
    @Override
    public T transform(Map<String, String> entry) throws Throwable {
        return this.objectMapper.readValue(this.objectMapper.writeValueAsString(entry), (Class<T>) this.type);
    }

    protected boolean isFilledVariable(String variableValue) {
        return StringUtils.isNotBlank(variableValue) && !StringUtils.equalsIgnoreCase("null", variableValue);
    }
}
