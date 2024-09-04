package utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

public class XmlUtil {

    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlUtil.class);


    public static <T> void toXML(T object, File file) {
        if (object == null) {
            LOGGER.warn("Object is null, nothing to write to XML file: {}", file.getAbsolutePath());
        }

        try {
            xmlMapper.writeValue(file, object);
            LOGGER.info("XML successfully saved to {}", file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error("Error saving XML to file: {}", file.getAbsolutePath(), e);
        }
    }
}
