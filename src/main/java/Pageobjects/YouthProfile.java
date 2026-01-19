package Pageobjects;

import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import Mybharat.AbstractsComponents.abstractComponents;

public class YouthProfile extends abstractComponents {

    private  Locator basicInfo;
    private  static Locator changePassword;
    private static Page page;

    public YouthProfile(Page page) {
        super(page);
        this.page = page;
        basicInfo = page.locator("a[href='#Basic_info']");
        changePassword = page.locator(".change-pwd-link");
    }

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
        
    
    	// Scroll & open Change Password popup
    
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
        
        Locator successModal = page.locator(
                "div.modal-dialog:has-text('You have successfully changed your password')"
        ).first();

        successModal.waitFor();

        assertThat(successModal)
                .containsText("You have successfully changed your password");

        successModal.locator("button:has-text('Login')").click();

        

    }
}

