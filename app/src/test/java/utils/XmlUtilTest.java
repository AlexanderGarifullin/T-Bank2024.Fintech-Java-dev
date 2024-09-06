package utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import model.City;
import model.Coords;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class XmlUtilTest {

    private final String CITY_NAME = "new-york";
    private final double LATITUDE = 40.7128;
    private final double LONGITUDE = -74.0060;

    private City createTestCity() {
        return new City(CITY_NAME, new Coords(LATITUDE, LONGITUDE));
    }

    @Test
    void testToXMLSuccess(@TempDir File tempDir) throws IOException {
        // Создаем временный XML файл
        File tempFile = new File(tempDir, "city-test.xml");

        // Создаем объект для конвертации
        City city = createTestCity();

        // Используем XmlUtil для конвертации объекта в XML
        XmlUtil.toXML(city, tempFile);

        // Проверяем, что файл был создан и содержит данные
        assertThat(tempFile)
                .exists()
                .isNotEmpty();

        // Проверим содержимое файла
        XmlMapper xmlMapper = new XmlMapper();
        City deserializedCity = xmlMapper.readValue(tempFile, City.class);

        assertThat(deserializedCity)
                .isNotNull()
                .satisfies(c -> {
                    assertThat(c.getSlug()).isEqualTo(CITY_NAME);
                    assertThat(c.getCoords())
                            .isNotNull()
                            .extracting(Coords::getLat, Coords::getLon)
                            .containsExactly(LATITUDE, LONGITUDE);
                });
    }

    @Test
    void testToXMLWithNullObject(@TempDir File tempDir) throws IOException {

        File tempFile = new File(tempDir, "city-null-test.xml");

        XmlUtil.toXML(null, tempFile);

        assertThat(tempFile).exists();

        String content = Files.readString(tempFile.toPath()).trim();

        // Проверяем, что содержимое файла соответствует <null/>
        assertThat(content).isEqualTo("<null/>");
    }


    @Test
    void testToXMLWithIOException() throws IOException {
        // Создаем временный XML файл, который не может быть записан
        File tempFile = new File("/invalid/path/city-test.xml"); // Невалидный путь

        City city = createTestCity();

        // Ожидаем, что будет вызвана ошибка, но метод должен корректно обработать исключение
        XmlUtil.toXML(city, tempFile);

        assertThat(tempFile).doesNotExist();
    }
}