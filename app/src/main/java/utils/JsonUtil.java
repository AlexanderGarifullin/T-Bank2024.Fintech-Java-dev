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
        LOGGER.debug(LogMessages.CONVERTING_TO_JSON, file.getAbsolutePath());
        try {
            T result = objectMapper.readValue(file, clazz);
            LOGGER.info(LogMessages.JSON_PARSE_SUCCESS, file.getAbsolutePath());
            return result;
        } catch (JsonProcessingException e) {
            LOGGER.error(LogMessages.JSON_PARSE_ERROR, file.getAbsolutePath(), e);
        } catch (IOException e) {
            LOGGER.error(LogMessages.JSON_IO_ERROR, file.getAbsolutePath(), e);
        }
        return null;
    }
}

