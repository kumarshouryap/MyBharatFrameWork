package TestComponents;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.microsoft.playwright.*;

import Pageobjects.LandingPage;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected LandingPage landingPage;

    @BeforeClass
    public void initializeBrowser() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setChannel("chrome")
                        .setHeadless(false));

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setViewportSize(1920, 1080));

        page = context.newPage();
        landingPage = new LandingPage(page);
    }

    @AfterClass
    public void tearDown() {
        playwright.close();
    }
}
