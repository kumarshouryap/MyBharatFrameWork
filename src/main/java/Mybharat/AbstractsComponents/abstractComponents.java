package Mybharat.AbstractsComponents;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.github.javafaker.Faker;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.io.File;
import java.io.FileOutputStream;


public class abstractComponents {

    protected Page page;
    private Properties properties;
    Faker faker;
    
    Locator locator;

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
    
 // random drop-down selection
    
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
    
    public static void writeEmailinExcel(String email) {
        try {
            File file = new File("UserDetails.xlsx");
            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                // Open existing file
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheet("UserData");

                if (sheet == null) {
                    sheet = workbook.createSheet("UserData");
                }
                fis.close();
            } else {
                // Create new file
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("UserData");

                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Email");
            }

            // Find next empty row
            int lastRow = sheet.getLastRowNum();
            int nextRow = (lastRow == 0 && sheet.getRow(0) == null) ? 0 : lastRow + 1;

            Row row = sheet.createRow(nextRow);
            row.createCell(0).setCellValue(email);

            FileOutputStream fos = new FileOutputStream(file);
            workbook.write(fos);

            fos.close();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	    

    // Get Random Email from Excel using Faker and insert in the email filed 
    
    public static String getRandomEmailFromExcelUsingFaker() {
        String path = "/UserDetails.xlsx";
        String email = "";

        try {
            FileInputStream fis = new FileInputStream(path);
            Workbook workbook = new XSSFWorkbook(fis);
            Sheet sheet = workbook.getSheet("UserData");

            int lastRow = sheet.getLastRowNum(); // excludes header

            if (lastRow >= 1) {
                Faker faker = new Faker();
                int randomRow = faker.number().numberBetween(1, lastRow + 1);

                Row row = sheet.getRow(randomRow);
                if (row != null && row.getCell(0) != null) {
                    email = row.getCell(0).getStringCellValue();
                }
            }

            workbook.close();
            fis.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return email;
    }
    
    // For Global Wait for Click

    public void globalWaitForClick(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));

        locator.scrollIntoViewIfNeeded();
        locator.click();
    }

    
    // Global Wait for Fill
    
    public void globalWaitForFill(Locator locator, String value) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));

        locator.scrollIntoViewIfNeeded();
        locator.fill(value);
    }


    
    // scroll the Element 
    
    public void scrollToElement(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }
    
    
    // Scroll by mouse
    
    

}
    

