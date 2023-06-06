package dtt.global.utilities;

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

    public static final String PAGINATION_MAX_ITEMS = "PAGINATION_MAX_ITEMS";
    public static final String ROOT_ADMIN = "ROOT_ADMIN";
    public static final String COLOR_SCHEME = "COLOR_SCHEME";
    public static final String IMPRINT = "IMPRINT";
    public static final String LOGO_PATH = "LOGO_PATH";
    public static final String PRODUCTION_MODE = "PRODUCTION_MODE";
    public static final String EMAIL_PATTERN = "EMAIL_PATTERN";
    public static final String DATABASE_URL = "DATABASE_URL";
    public static final String DATABASE_USER = "DATABASE_USER";
    public static final String DATABASE_PASSWORD = "DATABASE_PASSWORD";
    public static final String DATABASE_DRIVER = "DATABASE_DRIVER";
    public static final String DATABASE_SIZE = "DATABASE_SIZE";
    public static final String SSL = "SSL";
    public static final String SSL_FACTORY = "SSL_FACTORY";
    public static final String PASSWORD_PATTERN = "PASSWORD_PATTERN";

    /**
     * Reads the configuration properties from the properties file and assigns them
     * to a static Properties object.
     */
    public static void loadProperties() {
        try (FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties = new Properties();
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
