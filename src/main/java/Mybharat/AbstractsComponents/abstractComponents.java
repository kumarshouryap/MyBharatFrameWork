package Mybharat.AbstractsComponents;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

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
    
    /**
     * Generates a random 10-digit number as a string.
     * 
     * @return A 10-digit number string.
     */
    public static String get10DigitNumber() {
        Random random = new Random();
        StringBuilder mobileNumber = new StringBuilder("9"); // Start with '9'

        // Generate the remaining 9 digits
        for (int i = 1; i < 10; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0-9)
            mobileNumber.append(digit);
        }

        return mobileNumber.toString(); // Return the complete 10-digit number
    }
}
