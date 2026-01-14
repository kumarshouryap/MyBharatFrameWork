package Mybharat.AbstractsComponents;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.github.javafaker.Faker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class abstractComponents {

    protected Page page;
    private Properties properties;
    Faker faker;

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
    
 // Utility method for random drop-down selection
    
    public void selectRandomOption(Locator dropdown) {
        int count = dropdown.locator("option").count();
        if (count <= 1) {
            throw new RuntimeException("Dropdown has no selectable options");
        }

        int randomIndex = new Random().nextInt(count - 1) + 1; // skip index 0
        String value = dropdown.locator("option").nth(randomIndex).getAttribute("value");
        dropdown.selectOption(value);
    }

    // Select radio button in group 
    
    public void selectRadioByValue(String name, String value) {
        Locator radio = page.locator("input[type='radio'][name='" + name + "'][value='" + value + "']");
        selectRadioButton(radio);
    }
    
    // Generic method to select a radio button
    
    public void selectRadioButton(Locator radioButton) {
        if (!radioButton.isVisible()) {
            throw new RuntimeException("Radio button is not visible");
        }
        if (!radioButton.isEnabled()) {
            throw new RuntimeException("Radio button is not enabled");
        }

        if (!radioButton.isChecked()) {
            radioButton.check();   // Playwright-native way
        }
    }
    
    public void selectFirstFromTypeSearch(Page page, String inputLocator, String listBoxLocator, String triggerText) {

        page.locator(inputLocator).click();
        page.locator(inputLocator).fill(triggerText);

        Locator listBox = page.locator(listBoxLocator);
        listBox.waitFor();

        listBox.locator("[role='option'], div").first().click();
    }

}

