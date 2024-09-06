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
        LOGGER.debug(LogMessages.PARSING_XML_FILE, file.getAbsolutePath());
        if (object == null) {
            LOGGER.warn(LogMessages.OBJECT_NULL, file.getAbsolutePath());
        }

        try {
            xmlMapper.writeValue(file, object);
            LOGGER.info(LogMessages.XML_SAVE_SUCCESS, file.getAbsolutePath());
        } catch (IOException e) {
            LOGGER.error(LogMessages.XML_SAVE_ERROR, file.getAbsolutePath(), e);
        }
    }
}
