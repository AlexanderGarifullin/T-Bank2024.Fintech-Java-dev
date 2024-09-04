package utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;

public class XmlUtil {

    private static final XmlMapper xmlMapper = new XmlMapper();


    public static <T> void toXML(T object, File file) {
        if (object == null) {

            return;
        }

        try {
            xmlMapper.writeValue(file, object);

        } catch (IOException e) {

        }
    }
}
