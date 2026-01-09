package MybharatUtils;

import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.microsoft.playwright.Page;

public class TakeScreenShort {
	
	public static byte[] getScreenShort(Page page) {
		
		SimpleDateFormat customformatdate = new SimpleDateFormat("MM-DD-YYYY-HH-MM-SS");
		Date date = new Date();
		String newdate = customformatdate.format(date);
		byte[] arr = page.screenshot(new Page.ScreenshotOptions().setFullPage(true).setPath(Paths.get("screenshot/"+newdate+".png")));
		return arr;
		
	}
	

}
