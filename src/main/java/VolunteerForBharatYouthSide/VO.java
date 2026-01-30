package VolunteerForBharatYouthSide;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import Mybharat.AbstractsComponents.abstractComponents;
import Pageobjects.Loginyouth;

public class VO extends abstractComponents {

	Page page;
	Loginyouth loginYouth;

	Locator volunteerMenuLink;
	Locator countaryDropdownFilter;
	Locator searchIcon;
	Locator clickonFirstwithmatchname;
	Locator applayButton;

	Locator imageByYouthButton;
	Locator addImageforthisEvent;
	Locator browse;

	Locator sendForApprovelButton;
	Locator imageUploadError;
	Locator deleteiImageFromAddedImages;

	public VO(Page page) {
		super(page);
		this.page = page;
		this.loginYouth = new Loginyouth(page); // reuse Login page

		volunteerMenuLink = page.locator("//a[@class='text-indent-icons'][normalize-space()='Volunteer for Bharat']");

		countaryDropdownFilter = page.locator("select[name='filter-country']");
		searchIcon = page.locator("//div[@class='filter-content-search']");

		clickonFirstwithmatchname = page.locator("(//span[contains(text(),'nss special camp')])[3]");
		applayButton = page.locator("(//button[@id='elp_edit'])[2]");

		imageByYouthButton = page.locator("#profile-tab");
		addImageforthisEvent = page.locator("#upload-tab");
		browse = page.locator("#image-upload");

		sendForApprovelButton = page.locator("#gallery_publish_btn");
		imageUploadError = page.locator("#image_upload_error");

		deleteiImageFromAddedImages = page.locator("span[id='0']");
	}

	// Main flow for Volunteer for Bharat – Youth Side

	public void openVolunteerForBharat() {

		// Step 1: Click on "Volunteer for Bharat" menu
		// Uses reusable global wait + click method
		globalWaitForClick(volunteerMenuLink);

		// Step 2: Apply public page filters (country dropdown + search)
		publicPageFilterinVO();

		// Step 3: Click specific event by name (dynamic match)
		clickEventByName("National Voters");

		// Step 4: Scroll to Apply button to ensure visibility
		applayButton.first().scrollIntoViewIfNeeded();

		// Step 5: Click Apply button
		globalWaitForClick(applayButton);

		// ⚠ Hard wait used here (can be replaced with loader or element wait)
		page.waitForTimeout(10000);

		// Step 6: Upload images (intentionally uploading more than limit)
		uploadImages();

		// Step 7: Click Send for Approval button
		sendForApprovelButton.scrollIntoViewIfNeeded();
		sendForApprovelButton.click();

		// Step 8: Check if image upload validation error appears
		if (imageUploadError.isVisible(new Locator.IsVisibleOptions().setTimeout(3000))) {

			System.out.println("Image limit exceeded. Removing extra images...");

			// Step 9: Keep deleting images until error disappears
			while (imageUploadError.isVisible()) {

				int imageCount = deleteiImageFromAddedImages.count();

				// Safety check – if no images left, stop loop
				if (imageCount == 0) {
					System.out.println("No images left to delete.");
					break;
				}

				// Delete last uploaded image (more stable than first)
				Locator lastDeleteButton = deleteiImageFromAddedImages.last();
				lastDeleteButton.scrollIntoViewIfNeeded();

				int beforeCount = deleteiImageFromAddedImages.count();
				lastDeleteButton.click();

				// Wait until image count decreases (dynamic wait)
				page.waitForCondition(() -> deleteiImageFromAddedImages.count() < beforeCount);

				System.out.println("Deleted one image. Remaining: " + deleteiImageFromAddedImages.count());
			}

			// Step 10: Wait until validation error disappears completely
			imageUploadError.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

			System.out.println("Error removed. Clicking Send again...");

			// Step 11: Retry submission
			sendForApprovelButton.scrollIntoViewIfNeeded();
			sendForApprovelButton.click();
		}
	}

	// -------------------------------------------Filter on Public Page ---------------------------------------------------

// Apply filter on public Volunteer page
	public void publicPageFilterinVO() {

		// Wait until country dropdown is attached
		countaryDropdownFilter.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

		// Wait until dropdown is enabled and options are loaded
		page.waitForCondition(
				() -> countaryDropdownFilter.isEnabled() && countaryDropdownFilter.locator("option").count() > 0);

		// Select first option (All countries)
		countaryDropdownFilter.selectOption(new SelectOption().setIndex(0));

		// Click search icon
		globalWaitForClick(searchIcon);

		// Wait until event cards are visible after search
		page.waitForCondition(() -> page.locator("span.mdt_b1:visible").count() > 0);
	}

// ---------------------------------------------------Click Event by Name --------------------------------------------

// Method to dynamically search and click an event by its name
	public void clickEventByName(String eventName) {

		// Step 1: Normalize expected event name
		// - Remove extra spaces
		// - Convert to lowercase for case-insensitive comparison
		String expected = eventName.trim().toLowerCase();

		// Step 2: Wait until at least one event card becomes visible on UI
		// This ensures page has loaded event results after filter/search
		page.waitForCondition(() -> page.locator("span.mdt_b1:visible").count() > 0);

		// Step 3: Retry mechanism for dynamic/lazy loading UI
		// Some events may load late due to network delay
		// So we retry search up to 3 times
		for (int attempt = 0; attempt < 3; attempt++) {

			// Step 4: Locate all visible event name elements
			Locator events = page.locator("span.mdt_b1:visible");

			// Step 5: Count total visible events
			int count = events.count();
			System.out.println("Visible Events Found: " + count);

			// Step 6: Loop through each visible event
			for (int i = 0; i < count; i++) {

				// Get the nth event element
				Locator current = events.nth(i);

				// Step 7: Wait until this specific event element is visible
				current.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

				// Step 8: Get text from UI and normalize it
				// - Remove "..." (if truncated in UI)
				// - Trim spaces
				// - Convert to lowercase for comparison
				String uiText = current.innerText().replace("...", "").trim().toLowerCase();

				System.out.println("Event " + i + " = " + uiText);

				// Step 9: Compare UI text with expected event name
				// Using contains() in both directions to handle partial matches
				if (uiText.contains(expected) || expected.contains(uiText)) {

					// Step 10: Scroll element into view (if not visible in viewport)
					current.scrollIntoViewIfNeeded();

					// Step 11: Click the matched event
					current.click();

					// Step 12: Exit method immediately after successful click
					return;
				}
			}

			// Step 13: If event not found in this attempt,
			// wait briefly before retrying (handles delayed rendering)
			page.waitForTimeout(1000);
		}

		// Step 14: If event still not found after retries,
		// throw exception to fail the test with clear message
		throw new RuntimeException("Event not found on UI after retries: " + eventName);
	}

// -------------------------------------Upload Images by Youth-------------------------------------------------------------

// Upload images in Youth Gallery section
	public void uploadImages() {

		// Step 1: Open "Profile" tab
		imageByYouthButton.scrollIntoViewIfNeeded();
		imageByYouthButton.click();

		// Step 2: Open "Upload Images" tab
		addImageforthisEvent.click();

		// Step 3: Click browse (opens file chooser dialog)
		browse.click();

		// Step 4: Wait until file input is attached to DOM
		browse.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

		// Step 5: Upload 11 random images (intentionally exceeding limit)
		// This triggers validation error for testing
		uploadRandomImagesWithFileChooser(browse, 11);

		// Step 6: Click Send for Approval
		sendForApprovelButton.scrollIntoViewIfNeeded();
		sendForApprovelButton.click();
	}

}
