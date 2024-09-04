package fintech;

import utils.JsonUtil;
import model.City;
import utils.XmlUtil;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info("App started.");

        // Корректный файл.
        File correctFile = new File("jsonSample/city.json");
        // Файл с ошибками.
        File errorFile = new File("jsonSample/city-error.json");

        LOGGER.info("Starting JSON parsing...");
        // Парсинг JSON без ошибок
        City city = JsonUtil.parseJsonFile(correctFile, City.class);
        // Парсинг JSON с ошибкой
        City cityWithError = JsonUtil.parseJsonFile(errorFile, City.class);
        LOGGER.info("Finishing JSON parsing...");

        // Корректный файл xml.
        File correctFileXml = new File("xmlResult/city.xml");
        // Файл с ошибками xml.
        File errorFileXml = new File("xmlResult/city-error.xml");

        LOGGER.info("Starting XML converting...");
        // Конверитровать city в xml и сохранить в файле.
        XmlUtil.toXML(city, correctFileXml);
        // Конверитровать cityWithError в xml и сохранить в файле.
        XmlUtil.toXML(cityWithError, errorFileXml);
        LOGGER.info("Finishing XML converting...");

        LOGGER.info("App finished.");
    }
}
