package Pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitForSelectorState;

import Mybharat.AbstractsComponents.abstractComponents;

public class ExperientialLearning extends abstractComponents{
	
	Locator clickELPSideMenu;
	Locator selectState;
	Locator clickSearchButton;
	private int attemptelp ;

	public ExperientialLearning(Page page) {
		super(page);
		this.page=page;
		
		clickELPSideMenu = page.locator("li.list-group-item a.text-indent-icons").nth(0);
		selectState =  page.locator("select[name='filter-state']");
		clickSearchButton = page.locator("div.filter-content-search > img");
		
	 

}
	
	public void elpApplay() {

    globalWaitForClick(clickELPSideMenu);

    selectState.waitFor(new Locator.WaitForOptions()
            .setState(WaitForSelectorState.ATTACHED));

    page.waitForCondition(() ->
            selectState.isEditable() &&
            selectState.locator("option").count() > 0);

    selectState.selectOption(new SelectOption().setIndex(0));

    page.locator("input[name='filter-taskName']")
            .fill("Cyber Security ELP 2026");

    globalWaitForClick(clickSearchButton);
    
    

    // Wait & click first ELP
    
 // Wait until at least one ELP result is visible
    page.waitForCondition(() ->
        page.getByText("Cyber Security ELP 2026").count() > 0
    );

    



	}
public void addharVerification() {
	
	
}	
	
}
