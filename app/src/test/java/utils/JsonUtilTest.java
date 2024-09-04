package utils;

import model.City;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @Test
    void testParseJsonFileSuccess() throws IOException {
        // Создаем временный JSON файл
        File tempFile = File.createTempFile("good-city-test", ".json");
        tempFile.deleteOnExit();  // Удаление файла после завершения теста

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
    void testParseJsonFileWithInvalidJson() throws IOException {
        // Создаем временный файл с некорректными JSON данными
        File tempFile = File.createTempFile("city-error-test", ".json");
        tempFile.deleteOnExit();  // Удаление файла после завершения теста

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