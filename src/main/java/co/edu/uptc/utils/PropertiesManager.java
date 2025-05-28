package co.edu.uptc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private static Properties properties;

    private PropertiesManager() {

    }

    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("data/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getImgPath() {
        return properties.getProperty("path.img");
    }

    public static String getSoundPath() {
        return properties.getProperty("path.sound");
    }

}
