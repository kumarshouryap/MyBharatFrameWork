package Mybharat.AbstractsComponents;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.github.javafaker.Faker;
import com.microsoft.playwright.FileChooser;
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
    
	/*
	 * public static void writeEmailinExcel(String email) { try { // Use absolute or
	 * project-relative path so you know exactly where file is File file = new
	 * File(System.getProperty("user.dir") + "/UserDetails.xlsx");
	 * 
	 * Workbook workbook; Sheet sheet;
	 * 
	 * if (file.exists()) { try (FileInputStream fis = new FileInputStream(file)) {
	 * workbook = new XSSFWorkbook(fis); } sheet = workbook.getSheet("UserData"); if
	 * (sheet == null) { sheet = workbook.createSheet("UserData"); Row header =
	 * sheet.createRow(0); header.createCell(0).setCellValue("Email"); } } else {
	 * workbook = new XSSFWorkbook(); sheet = workbook.createSheet("UserData");
	 * 
	 * Row header = sheet.createRow(0); header.createCell(0).setCellValue("Email");
	 * }
	 * 
	 * // Correct way to find next row int nextRow =
	 * sheet.getPhysicalNumberOfRows(); Row row = sheet.createRow(nextRow);
	 * row.createCell(0).setCellValue(email);
	 * 
	 * try (FileOutputStream fos = new FileOutputStream(file)) {
	 * workbook.write(fos); fos.flush(); }
	 * 
	 * workbook.close(); System.out.println("Email written to: " +
	 * file.getAbsolutePath());
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */
    
    public static void writeEmailinExcel(String email) {
        try {
        	 File file = new File(System.getProperty("user.dir")
                     + "/src/main/resources/UserDetails.xlsx");
            

            Workbook workbook;
            Sheet sheet;

            if (file.exists()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    workbook = new XSSFWorkbook(fis);
                }
                sheet = workbook.getSheet("UserData");

                if (sheet == null) {
                    sheet = workbook.createSheet("UserData");
                    Row header = sheet.createRow(0);
                    header.createCell(0).setCellValue("Email");
                }
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("UserData");

                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Email");
            }

            int nextRow = sheet.getPhysicalNumberOfRows();
            Row row = sheet.createRow(nextRow);
            row.createCell(0).setCellValue(email);

            try (FileOutputStream fos = new FileOutputStream(file)) {
                workbook.write(fos);
                fos.flush();
            }

            workbook.close();

            System.out.println("Excel updated at: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Get Random Email from Excel using Faker and insert in the email filed 
    
    public static String getRandomEmailFromExcelUsingFaker() {
    String path = System.getProperty("user.dir") + "/src/main/resources/UserDetails.xlsx";
    String email = "";

    try (FileInputStream fis = new FileInputStream(path);
         Workbook workbook = new XSSFWorkbook(fis)) {

        Sheet sheet = workbook.getSheet("UserData");
        int lastRow = sheet.getLastRowNum();

        if (lastRow >= 1) {
            Faker faker = new Faker();
            int randomRow = faker.number().numberBetween(1, lastRow + 1);

            Row row = sheet.getRow(randomRow);
            if (row != null && row.getCell(0) != null) {
                email = row.getCell(0).getStringCellValue().trim();
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return email;
}
    
    
    // ----------------- Upload Random Image  ---------------------------- 
    
   public void uploadRandomImage(Locator trigger, Locator fileInput) {

    String projectRoot = System.getProperty("user.dir");
    File imagesDir = Paths.get(projectRoot, "UploadImages").toFile();

    File[] files = imagesDir.listFiles((dir, name) ->
            name.toLowerCase().endsWith(".jpg")
         || name.toLowerCase().endsWith(".png")
         || name.toLowerCase().endsWith(".jpeg"));

    if (files == null || files.length == 0) {
        throw new RuntimeException("No images found in: " + imagesDir.getAbsolutePath());
    }

    File randomFile = files[new Random().nextInt(files.length)];

    // Best case: direct upload via hidden input (no OS dialog)
    if (fileInput != null) {
        fileInput.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED));
        fileInput.setInputFiles(randomFile.toPath());
    }
    // Fallback: camera / browse icon flow
    else if (trigger != null) {
        FileChooser chooser = page.waitForFileChooser(() -> {
            trigger.scrollIntoViewIfNeeded();
            trigger.click(new Locator.ClickOptions().setForce(true));
        });
        chooser.setFiles(randomFile.toPath());
    } else {
        throw new IllegalArgumentException("Either trigger or fileInput must be provided");
    }

    System.out.println("Uploaded file: " + randomFile.getName());
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

    // Wait for Element to be attached
    
    public void waitForElement(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED)
                .setTimeout(60000));
    }

    // wait for Element to be visible
    
    public void waitForElementVisible(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.VISIBLE)
                .setTimeout(60000));
    }

    
    // scroll the Element 
    
    public void scrollToElement(Locator locator) {
        locator.scrollIntoViewIfNeeded();
    }
    
    
    // Scroll by mouse
    
    public void scrollByMouse(int x, int y) {
		page.mouse().move(0, 2000); // Move mouse to top to bottom
	}
    
    

}
    

