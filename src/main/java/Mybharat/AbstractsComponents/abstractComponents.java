package Mybharat.AbstractsComponents;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputFilter.Config;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import com.github.javafaker.Faker;
import com.microsoft.playwright.FileChooser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.io.File;
import java.io.FileOutputStream;


public class abstractComponents {

    protected Page page;
    private static   Properties properties;
    Faker faker;
    
    Locator locator;

    public abstractComponents(Page page) {
        this.page = page;
       
        loadProperties();
        faker = new Faker();
        
    }

    public  void loadProperties() {
    	if (properties == null) {
            properties = new Properties();
            try (FileInputStream fis = new FileInputStream(
                    System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties")) {
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load GlobalData.properties", e);
            }
        }
    }

    public String getProperty(String key) {
        String value = properties.getProperty(key);

        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(
                "Property '" + key + "' is missing or empty in GlobalData.properties"
            );
        }
        return value.trim();
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
    
    
    // Password Read from Global Data Properties File
    
    public static String getPropertyfromGlobalDataFile(String key) {

        try {
            if (properties == null) {
            	properties = new Properties();

                // Read from classpath (BEST PRACTICE)
            	
                InputStream input = Config.class
                        .getClassLoader()
                        .getResourceAsStream("GlobalData.properties");

                if (input == null) {
                    throw new RuntimeException("GlobalData.properties not found in classpath");
                }

                properties.load(input);
            }
            return properties.getProperty(key);

        } catch (Exception e) {
            throw new RuntimeException("Failed to read property: " + key, e);
        }
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
    
   // random drop-down selection by removing "Select" option
     
    public void selectRandomFromDropdown(Locator dropdown) {
    List<String> options = dropdown.locator("option").allTextContents();
    options.remove(0); // remove "Select"

    int randomIndex = new Random().nextInt(options.size()) + 1;
    dropdown.selectOption(new SelectOption().setIndex(randomIndex));

    }
    
    // drop down type search and select first option
    
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
    
   // Static method to write an email into Excel file
public static void writeEmailinExcel(String email) {

    try {
        // Step 1: Define file path dynamically using project root directory
        // This ensures the file path works on any machine
        File file = new File(System.getProperty("user.dir")
                + "/src/main/resources/UserDetails.xlsx");

        // Step 2: Declare Workbook and Sheet references
        // Workbook represents entire Excel file
        // Sheet represents a specific sheet inside workbook
        Workbook workbook;
        Sheet sheet;

        // Step 3: Check if Excel file already exists
        if (file.exists()) {

            // Step 4: If file exists, open it using FileInputStream
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);  // Load existing workbook
            }

            // Step 5: Try to get sheet named "UserData"
            sheet = workbook.getSheet("UserData");

            // Step 6: If sheet does not exist, create it
            if (sheet == null) {
                sheet = workbook.createSheet("UserData");

                // Create header row at index 0
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Email"); // Set column header
            }

        } else {

            // Step 7: If file does NOT exist, create new workbook
            workbook = new XSSFWorkbook();

            // Create new sheet named "UserData"
            sheet = workbook.createSheet("UserData");

            // Create header row
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Email");
        }

        // Step 8: Get next available row number
        // getPhysicalNumberOfRows() returns count of non-empty rows
        int nextRow = sheet.getPhysicalNumberOfRows();

        // Step 9: Create new row at next available index
        Row row = sheet.createRow(nextRow);

        // Step 10: Create cell in column 0 and write email value
        row.createCell(0).setCellValue(email);

        // Step 11: Write updated workbook back to file
        try (FileOutputStream fos = new FileOutputStream(file)) {
            workbook.write(fos);  // Save workbook to file
            fos.flush();          // Ensure data is written completely
        }

        // Step 12: Close workbook to release memory resources
        workbook.close();

        // Step 13: Print success message with full file path
        System.out.println("Excel updated at: " + file.getAbsolutePath());

    } catch (Exception e) {

        // Step 14: Print stack trace if any error occurs
        // Helps in debugging issues
        e.printStackTrace();
    }
}



