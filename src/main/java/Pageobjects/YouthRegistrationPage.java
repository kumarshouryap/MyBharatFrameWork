
package Pageobjects;

import com.github.javafaker.Faker;
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;

import Mybharat.AbstractsComponents.abstractComponents;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.util.Random;
import com.microsoft.playwright.options.SelectOption;
import org.testng.asserts.SoftAssert;
import static org.assertj.core.api.Assertions.assertThat;

public class YouthRegistrationPage extends abstractComponents {

	Page page;
	Faker faker = new Faker();

	// --- Locators --- Locator firstName; Locator lastName;

	// Personal Info
	private final Locator firstName;
	private final Locator lastName;

	private final Locator dobDD;
	private final Locator dobMM;
	private final Locator dobYYYY;

	private final Locator gender;
	
	// Caste Catogery
	
	private final Locator casteCatogery;
	
	// PWD Disablility 
	
	private final Locator pwdYes;
	
	private final Locator pwdType;
	
	private final Locator youthState;
	private final Locator youthDistrict;

	// Address
	private final Locator urban;
	private final Locator rural;
	private final Locator ulb;

	private final Locator blockPlaceholder;
	private final Locator blockInput;

	private final Locator panchayatPlaceholder;
	private final Locator panchayatInput;

	private final Locator villagePlaceholder;
	private final Locator villageInput;

	private final Locator urbanPincode;
	private final Locator ruralPincode;

	// Education & Others
	private final Locator yuvaType;
	private final Locator highestQualification;
	private final Locator institutionType;
	private final Locator schoolState;
	private final Locator schoolDistrict;

	private final Locator institutionPlaceholder;
	private final Locator institutionInput;

	private final Locator sportPlaceholder;
	private final Locator sportInput;

	private final Locator participate;
	private final Locator consent;
	private final Locator submit;
	
	

	public YouthRegistrationPage(Page page) {
		super(page);
		this.page = page;
		firstName = page.locator("#firstname");
		lastName = page.locator("#lastname");

		dobDD = page.locator("#dobDD");
		dobMM = page.locator("#dobMM");
		dobYYYY = page.locator("#dobYYYY");

		gender = page.locator("#gender");
		
		casteCatogery = page.locator("//select[@id='caste_category']");
		
		pwdYes = page.locator("#disability");
		
		pwdType = page.locator("#pwd_type");
					
		youthState = page.locator("#state");
		youthDistrict = page.locator("#district");

		urban = page.locator("(//input[@id='flexRadioDefault1'])[1]");
		rural = page.locator("(//input[@id='flexRadioDefault2'])[1]");
		ulb = page.locator("#ulb");

		blockPlaceholder = page.locator("//div[normalize-space()='Search and select a block']").first();

		blockInput = page.locator("(//div[contains(@class,'choices')]/input)[1]");

		panchayatPlaceholder = page.locator(
				"//div[contains(@class,'choices__placeholder') and normalize-space()='Search and select a panchayat']");
		panchayatInput = page.locator("(//div[contains(@class,'choices')]/input)[2]");

		villagePlaceholder = page.locator(
				"//div[contains(@class,'choices__placeholder') and normalize-space()='Search and select a village']");
		villageInput = page.locator("(//div[contains(@class,'choices')]/input)[3]");

		urbanPincode = page.locator("#pincode_urban");
		ruralPincode = page.locator("#pincode_rural");

		yuvaType = page.locator("(//input[@id='NSS'])[1]");
		highestQualification = page.locator("#qualification");
		institutionType = page.locator("#institution_type");
		schoolState = page.locator("#institution_state");
		schoolDistrict = page.locator("#institution_district");

		institutionPlaceholder = page.locator("//div[normalize-space()='Search and select an institution']").first();

		institutionInput = page.locator("(//div[contains(@class,'choices')]/input)[4]");

		sportPlaceholder = page.locator(
				"//div[contains(@class,'choices__placeholder') and contains(normalize-space(),'Search and select a sport')]");

		sportInput = page.locator("(//div[contains(@class,'choices')]/input)[5]");

		participate = page.locator("#khel_participate");
		consent = page.locator("(//input[@id='defaultCheck1'])[1]");
		submit = page.locator("#registrationButton");

	}

	// ---------------- REGISTRATION FORM ----------------

	public void fillRegistrationForm() throws InterruptedException {

		// First & Last Name
		firstName.fill(faker.name().firstName());
		lastName.fill(faker.name().lastName());

		// DOB
		dobDD.fill(String.valueOf(faker.number().numberBetween(1, 28)));
		dobMM.fill(String.valueOf(faker.number().numberBetween(1, 12)));
		dobYYYY.fill("2002");

		// Gender
		String selectedGender = faker.options().option("Male", "Female", "Others");
		gender.selectOption(selectedGender);
		
		// Caste Category 
		
		selectRandomOption(casteCatogery);
		
		// PWD Disability 
		
	
		// By default "No" is already selected

		if (faker.bool().bool()) {
		    // Change to "Yes" only sometimes
		    if (!pwdYes.isChecked()) {
		        pwdYes.check();
		    }
		    pwdType.waitFor();
		    pwdType.selectOption(new SelectOption().setIndex(1));
		}
		// else: keep "No" as it is, do nothing

	


		// State & District
		youthState.selectOption(new SelectOption().setIndex(1));
		youthDistrict.selectOption(new SelectOption().setIndex(1));

		// Address
		urban.click();

		if (ulb.locator("option").count() > 1) {
			ulb.selectOption(new SelectOption().setIndex(1));
			urbanPincode.fill(String.valueOf(faker.number().numberBetween(100000, 999999)));
		} else {
			rural.click();

			blockPlaceholder.click();
			blockInput.fill("a");
			blockInput.press("Enter");

			panchayatPlaceholder.click();
			panchayatInput.fill("a");
			panchayatInput.press("Enter");

			villagePlaceholder.click();
			villageInput.fill("a");
			villageInput.press("Enter");

			ruralPincode.fill(String.valueOf(faker.number().numberBetween(100000, 999999)));
		}

		// Education
		yuvaType.check();

		highestQualification.selectOption(new SelectOption().setIndex(4));
		institutionType.selectOption(new SelectOption().setIndex(1));

		schoolState.selectOption(new SelectOption().setIndex(6));
		schoolDistrict.selectOption(new SelectOption().setIndex(1));

		// Institution & Sport
		institutionPlaceholder.click();
		institutionInput.fill("s");
		institutionInput.press("Enter");

        // Sport Section 
		sportPlaceholder.click();
		sportInput.fill("B");
		sportInput.press("Enter");

		// Participation & Consent
		participate.check();
		consent.check();

		// Submit
		submit.click();

	}

}
