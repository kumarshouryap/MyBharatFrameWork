package Pageobjects;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import Mybharat.AbstractsComponents.abstractComponents;

public class Logoutyouth extends abstractComponents {

	private static Page page;
	Locator logout;

	public Logoutyouth(Page page) {
		super(page);
		this.page = page;
	}

	public void logoutyouth() {
		
		page.keyboard().press("End");
		logout = page.locator("a[id='logout'] h3[class='logout-fafa']");
		globalWaitForClick(logout);
	}
	
	

}
