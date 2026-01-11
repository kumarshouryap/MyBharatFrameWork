package YouthTest;

import org.testng.annotations.Test;

import MybharatUtils.Log;
import MybharatUtils.TakeScreenShort;
import Pageobjects.YouthEmailOTPVerificationPage;
import TestComponents.BaseTest;

public class Youth extends BaseTest {

    @Test(priority = 1)
    
    public void youthLandingPage() throws InterruptedException {
    	
    	Log.info("--------- open landing page sucessfullay ---------");
    	test = extentreport.createTest("Youth landing page opened successfully");
    	
        landingPage.goTo();
        
		/*
		 * String title = page.title(); if(title.equals("Home | MYBharat")) {
		 * test.pass("Youth Landing page title verified successfully"); }else {
		 * test.fail("Title verification failed"); }
		 */
        
        YouthEmailOTPVerificationPage registration = new YouthEmailOTPVerificationPage(page);
        registration.register_verifyOTP();
        TakeScreenShort.getScreenShort(page);
    }
}
