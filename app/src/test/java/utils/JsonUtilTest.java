package utils;

import model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @Test
    void testParseJsonFileSuccess(@TempDir File tempDir) throws IOException {
        // Создаем временный JSON файл
        File tempFile = new File(tempDir, "good-city-test.json");

        String CITY_NAME = "new-york";
        double LATITUDE = 40.7128;
        double LONGITUDE = -74.0060;

        // Пишем JSON данные в этот файл
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write(String.format(
                    "{\"slug\":\"%s\",\"coords\":{\"lat\":%f,\"lon\":%f}}",
                    CITY_NAME, LATITUDE, LONGITUDE
            ));
        }

        // Используем JsonUtil для парсинга файла
        City city = JsonUtil.parseJsonFile(tempFile, City.class);

        // Проверяем, что объект был корректно создан
        assertNotNull(city);
        assertEquals(CITY_NAME, city.getSlug());
        assertNotNull(city.getCoords());
        assertEquals(LATITUDE, city.getCoords().getLat());
        assertEquals(LONGITUDE, city.getCoords().getLon());
    }

    @Test
    void testParseJsonFileWithInvalidJson(@TempDir File tempDir) throws IOException {
        // Создаем временный файл с некорректными JSON данными
        File tempFile = new File(tempDir, "city-error-test.json");

        // Пишем некорректные JSON данные в файл
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("{invalid-json}");
        }

        City city = JsonUtil.parseJsonFile(tempFile, City.class);
        assertNull(city);
    }

    @Test
    void testParseJsonFileWithNonexistentFile() {
        // Пытаемся распарсить несуществующий файл
        File nonExistentFile = new File("nonexistent.json");

        // Проверяем, что метод возвращает null
        City city = JsonUtil.parseJsonFile(nonExistentFile, City.class);
        assertNull(city);
    }
}