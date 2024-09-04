package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

public class JsonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static <T> T parseJsonFile(File file, Class<T> clazz) {
        LOGGER.debug("Converting to XML file: {}", file.getAbsolutePath());
        try {
            T result = objectMapper.readValue(file, clazz);
            LOGGER.info("Successfully parsed JSON file: {}", file.getAbsolutePath());
            return result;
        } catch (JsonProcessingException e) {
            LOGGER.error("Error parsing JSON file: {}", file.getAbsolutePath(), e);
        } catch (IOException e) {
            LOGGER.error("IO error reading file: {}", file.getAbsolutePath(), e);
        }
        return null;
    }
}

