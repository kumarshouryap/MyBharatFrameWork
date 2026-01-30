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
        
        VO volunteer = new VO(page);
        volunteer.openVolunteerForBharat();
        
       
             
    }
	
	
}
