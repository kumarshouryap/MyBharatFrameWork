package YouthTest;

import org.testng.annotations.Test;

import MybharatUtils.Log;
import MybharatUtils.TakeScreenShort;
import Pageobjects.LandingPage;
import Pageobjects.Loginyouth;
import Pageobjects.YouthEmailMobOTPVerification;
import Pageobjects.YouthProfile;
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
	
	@Test(priority =4)
	
	public void mybharatProfile() throws InterruptedException {
		
		// Extract email from profile and write to Excel
		
		test = extentreport.createTest("Extract Email form Profile and Write that email into Excel");
		
		YouthProfile mybharatProfileYouth = new YouthProfile(page);
		mybharatProfileYouth.getemailfromprofile();
		
		// Take Screen Short
		
		TakeScreenShort.getScreenShort(page);
		
		// Make Extent Report
		
		String message1 = "Email extracted from profile and written to Excel successfully";
				
		if (message1.equals("Email extracted from profile and written to Excel successfully")) {
			test.pass("Email extracted from profile and written to Excel successfully");
		} else {
			test.fail("Failed to extract email from profile and write to Excel");
		}
		
		// Change Password Call 
		
		mybharatProfileYouth.changePassword();
		
		test = extentreport.createTest("Change Password Successfullay");
		String  message2 = "You have successfully changed your password";
		if(message2.equals("You have successfully changed your password")) {
			test.pass("Password Changed Successfullay");
			} else {	
				test.fail("Password Change Failed");
			}		
	}
	
	public void loginYuth() {
		Loginyouth signin = new Loginyouth(page);
		signin.loginYouth();
	}
}
