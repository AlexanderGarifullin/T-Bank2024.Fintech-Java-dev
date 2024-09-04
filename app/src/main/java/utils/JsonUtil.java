package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseJsonFile(File file, Class<T> clazz) {
        try {
            T result = objectMapper.readValue(file, clazz);

           return result;
        } catch (JsonProcessingException e) {

        } catch (IOException e) {

        }
        return null;
    }
}

