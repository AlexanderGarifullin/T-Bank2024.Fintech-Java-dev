package fintech;

import utils.JsonUtil;
import model.City;
import utils.XmlUtil;

import java.io.File;

public class Main {


    public static void main(String[] args) {

        // Корректный файл.
        File correctFile = new File("jsonSample/city.json");
        // Файл с ошибками.
        File errorFile = new File("jsonSample/city-error.json");

        // Парсинг JSON без ошибок
        City city = JsonUtil.parseJsonFile(correctFile, City.class);
        // Парсинг JSON с ошибкой
        City cityWithError = JsonUtil.parseJsonFile(errorFile, City.class);


        // Корректный файл xml.
        File correctFileXml = new File("xmlResult/city.xml");
        // Файл с ошибками xml.
        File errorFileXml = new File("xmlResult/city-error.xml");

        // Конверитровать city в xml и сохранить в файле.
        XmlUtil.toXML(city, correctFileXml);
        // Конверитровать cityWithError в xml и сохранить в файле.
        XmlUtil.toXML(cityWithError, errorFileXml);
    }
}
