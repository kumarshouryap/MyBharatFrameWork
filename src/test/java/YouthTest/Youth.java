package YouthTest;

import org.testng.annotations.Test;

import MybharatUtils.Log;
import MybharatUtils.TakeScreenShort;
import Pageobjects.LandingPage;
import Pageobjects.YouthEmailMobOTPVerification;
import Pageobjects.YouthRegistrationPage;
import TestComponents.BaseTest;

public class Youth extends BaseTest {

	@Test(priority = 1)

	public void youthLandingPage() throws InterruptedException {

		Log.info("--------- open landing page sucessfullay ---------");
		test = extentreport.createTest("Youth landing Page Open Successfullay");
        
		// Open Landing Page
		landingPage = new LandingPage(page);
		landingPage.goTo();

		// Take screen short

		TakeScreenShort.getScreenShort(page);

		// 	Make Extent Report	

		String title = page.title();
		
		if (title.equals("Home | MYBharat")) {
			test.pass("Youth Landing page title verified successfully");
		} else {
			test.fail("Title verification failed");
		}

	}

	@Test(priority = 2)
	public void youthEOTPV() throws InterruptedException {

		// Test Case Name reflect in Extent Report

		test = extentreport.createTest("OTP Verification Successfullay");

		// Verify OTP

		YouthEmailMobOTPVerification registration = new YouthEmailMobOTPVerification(page);
		registration.register_verifyOTP();

		// Take Screen Short

		TakeScreenShort.getScreenShort(page);

		// Make Extent Report

		String title = page.title();

		if (title.equals("Youth Registration | MYBharat")) {
			test.pass("Youth Email OTP Verification Success");
		} else {
			test.fail("Youth Email OTP Verification Failed");
		}
			}
	
	@Test(priority = 3)
	
	public void youthRegistrationFormFill() throws InterruptedException {
		
		// Test Case Name reflect in Extent Report

		test = extentreport.createTest("Youth Registration Form Fill Successfullay");
		
		// Fill Registration Form
		
		YouthRegistrationPage registrationForm = new YouthRegistrationPage(page);
		registrationForm.fillRegistrationForm();
		Thread.sleep(2000);
		
		// Take Screen Short
		
		TakeScreenShort.getScreenShort(page);
		
		// Make Extent Report
		
		String title = page.title();
		if (title.equals("Youth Public Profile | MY Bharat")) {
			test.pass("Youth Registration Form Fill Successfully");
		} else {
			test.fail("Youth Registration Form Fill Failed");
		}
		
		
	}
}
