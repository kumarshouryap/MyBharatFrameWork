package TestComponents;

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

		Log.info("---------------Launching Chromium browser with Chrome channel ------------");

		browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));
		Log.info("---------------Browser Screen Size 1920, 1080 opening successfully ------------");

		context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1920, 1080));

		Log.info("---------------Opening new Browser Page/Tab ------------");
		page = context.newPage();

		Log.info("---------------Create Landing Page Object Successfullay ------------");

		landingPage = new LandingPage(page);
	}

	// Flag to determine whether to close the browser after tests

	private boolean shouldCloseBrowser = false;

	// Runs after each test method

	@AfterMethod
	public void setFlagAfterTest(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS) {
			shouldCloseBrowser = true; // set true only if test passed
		} else {
			shouldCloseBrowser = false; // keep browser open on failure
		}
	}

	// Runs once after all tests in the class
	@AfterClass(alwaysRun = true)
	public void tearDown() {
		if (shouldCloseBrowser) {
			if (page != null)
				page.close();
			if (context != null)
				context.close();
			if (browser != null)
				browser.close();
			if (playwright != null)
				playwright.close();
		}
	}

	@AfterSuite

	public void flushReport() {
		extentreport.flush();
	}
}
