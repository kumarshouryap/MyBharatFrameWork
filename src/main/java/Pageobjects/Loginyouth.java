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
		
				
		signinlink= page.locator("//span[normalize-space()='Sign In']");
		loginWithPassword = page.locator("#login_with_pwd");
		
		emailidinput = page.locator("#username");
		passwordinput = page.locator("#password");
		
		iconSenttoTermsOfUse = page.locator("//input[@id='consentCheck2']");
		
		signinbutton = page.locator("//button[@id='signInButton']");
		
		
		
		
		
	}
	
	public void loginYouth() {
		
		String email = abstractComponents.getRandomEmailFromExcelUsingFaker().trim();
	    String password = getProperty("password").trim();

	    globalWaitForClick(signinlink);
	    globalWaitForClick(loginWithPassword);
	    

	    globalWaitForFill(emailidinput, email);
	    globalWaitForFill(passwordinput, password);

	    if (!iconSenttoTermsOfUse.isChecked()) {
	        iconSenttoTermsOfUse.check();
	    }

	    // Wait until button becomes enabled
	    signinbutton.waitFor(new Locator.WaitForOptions()
	            .setState(WaitForSelectorState.ATTACHED));

	    assertThat(signinbutton).isEnabled();
	    signinbutton.click();
	}

		
		
	}
	
	


