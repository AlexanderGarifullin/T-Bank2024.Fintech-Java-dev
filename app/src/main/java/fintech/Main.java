package fintech;

import utils.JsonUtil;
import model.City;
import utils.LogMessages;
import utils.XmlUtil;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOGGER.info(LogMessages.APP_STARTED);

        // Корректный файл.
        File correctFile = new File("jsonSample/city.json");
        // Файл с ошибками.
        File errorFile = new File("jsonSample/city-error.json");

        LOGGER.info(LogMessages.STARTING_JSON_PARSING);
        // Парсинг JSON без ошибок
        City city = JsonUtil.parseJsonFile(correctFile, City.class);
        // Парсинг JSON с ошибкой
        City cityWithError = JsonUtil.parseJsonFile(errorFile, City.class);
        LOGGER.info(LogMessages.FINISHING_JSON_PARSING);

        // Корректный файл xml.
        File correctFileXml = new File("xmlResult/city.xml");
        // Файл с ошибками xml.
        File errorFileXml = new File("xmlResult/city-error.xml");

        LOGGER.info(LogMessages.STARTING_XML_CONVERTING);
        // Конвертировать city в xml и сохранить в файле.
        XmlUtil.toXML(city, correctFileXml);
        // Конвертировать cityWithError в xml и сохранить в файле.
        XmlUtil.toXML(cityWithError, errorFileXml);
        LOGGER.info(LogMessages.FINISHING_XML_CONVERTING);

        LOGGER.info(LogMessages.APP_FINISHED);
    }
}
