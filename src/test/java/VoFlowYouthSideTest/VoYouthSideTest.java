package VoFlowYouthSideTest;

import org.testng.annotations.Test;

import com.microsoft.playwright.Page;

import Pageobjects.LandingPage;
import Pageobjects.Loginyouth;
import TestComponents.BaseTest;

import VolunteerForBharatYouthSide.VO;


public class VoYouthSideTest extends BaseTest {
 

	@Test(enabled = true, priority = 1)
    public void verifyVolunteerFlow() {
 	   // Open Landing Page
		
		  LandingPage landing = new LandingPage(page); 
		  landing.goTo();
		 
        
        // Login as Youth
        
        Loginyouth loginYouth = new Loginyouth(page);
        loginYouth.loginYouth();
    	
        // Open Volunteer for Bharat Page
        
        test = extentreport.createTest("VO Youth Side Flow like apply on VO upload Images");
        
        VO volunteer = new VO(page);
        volunteer.openVolunteerForBharat();
        
        String title = page.title();
    	if (title.equals("Events | MYBharat"))
    		
    	{
			test.pass("Event's Page Successfully opened"); 
		} else {
			test.fail("Failed to open Event's Page"); 
		}
        
       
             
    }
	
	
}
