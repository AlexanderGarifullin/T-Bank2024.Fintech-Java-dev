package utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.City;
import model.Coords;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XmlUtilTest {

    private final String CITY_NAME = "new-york";
    private final double LATITUDE = 40.7128;
    private final double LONGITUDE = -74.0060;

    private City createTestCity() {
        return new City(CITY_NAME, new Coords(LATITUDE, LONGITUDE));
    }

    @Test
    void testToXMLSuccess() throws IOException {
        // Создаем временный XML файл
        File tempFile = File.createTempFile("city-test", ".xml");
        tempFile.deleteOnExit();  // Удаление файла после завершения теста

        // Создаем объект для конвертации
        City city = createTestCity();

        // Используем XmlUtil для конвертации объекта в XML
        XmlUtil.toXML(city, tempFile);

        // Проверяем, что файл был создан и содержит данные
        assertTrue(tempFile.exists());
        assertTrue(tempFile.length() > 0);

        // Проверим содержимое файла
        XmlMapper xmlMapper = new XmlMapper();
        City deserializedCity = xmlMapper.readValue(tempFile, City.class);

        assertNotNull(deserializedCity);
        assertEquals(CITY_NAME, deserializedCity.getSlug());
        assertNotNull(deserializedCity.getCoords());
        assertEquals(LATITUDE, deserializedCity.getCoords().getLat());
        assertEquals(LONGITUDE, deserializedCity.getCoords().getLon());
    }

    @Test
    void testToXMLWithNullObject() throws IOException {

        File tempFile = File.createTempFile("city-null-test", ".xml");
        tempFile.deleteOnExit();  // Удаление файла после завершения теста

        XmlUtil.toXML(null, tempFile);

        assertTrue(tempFile.exists());
        assertEquals(0, tempFile.length());
    }


    @Test
    void testToXMLWithIOException() throws IOException {
        // Создаем временный XML файл, который не может быть записан
        File tempFile = new File("/invalid/path/city-test.xml"); // Невалидный путь

        City city = createTestCity();

        // Ожидаем, что будет вызвана ошибка, но метод должен корректно обработать исключение
        XmlUtil.toXML(city, tempFile);

        assertFalse(tempFile.exists());
    }
}