package MybharatUtils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {

	private static ExtentReports extentreport;

	public static ExtentReports getExtentReport() {

		if (extentreport == null) {
			String reportpath = System.getProperty("user.dir") + "/reports/extentreports.html";
			ExtentSparkReporter sparkreporter = new ExtentSparkReporter(reportpath);
			sparkreporter.config().setReportName("Mybharat Automation Test Report");
			sparkreporter.config().setDocumentTitle("Test Results of Mybharat Application");
			extentreport = new ExtentReports();
			extentreport.attachReporter(sparkreporter);
			extentreport.setSystemInfo("Tester", "Manoj Kumar");
			extentreport.setSystemInfo("Enviroment", "Production");

		}
		return extentreport;

	}
}
