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

    // Load properties
    Properties props = new Properties();
    try (FileInputStream fis = new FileInputStream(
            System.getProperty("user.dir") + "/src/main/resources/GlobalData.properties")) {
        props.load(fis);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }

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

    context = browser.newContext();
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
