package Pageobjects;

import com.microsoft.playwright.Locator;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.List;
import java.util.Random;

import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.LocatorAssertions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.BoundingBox;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import Mybharat.AbstractsComponents.abstractComponents;

public class YouthProfile extends abstractComponents {

	// All Locators define here

	Page page;
	Locator bannerIcon;
	Locator bannerInput;
	Locator logoIcon;
	Locator logoInput;

	Locator about;
	Locator inputValueInAbout;
	Locator rightmarkbutton;

	Locator areaofInterestPlusIcon;
	Locator clickAeraofInterest;

	Locator subAreaofInterest;
	Locator saveAreaofInterest;

	Locator languagePlusIcon;
	Locator selectLanguage;
	Locator saveLanguage;

	Locator professionalSummaryPlusIcon;
	Locator professionaldescriptionInput;
	Locator professionalSummarySkill;
	Locator professionalSummarySkillLabelClick;
	Locator professionalSummarySkillSelect;
	Locator professionalSummarySaveButton;

	Locator workExperiencePlusIcon;
	Locator workExperienceInputJobTitle;
	Locator workExperienceInputCompanyName;
	Locator startDateInput;
	Locator endDateInput;
	Locator workExperienceSaveButton;

	Locator tools;
	Locator inputValueInTools;
	Locator inputProfessionalIntroductionVideoUrl;
	Locator selectSocialMediaType;
	Locator inputSocialMediaUrl;
	Locator saveToolsButton;

	Locator myCertifications;
	Locator certificationDownloadInPng;
	Locator certificationDownloadInPdf;
	Locator clickonCrossButton;
	
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

		about = page.locator("#edit1");
		inputValueInAbout = page.locator("#aboutid");
		rightmarkbutton = page.locator("#saveAboutInfo");

		areaofInterestPlusIcon = page.locator("#add_intrst");
		clickAeraofInterest = page.locator("select[name='area_of_interest[]']");

		subAreaofInterest = page.locator("#mySelect4");
		saveAreaofInterest = page.locator(".firebase-profile-areaofinterest-save-btn");

		languagePlusIcon = page.locator("#add_languages");
		selectLanguage = page.locator("#mySelect1");
		saveLanguage = page.locator("#lang_submit");

		professionalSummaryPlusIcon = page.locator("#add_prof_sumry");
		professionaldescriptionInput = page.locator("#work_exposure");
		professionalSummarySkillLabelClick = page.locator("#s2id_mySelect2");
		professionalSummarySkillSelect = page.locator("ul.select2-results li.select2-result-selectable");
		professionalSummarySaveButton = page.locator("#skills_submit");

		workExperiencePlusIcon = page.locator("#add_pluse");
		workExperienceInputJobTitle = page.locator("#title");
		workExperienceInputCompanyName = page.locator("#company");
		startDateInput = page.locator("#start_date");
		endDateInput = page.locator("#end_date");
		workExperienceSaveButton = page.locator("#form_cl7");

		tools = page.locator("#edit16");
		inputValueInTools = page.locator("#devops_tools");
		inputProfessionalIntroductionVideoUrl = page.locator("#introduction_video");
		selectSocialMediaType = page.locator("//select[@name='social_links[key][]']");
		inputSocialMediaUrl = page.locator("#social_url");
		saveToolsButton = page.locator("#tools_submit");

		myCertifications = page.locator(".certificate_layout");
		page.locator(".Downloadbtnpng");
		certificationDownloadInPdf = page.locator(".Downloadbtnpdf");
		clickonCrossButton = page.locator(".fa.fa-times");

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

		// About Me Section

		waitForElementVisible(about);
		about.click();
		inputValueInAbout.waitFor();
		inputValueInAbout.fill("This is automated testing profile. Please ignore.");
		rightmarkbutton.click();

		// Area of Interest Section

		globalWaitForClick(areaofInterestPlusIcon);
		selectRandomOption(clickAeraofInterest);

		// Sub Area of Interest

		page.mouse().wheel(0, 500);

		selectRandomFromDropdown(subAreaofInterest);
		saveAreaofInterest.waitFor();
		globalWaitForClick(saveAreaofInterest);

		// Language

		globalWaitForClick(languagePlusIcon);
		selectRandomFromDropdown(selectLanguage);
		globalWaitForClick(saveLanguage);

		// Professional Summary

		professionalSummary();

		// Work Experience

		workExperience();

		// Tools

		tools();

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

	// Professional Summary Method

