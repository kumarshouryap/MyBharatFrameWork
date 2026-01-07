package Pageobjects;

import com.microsoft.playwright.Page;
import Mybharat.AbstractsComponents.abstractComponents;

public class LandingPage extends abstractComponents {

    public LandingPage(Page page) {
        super(page);
    }

    public void goTo() {
        page.navigate(getProperty("enviroment"));
    }
}
