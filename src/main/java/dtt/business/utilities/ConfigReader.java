package dtt.business.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
/**
 * Utility class for reading configuration properties from a properties file.
 * @author Johannes Silvennoinen
 */
public class ConfigReader {
    private static final String PROPERTIES_FILE_PATH = "configuration.properties";
    private static Properties properties;

    /**
     * Reads the configuration properties from the properties file and assigns them
     * to a static Properties object.
     */
    public static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH)) {
            Properties properties = new Properties();
            properties.load(fis);
        } catch (IOException e) {
            //Handle the exception.
        }
    }
    /**
     * Retrieves the value of the specified property key.
     * The configuration properties must be loaded using {@link #loadProperties()} before calling this method.
     *
     * @param key The property key.
     * @return The value associated with the key, or null if the key is not found.
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
