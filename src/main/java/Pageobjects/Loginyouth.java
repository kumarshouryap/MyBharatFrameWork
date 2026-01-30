package Pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.impl.Utils;
import com.microsoft.playwright.options.WaitForSelectorState;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import Mybharat.AbstractsComponents.abstractComponents;

public class Loginyouth extends abstractComponents {

	private static final String ExcelUtils = null;

	Page page;
	Locator signinlink;
	Locator loginWithPassword;
	Locator emailidinput;
	Locator passwordinput;
	Locator iconSenttoTermsOfUse;
	Locator signinbutton;

	public Loginyouth(Page page) {

		super(page);
		this.page = page;

		signinlink = page.locator("//span[normalize-space()='Sign In']");
		loginWithPassword = page.locator("#login_with_pwd");

		emailidinput = page.locator("#username");
		passwordinput = page.locator("#password");

		iconSenttoTermsOfUse = page.locator("//input[@id='consentCheck2']");

		signinbutton = page.locator("//button[@id='signInButton']");
	}

	public void loginYouth() {

		// Open login flow
		globalWaitForClick(signinlink);
		globalWaitForClick(loginWithPassword);

		// Fetch data
		String email = abstractComponents.getRandomEmailFromExcelUsingFaker();
		String password = getProperty("password"); // already throws if missing

		// Validate email
		if (email == null || email.trim().isEmpty()) {
			throw new RuntimeException("Email is missing from Excel/Faker generation.");
		}

		email = email.trim();
		password = password.trim();

		// Fill credentials
		globalWaitForFill(emailidinput, email);
		globalWaitForFill(passwordinput, password);

		System.out.println("Email = [" + email + "]");
		System.out.println("Password from properties = [" + password + "]");

		// Accept terms if not already checked
		if (!iconSenttoTermsOfUse.isChecked()) {
			iconSenttoTermsOfUse.check();
		}

		// Ensure button is ready
		signinbutton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

		assertThat(signinbutton).isEnabled();
		signinbutton.click();
	}

}
