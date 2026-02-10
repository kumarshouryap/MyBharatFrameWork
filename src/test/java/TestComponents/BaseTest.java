package TestComponents;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.microsoft.playwright.*;

import MybharatUtils.ExtentReport;
import MybharatUtils.Log;
import Pageobjects.LandingPage;

public class BaseTest {

	protected Playwright playwright;
	protected Browser browser;
	protected BrowserContext context;
	protected Page page;
	protected LandingPage landingPage;
	protected static ExtentReports extentreport;
	protected static ExtentTest test;

	@BeforeSuite

	public void SetupExtentReport() {
		extentreport = ExtentReport.getExtentReport();
	}

	@BeforeClass
	
public void initializeBrowser() {
    Log.info("---------------Create Playwright instance ------------");
    playwright = Playwright.create();

 // Create Properties object to store key-value pairs from .properties file
    Properties props = new Properties();

    try (FileInputStream fis = new FileInputStream(
            // Get current project directory dynamically
            System.getProperty("user.dir") 
            // Path of properties file inside resources folder
            + "/src/main/resources/GlobalData.properties")) {

        // Load all key-value pairs from properties file into props object
        props.load(fis);

    } catch (Exception e) {
        
        // If file not found or any error occurs, throw runtime exception
        // This will stop execution and show the actual error
        throw new RuntimeException(e);
    }

    // Read "browser" value from properties file
    // If browser key is not present, default value will be "chrome"
    String browserName = props.getProperty("browser", "chrome");

    Log.info("Launching browser: " + browserName);

    switch (browserName.toLowerCase()) {
        case "firefox":
            browser = playwright.firefox()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));
            break;

        case "webkit":
            browser = playwright.webkit()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));
            break;

        case "chromium":
            browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));
            break;

        case "chrome":
        default:
            browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions()
                            .setChannel("chrome")
                            .setHeadless(false));
            break;
    }

  
 // Create a new browser context and enable file download support.
 // setAcceptDownloads(true) allows Playwright to handle and save downloaded files.
 // Without this, page.waitForDownload() will not work properly.
    
 context = browser.newContext(
         new Browser.NewContextOptions().setAcceptDownloads(true)
 );
 
    page = context.newPage();
    landingPage = new LandingPage(page);
}

	@AfterSuite

	public void flushReport() {
		extentreport.flush();
		
		// Stop the browser for debug 
		
		 page.pause();
	}
}
