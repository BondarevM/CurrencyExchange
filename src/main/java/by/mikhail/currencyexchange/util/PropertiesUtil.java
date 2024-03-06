package by.mikhail.currencyexchange.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {
    private static final Properties PROPERTIES = new Properties();
    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream stream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getProperty(String key){
        return PROPERTIES.getProperty(key);
    }

    private PropertiesUtil() {
    }
}
