package utils;

public final class LogMessages {
    private LogMessages() {
        // Prevent instantiation
    }

    // Main class messages
    public static final String APP_STARTED = "App started.";
    public static final String STARTING_JSON_PARSING = "Starting JSON parsing...";
    public static final String FINISHING_JSON_PARSING = "Finishing JSON parsing...";
    public static final String STARTING_XML_CONVERTING = "Starting XML converting...";
    public static final String FINISHING_XML_CONVERTING = "Finishing XML converting...";
    public static final String APP_FINISHED = "App finished.";

    // JsonUtil class messages
    public static final String CONVERTING_TO_JSON = "Converting to XML file: {}";
    public static final String JSON_PARSE_SUCCESS = "Successfully parsed JSON file: {}";
    public static final String JSON_PARSE_ERROR = "Error parsing JSON file: {}";
    public static final String JSON_IO_ERROR = "IO error reading file: {}";

    // XmlUtil class messages
    public static final String PARSING_XML_FILE = "File to parse: {}";
    public static final String OBJECT_NULL = "Object is null, nothing to write to XML file: {}";
    public static final String XML_SAVE_SUCCESS = "XML successfully saved to {}";
    public static final String XML_SAVE_ERROR = "Error saving XML to file: {}";
}
