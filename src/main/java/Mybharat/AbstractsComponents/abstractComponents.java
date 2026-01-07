package Mybharat.AbstractsComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.microsoft.playwright.Page;

public class abstractComponents {

    protected Page page;
    private Properties properties;

    public abstractComponents(Page page) {
        this.page = page;
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties")) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load properties file", e);
        }
    }

    protected String getProperty(String key) {
        return properties.getProperty(key);
    }
}
