package Pageobjects;

import java.nio.file.Paths;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import Mybharat.AbstractsComponents.abstractComponents;

public class CVBuilder extends abstractComponents  {
	
	Page page;
	Locator ClickCVBuilderLeftSideMenu;
	
	Locator cvLogo;
	Locator uploadLogoInCV;
	
	Locator inputresumeTitle;
	Locator inputNumber;
	Locator ShowMYBharatLogoonCV;
	Locator saveCV;
	Locator downloadCV;
	
	
	public CVBuilder(Page page) {
		super(page);
		this.page=page;
		
		ClickCVBuilderLeftSideMenu = page.locator("//a[@class='text-indent-icons cvbuilder_anch']");
		
		cvLogo = page.locator("//img[@class='aspect-square size-full object-cover']");
		uploadLogoInCV = page.locator(".relative.flex.shrink-0.overflow-hidden.rounded-full.size-14.bg-secondary");
		inputresumeTitle = page.locator("input[id='basics.headline']");
		inputNumber = page.locator("input[type='number']");
		ShowMYBharatLogoonCV = page.locator("//button[@id='basics.logoFlag']");
		saveCV =  page.locator(
			    ".inline-flex.items-center.justify-center.rounded-full.bg-background.px-4.shadow-xl.custom-toolbar-xs > button"
				).nth(5);
		downloadCV = page.locator(
			    ".inline-flex.items-center.justify-center.rounded-full.bg-background.px-4.shadow-xl.custom-toolbar-xs > button"
				).nth(6);
	}
	
public void createCVBuilder() {

	ClickCVBuilderLeftSideMenu.click();

	page.waitForURL("**cv.mybharat.gov.in**",
	        new Page.WaitForURLOptions().setTimeout(60000));



    // Wait until page fully loads
    page.waitForLoadState();

    uploadLogoInCV.waitFor(
        new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE)
    );

    uploadLogoInCV.hover();

    Locator fileInput = page.locator("input[type='file']").nth(1);

    fileInput.setInputFiles(
        Paths.get(System.getProperty("user.dir"), "UploadImages", "JPG15.jpg")
    );

    inputresumeTitle.fill("This is only for Testing");
    inputNumber.fill("8956326985");

    ShowMYBharatLogoonCV.scrollIntoViewIfNeeded();
    globalWaitForClick(ShowMYBharatLogoonCV);

    saveCV.scrollIntoViewIfNeeded();

    saveCV.waitFor(
        new Locator.WaitForOptions()
            .setState(WaitForSelectorState.ATTACHED)
            .setTimeout(60000)
    );

    saveCV.click();
    
   // Download File and Save 
    
    Download download = page.waitForDownload(() -> {
        downloadCV.click();
    });
    
    String downloadCVPath = System.getProperty("user.dir")+ "/DownloadCV/"+download.suggestedFilename();
    download.saveAs(Paths.get(downloadCVPath));
    System.out.println("File Saved Succesfully");
}



	
}

