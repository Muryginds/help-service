package ru.t1.helpservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import ru.t1.helpservice.exception.DtoValidationException;

import java.io.BufferedReader;
import java.io.IOException;

@UtilityClass
public class JsonUtils {
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public <T> T readJson(BufferedReader reader, Class<T> tClass) {
        try {
            return objectMapper.readValue(reader, tClass);
        } catch (IOException e) {
            throw new DtoValidationException(e.getMessage());
        }
    }

    public byte[] writeJsonAsBytes(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsBytes(object);
    }
}
