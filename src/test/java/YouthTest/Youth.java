package YouthTest;

import org.testng.annotations.Test;

import MybharatUtils.Log;
import TestComponents.BaseTest;

public class Youth extends BaseTest {

    @Test(priority = 1)
    
    public void youthLandingPage() {
    	
    	Log.info("--------- open landing page sucessfullay ---------");
    	
        landingPage.goTo();
    }
}
