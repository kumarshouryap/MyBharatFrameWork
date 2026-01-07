package YouthTest;

import org.testng.annotations.Test;

import TestComponents.BaseTest;

public class Youth extends BaseTest {

    @Test(priority = 1)
    public void youthLandingPage() {
        landingPage.goTo();
    }
}