    // Get Random Email from Excel using Faker and insert in the email filed 
    
public static String getRandomEmailFromExcelUsingFaker() {

    // Build the Excel file path
    String path = System.getProperty("user.dir") + "/src/main/resources/UserDetails.xlsx";

    // List to store only valid emails
    List<String> validEmails = new ArrayList<>();

    try (FileInputStream fis = new FileInputStream(path);
         Workbook workbook = new XSSFWorkbook(fis)) {

        // Get the sheet
        Sheet sheet = workbook.getSheet("UserData");

        // Loop from row 1 (skip header) till last row
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            // Get current row
            Row row = sheet.getRow(i);

            // Continue if row is null
            if (row == null) continue;

            // Get first cell (email column)
            Cell cell = row.getCell(0);

            // Continue if cell is null
            if (cell == null) continue;

            // Convert cell value to String safely
            String email = cell.toString().trim();

            // Add only non-empty values
            if (!email.isEmpty()) {
                validEmails.add(email);
            }
        }

        // If no valid email found in entire sheet
        if (validEmails.isEmpty()) {
            throw new RuntimeException("No valid email found in Excel sheet");
        }

        // Pick a random email from the valid list
        Faker faker = new Faker();
        int index = faker.number().numberBetween(0, validEmails.size());

        return validEmails.get(index);

    } catch (Exception e) {
        throw new RuntimeException("Error reading email from Excel", e);
    }
}



    
    
    // ----------------- Upload Random Image  Logo and Banner Single File---------------------------- 
    
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


		
	 
   // Upload multiple Images 
   
// Method to upload multiple random images using Playwright FileChooser
// trigger  -> element that opens file dialog (like Browse button)
// howMany  -> number of random images to upload
public void uploadRandomImagesWithFileChooser(Locator trigger, int howMany) {

    // Step 1: Get dynamic path to "UploadImages" folder inside project root
    // System.getProperty("user.dir") returns project root directory
    Path imagesDir = Paths.get(System.getProperty("user.dir"), "UploadImages");

    // Step 2: Check if UploadImages folder exists
    // If folder is missing, stop execution with clear message
    if (!Files.exists(imagesDir)) {
        throw new RuntimeException("UploadImages folder not found at: " + imagesDir);
    }

    // Step 3: Open a stream to read all files inside UploadImages folder
    // try-with-resources ensures stream is closed automatically
    try (Stream<Path> filesStream = Files.list(imagesDir)) {

        // Step 4: Filter only image files (.jpg, .png, .jpeg)
        // Convert filename to lowercase for case-insensitive comparison
        List<Path> imageFiles = filesStream
                .filter(path -> {
                    String name = path.getFileName().toString().toLowerCase();
                    return name.endsWith(".jpg")
                        || name.endsWith(".png")
                        || name.endsWith(".jpeg");
                })
                .collect(Collectors.toList());  // Convert filtered stream to list

        // Step 5: If no image files found, throw error
        if (imageFiles.isEmpty()) {
            throw new RuntimeException("No images found in: " + imagesDir);
        }

        // Step 6: Shuffle image list randomly
        // This ensures different images are selected every time
        Collections.shuffle(imageFiles);

        // Step 7: Select required number of images
        // Math.min prevents IndexOutOfBoundsException if howMany > available files
        List<Path> selected = imageFiles.subList(0,
                Math.min(howMany, imageFiles.size()));

        // Step 8: Wait for FileChooser event to be triggered
        // FileChooser is used when clicking a button opens OS file dialog
        FileChooser chooser = page.waitForFileChooser(() -> {

            // Scroll trigger element into view if not visible
            trigger.scrollIntoViewIfNeeded();

            // Click trigger element to open file dialog
            trigger.click();
        });

        // Step 9: Set selected image files in FileChooser
        // Convert List<Path> to Path[] array format required by Playwright
        chooser.setFiles(selected.toArray(new Path[0]));

        // Step 10: Print uploaded file names for debugging/logging
        System.out.println("Uploaded files:");
        selected.forEach(path ->
                System.out.println(" - " + path.getFileName()));

    } catch (IOException e) {

        // Step 11: Handle IO errors (like folder access issues)
        // Wrap original exception inside RuntimeException
        throw new RuntimeException("Error reading images from folder", e);
    }
}



    
    // For Global Wait for Click

//This method waits properly and safely clicks on any element
public void globalWaitForClick(Locator locator) {

 // Wait until the element becomes visible in the DOM
 // Visible means: present + displayed (not hidden)
 locator.waitFor(new Locator.WaitForOptions()
         .setState(WaitForSelectorState.VISIBLE));  

 // Wait until the element is enabled (not disabled)
 // This prevents clicking too early when button is still disabled
 page.waitForCondition(() ->
         locator.isEnabled() && locator.isVisible()
 );

 // Scroll the element into view if it is outside the visible area
 // This ensures click does not fail due to element being off-screen
 locator.scrollIntoViewIfNeeded();

 // Perform the actual click action
 // Timeout ensures Playwright keeps retrying click if needed
 locator.click(new Locator.ClickOptions()
         .setTimeout(60000));
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
    

