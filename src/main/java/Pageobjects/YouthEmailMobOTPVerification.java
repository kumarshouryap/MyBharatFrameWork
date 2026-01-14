package Pageobjects;

import com.github.javafaker.Faker;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.Keyboard;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Mybharat.AbstractsComponents.abstractComponents;

public class YouthEmailMobOTPVerification extends abstractComponents {

	Page page;
	
	Faker faker = new Faker();
	
	String email;
	
	String mobile;

	// Locators

	Locator registerNow;
	Locator register;
	Locator enterMobileEmail;
	Locator getOTP;

	Locator yopmailInput;
	Locator yopmailGo;
	Locator yopmailRefresh;
	Locator yopmailOtpText;

	Locator otpField;
	Locator verifyOtp;

	public YouthEmailMobOTPVerification(Page page) {
		super(page);
		this.page = page;

		email = faker.name().fullName().replaceAll("[ .']", "") + "@yopmail.com";
		mobile = faker.number().digits(10);

		System.out.println(email);

		registerNow = page.locator("//span[@class='fontchange']");
		register = page.locator("//button[contains(@class,'yuva_register_as_youth_btn')]");
		enterMobileEmail = page.locator("(//input[@id='user_mobile'])[1]");
		getOTP = page.locator("button.generate_otp");

		yopmailInput = page.locator("#login");
		yopmailGo = page.locator(".material-icons-outlined.f36");
		yopmailRefresh = page.locator("#refresh");
		yopmailOtpText = page.locator("//p[contains(text(),'Your one-time password')]");

		otpField = page.locator("(//input[@id='otp-field-1'])[1]");
		verifyOtp = page.locator("#btn-verify-otp");

	}

	public void register_verifyOTP() throws InterruptedException {

		// Register flow

		assertThat(registerNow).isVisible();
		registerNow.click();

		assertThat(register).isVisible();
		register.click();

		// Enter email using keyboard style

		enterMobileEmail.click();
		page.keyboard().type(email, new Keyboard.TypeOptions().setDelay(80));

		// Wait until button becomes enabled

		page.waitForCondition(() -> !getOTP.isDisabled());
		getOTP.click();

		// Open Yopmail in new tab
		// Open a new browser tab in the same browser session (same cookies, same
		// context)

		Page mailPage = page.context().newPage();

		// Navigate to Yopmail website in the new tab

		mailPage.navigate("https://yopmail.com/");

		// From the generated email like "WilmerHills@yopmail.com",
		// extract only "WilmerHills" because Yopmail uses only the prefix

		String prefix = email.split("@")[0];

		// Locate the inbox input box on Yopmail and enter the email prefix

		mailPage.locator("#login").fill(prefix);

		// Click on the "Go" button to open the inbox

		mailPage.locator(".material-icons-outlined.f36").click();

		// Click the refresh button to fetch the latest emails (OTP mail may arrive
		// late)

		mailPage.locator("#refresh").click();

		// Yopmail shows email content inside an iframe with id "ifmail"
		// Switch into that iframe and wait until the OTP message appears

		mailPage.frameLocator("iframe#ifmail").locator("//p[contains(text(),'Your one-time password')]").waitFor();

		// Once the OTP message is visible, read its full text

		String otpText = mailPage.frameLocator("iframe#ifmail")
				.locator("//p[contains(text(),'Your one-time password')]").innerText();

		// Example text:
		// "Your one-time password (OTP) for registering on MyBharat is 482913. This OTP
		// is valid for 10 minutes."

		// Extract only the numeric OTP value from the text
		String otp = otpText.split("\\.")[0] // Take part before first dot
				.split(" is ")[1] // Take part after " is "
				.trim(); // Remove extra spaces

		// Print OTP in console for debugging
		System.out.println("OTP: " + otp);

		// Close the Yopmail tab after reading OTP
		mailPage.close();

		// Now you can use this OTP on your main application page:
		// otpField.fill(otp);
		// verifyOtp.click();

		// Enter OTP
		assertThat(otpField).isVisible();
		otpField.fill(otp);

		assertThat(verifyOtp).isEnabled();
		verifyOtp.click();
	}
}