	public void professionalSummary() {

		globalWaitForClick(professionalSummaryPlusIcon);
		professionaldescriptionInput.fill("This is automated testing professional summary. Please ignore.");

		globalWaitForClick(professionalSummarySkillLabelClick);
		List<Locator> skills = professionalSummarySkillSelect.all();
		Random rand = new Random();
		int randomIndex = rand.nextInt(skills.size());
		skills.get(randomIndex).click();

		globalWaitForClick(professionalSummarySaveButton);

		System.out.println("Professional Summary added successfully.");
	}

	// Work Experience Method

	public void workExperience() {

		globalWaitForClick(workExperiencePlusIcon);
		workExperienceInputJobTitle.fill("Software Tester");
		workExperienceInputCompanyName.fill("ABC Technologies");

		/*
		 * startDateInput.click();
		 * page.locator("td.available:has-text('1')").first().click();
		 * 
		 * endDateInput.click();
		 * page.locator("td.available:has-text('1')").nth(1).click();
		 */

		startDateInput.fill("2020-01-01");
		endDateInput.fill("2022-12-31");

		globalWaitForClick(workExperienceSaveButton);

		System.out.println("Work Experience added successfully.");
	}

	// Tools Method

	public void tools() {

		tools.scrollIntoViewIfNeeded();
		globalWaitForClick(tools);

		inputValueInTools.fill("Selenium, Playwright, Maven, TestNG, Java, Git, Jenkins");

		inputProfessionalIntroductionVideoUrl.fill("https://www.youtube.com/watch?v=QZSlDNgi-eQ");
		
		globalWaitForClick(selectSocialMediaType);

		selectSocialMediaType.selectOption(new SelectOption().setIndex(3));

		inputSocialMediaUrl.fill("https://x.com/MkumarManoj1");

		globalWaitForClick(saveToolsButton);

		System.out.println("Tools section completed successfully.");

	}

	public void certificationDownload() {

		myCertifications.scrollIntoViewIfNeeded();

		certificationDownloadInPng.click();
		System.out.println("Certification downloaded in PNG format.");

		certificationDownloadInPdf.click();
		System.out.println("Certification downloaded in PDF format.");
		clickonCrossButton.click();

	}

}

/*
 * public void fetchPublicProfileStatus(Page page) {
 * 
 * // Wait for page to stabilize page.waitForLoadState(LoadState.NETWORKIDLE);
 * 
 * // Open "Basic Info" tab Locator basicInfoTab =
 * page.locator("text=Basic Info"); basicInfoTab.waitFor(new
 * Locator.WaitForOptions().setTimeout(20000)); basicInfoTab.click();
 * 
 * // Wait until Public Profile section is present
 * page.waitForSelector("text=Public Profile", new
 * Page.WaitForSelectorOptions().setTimeout(30000));
 * 
 * // Scroll page so section is visible
 * page.evaluate("window.scrollBy(0, 800)");
 * 
 * // Locate toggle near Public Profile Locator toggle = page.locator(
 * "//div[contains(text(),'Public Profile')]/following::button[1]" );
 * 
 * if (toggle.count() == 0) {
 * System.out.println("Public Profile toggle not found."); return; }
 * 
 * toggle.first().waitFor();
 * 
 * String state = toggle.first().getAttribute("aria-checked");
 * 
 * // -------- Scenario 1: Toggle is ON -------- if
 * ("true".equalsIgnoreCase(state)) {
 * 
 * Locator urlText = page.locator("text=Public Profile URL");
 * urlText.first().waitFor(new Locator.WaitForOptions().setTimeout(15000));
 * 
 * String fullText = urlText.first().textContent().trim();
 * System.out.println("Toggle ON → " + fullText);
 * 
 * if (fullText.contains(":-")) { String url = fullText.split(":-")[1].trim();
 * System.out.println("Extracted URL → " + url); }
 * 
 * // Turn toggle OFF using mouse move BoundingBox box =
 * toggle.first().boundingBox(); page.mouse().move(box.x + box.width / 2, box.y
 * + box.height / 2); page.waitForTimeout(300); page.mouse().click(box.x +
 * box.width / 2, box.y + box.height / 2);
 * 
 * // Read private message Locator privateMsg =
 * page.locator("text=Your profile is currently private");
 * privateMsg.first().waitFor();
 * 
 * System.out.println("After OFF → " + privateMsg.first().textContent().trim());
 * }
 * 
 * // -------- Scenario 2: Toggle already OFF -------- else { Locator privateMsg
 * = page.locator("text=Your profile is currently private");
 * privateMsg.first().waitFor();
 * 
 * System.out.println("Toggle already OFF → " +
 * privateMsg.first().textContent().trim()); }
 * 
 * 
 * }
 */
