package YouthTest;

import org.testng.annotations.Test;

import MybharatUtils.Log;
import MybharatUtils.TakeScreenShort;
import Pageobjects.LandingPage;
import Pageobjects.Loginyouth;
import Pageobjects.Logoutyouth;
import Pageobjects.YouthEmailMobOTPVerification;
import Pageobjects.YouthProfile;
import Pageobjects.YouthRegistrationPage;
import TestComponents.BaseTest;

public class Youth extends BaseTest {

	private static final boolean flase = false;

	@Test(enabled = true,  priority = 1)

	public void youthLandingPage() throws InterruptedException {

		Log.info("--------- Open landing page  ---------");
		test = extentreport.createTest("Youth landing Page Open");  // Test Case Name reflect in Extent Report

		// Open Landing Page
		
		landingPage = new LandingPage(page);
		landingPage.goTo();

		// Take screen short

		TakeScreenShort.getScreenShort(page);

		// Make Extent Report

		String title = page.title();

		if (title.equals("Home | MYBharat")) {      // Verify Landing Page by checking page title
			test.pass("Successfully Open Youth Landing Page");  // Verify Landing Page Opened
		} else {
			test.fail("Failed to open Youth Landing Page"); // Verify Landing Page Open Failed
		}

	}

	@Test(enabled = true,  priority = 2)
	public void youthEOTPV() throws InterruptedException {

		// Test Case Name reflect in Extent Report

		test = extentreport.createTest("OTP Verification");

		// Verify OTP

		YouthEmailMobOTPVerification registration = new YouthEmailMobOTPVerification(page);
		registration.register_verifyOTP();

		// Take Screen Short

		TakeScreenShort.getScreenShort(page);

		// Make Extent Report

		String title = page.title();

		if (title.equals("Youth Registration | MYBharat")) {
			test.pass("Youth Email OTP Verification Success");   // Verify OTP Success
		} else {
			test.fail("Youth Email OTP Verification Failed");
		}
	}

	@Test( enabled = true,  priority = 3)

	public void youthRegistrationFormFill() throws InterruptedException {

		// Test Case Name reflect in Extent Report

		test = extentreport.createTest("Youth Registration Form Open and Completed"); 

		// Fill Registration Form

		YouthRegistrationPage registrationForm = new YouthRegistrationPage(page);
		registrationForm.fillRegistrationForm();
		Thread.sleep(2000);

		// Take Screen Short

		TakeScreenShort.getScreenShort(page);

		// Make Extent Report

		String title = page.title();
		if (title.equals("Youth Public Profile | MY Bharat")) {
			test.pass("Youth Registration Form opened and completed successfully.");  // Verify Registration Form Completion
		} else {
			test.fail("Youth Registration Form could not be opened or completed.");
		}

	}

	@Test(enabled = true, priority = 4)

	public void mybharatProfile() throws InterruptedException {

		/*
		 * test = extentreport.
		 * createTest("Getting Email from User Profile and Writing to Excel"); // Test
		 * Case Name reflect in Extent Report
		 */
		YouthProfile mybharatProfileYouth = new YouthProfile(page);
		mybharatProfileYouth.completeYouthProfile();
		TakeScreenShort.getScreenShort(page);
		/* mybharatProfileYouth.getemailfromprofile(); */

		// Take Screen Short

		

		// Make Extent Report

		/*
		 * String message1 =
		 * "Getting email from the User Profile and saved it to Excel successfully"
		 ;*/

		/*
		 * if (message1.
		 * equals("Getting email from the User Profile and saved it to Excel successfully"
		 * )) { // Verify email extraction test.
		 * pass("Getting email from the User Profile and saved it to Excel successfully"
		 * ); } else { test.fail("User Profile email could not be retrieved"); }
		 */

		// Change Password Call

		/* mybharatProfileYouth.changePassword(); */

	/*	test = extentreport.createTest("Change Password Successfullay from User Profile");   // Test Case Name reflect in Extent Report
		String message2 = "You have successfully changed your password";  // Message after changing password
		if (message2.equals("You have successfully changed your password")) { 
			test.pass("Password Changed Successfullay");
		} else {
			test.fail("Password Change Failed");
		}*/
	}

	// Login Youth 
	
	@Test(enabled = false, priority = 5)

	public void loginYuth() {

		test = extentreport.createTest("Youth Login Successfullay"); // Test Case Name reflect in Extent Report

		Loginyouth signin = new Loginyouth(page);
		signin.loginYouth();

		String title = page.title();
		if (title.equals("Youth Public Profile | MY Bharat")) {   // Verify Login by checking page title
			test.pass("Youth Login Successfullay");
		} else {
			test.fail("Youth Login Failed");
		}
	}
	
	// Logout Youth

	@Test(enabled = false, priority = 6)

	public void LogoutYouth() {
		test = extentreport.createTest("Youth Logout Successfullay"); // Test Case Name reflect in Extent Report
		Logoutyouth logout = new Logoutyouth(page);
		logout.logoutyouth();
		String title = page.title(); 
		if (title.equals("Home | MYBharat")) {     // Verify Logout by checking page title
			test.pass("Youth Logout Successfullay");
		} else {
			test.fail("Youth Logout Failed");
		}
	}
}
