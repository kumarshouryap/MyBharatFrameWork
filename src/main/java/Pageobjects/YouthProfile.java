package Pageobjects;

import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.WaitForSelectorState;

import Mybharat.AbstractsComponents.abstractComponents;

public class YouthProfile extends abstractComponents {
	
	// All Locators define here

	
	Page page;
	Locator bannerIcon;
	Locator bannerInput;
	Locator logoIcon;
	Locator logoInput;
	
	 
	Locator basicInfo;
	Locator changePassword;
	/*
	 * Locator profileToggle; Locator publicProfileUrl; Locator
	 * privateProfileMessage;
	 */
	

	public YouthProfile(Page page) {
		super(page);
		this.page = page;
		
		bannerIcon = page.locator("div.tooltip_image1");
		bannerInput = page.locator("#fileInput");
		logoIcon = page.locator(".tooltip_image");
		logoInput = page.locator("#fileInput1");
		
		basicInfo = page.locator("a[href='#Basic_info']");
		changePassword = page.locator(".change-pwd-link");
		
		/*
		 * profileToggle = page.locator("#switch"); publicProfileUrl =
		 * page.locator("#purl"); privateProfileMessage = page.
		 * locator("p:has-text('Your profile is currently private. To make it publ')]");
		 */
				
		
	}

	// Complete Youth profile

	public void completeYouthProfile() {

		// Upload Banner Image

		bannerIcon.scrollIntoViewIfNeeded();
		uploadRandomImage(bannerIcon, bannerInput);
		System.out.println("Banner image uploaded Successfully.");

		// Upload Logo Image

		logoIcon.scrollIntoViewIfNeeded();
		uploadRandomImage(logoIcon, logoInput);
		System.out.println("Logo image uploaded Successfully.");
		
		//Public Profile Status
		
		fetchPublicProfileStatus(page);

	}

	// Get email from profile and write to Excel

	public void getemailfromprofile() {

		globalWaitForClick(basicInfo);

		Locator email = page.locator("#user_email_id");
		email.waitFor();
		email.scrollIntoViewIfNeeded();

		String value = email.inputValue();
		System.out.println(value);
		abstractComponents.writeEmailinExcel(value);

		System.out.println("Email extracted from profile and written to Excel successfully.");
	}

	// Scroll & open Change Password pop-up

	public void changePassword() {

		try {
			page.mouse().wheel(0, 4000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		changePassword.click();

		// Scope everything inside the modal
		Locator modal = page.locator("div.modal-dialog:has-text('Change Password')");
		modal.waitFor();

		Locator newPassword = modal.locator("#newPassword");
		Locator confirmPassword = modal.locator("#confirmPassword");
		Locator updatePassButton = modal.locator("#updatePasswordButton");

		newPassword.waitFor();
		confirmPassword.waitFor();
		updatePassButton.waitFor();

		assertThat(newPassword).isEnabled();
		newPassword.fill("Pass@123456");

		assertThat(confirmPassword).isEnabled();
		confirmPassword.fill("Pass@123456");

		assertThat(updatePassButton).isEnabled();
		updatePassButton.click();

		assertThat(modal).not().isVisible();

		// Verify only Change Password success modal

		Locator successModal = page.locator("div.modal-dialog:has-text('You have successfully changed your password')")
				.first();

		successModal.waitFor();

		assertThat(successModal).containsText("You have successfully changed your password");

		successModal.locator("button:has-text('Login')").click();

	}
	

		public void fetchPublicProfileStatus(Page page) {
			
			  // Wait for page to stabilize
		    page.waitForLoadState(LoadState.NETWORKIDLE);

		    // Open "Basic Info" tab
		    Locator basicInfoTab = page.locator("text=Basic Info");
		    basicInfoTab.waitFor(new Locator.WaitForOptions().setTimeout(20000));
		    basicInfoTab.click();

		    // Wait until Public Profile section is present
		    page.waitForSelector("text=Public Profile",
		            new Page.WaitForSelectorOptions().setTimeout(30000));

		    // Scroll page so section is visible
		    page.evaluate("window.scrollBy(0, 800)");

		    // Locate toggle near Public Profile
		    Locator toggle = page.locator(
		            "//div[contains(text(),'Public Profile')]/following::button[1]"
		    );

		    if (toggle.count() == 0) {
		        System.out.println("Public Profile toggle not found.");
		        return;
		    }

		    toggle.first().waitFor();

		    String state = toggle.first().getAttribute("aria-checked");

		    // -------- Scenario 1: Toggle is ON --------
		    if ("true".equalsIgnoreCase(state)) {

		        Locator urlText = page.locator("text=Public Profile URL");
		        urlText.first().waitFor(new Locator.WaitForOptions().setTimeout(15000));

		        String fullText = urlText.first().textContent().trim();
		        System.out.println("Toggle ON → " + fullText);

		        if (fullText.contains(":-")) {
		            String url = fullText.split(":-")[1].trim();
		            System.out.println("Extracted URL → " + url);
		        }

		        // Turn toggle OFF using mouse move
		        BoundingBox box = toggle.first().boundingBox();
		        page.mouse().move(box.x + box.width / 2, box.y + box.height / 2);
		        page.waitForTimeout(300);
		        page.mouse().click(box.x + box.width / 2, box.y + box.height / 2);

		        // Read private message
		        Locator privateMsg = page.locator("text=Your profile is currently private");
		        privateMsg.first().waitFor();

		        System.out.println("After OFF → " + privateMsg.first().textContent().trim());
		    }

		    // -------- Scenario 2: Toggle already OFF --------
		    else {
		        Locator privateMsg = page.locator("text=Your profile is currently private");
		        privateMsg.first().waitFor();

		        System.out.println("Toggle already OFF → " + privateMsg.first().textContent().trim());
		    }


		}




	}


