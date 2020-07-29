package com;

import java.io.File;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.utility.Constants;
import com.utility.DbOperation;
import com.utility.Logutil;
import com.utility.MyScreenRecorder;
import com.utility.Util;

public class ExtentReportTest extends Baseclass {

	public static ExtentSparkReporter spark;
	public static ExtentReports extent;
	public static ExtentTest parenttest;
	public static ExtentTest childtest;

	DbOperation dbo = new DbOperation();

	@BeforeSuite
	public void setUp() {
		spark = new ExtentSparkReporter(Constants.EXTENTREPORT);
		extent = new ExtentReports();
		extent.attachReporter(spark);

		extent.setSystemInfo("OS", "Windows 10");
		extent.setSystemInfo("Host Name", "Yuvraj");
		extent.setSystemInfo("Environment", "Dev");
		extent.setSystemInfo("User Name", "Yuvraj Rajput");

		spark.config().setDocumentTitle("Automation Test Report");
		spark.config().setReportName("Automation Report");
		spark.config().setTheme(Theme.DARK);

		// Database Connection

		dbo.dbConnection();

	}

	@BeforeTest
	public void getTestname() {

		parenttest = extent.createTest(getClass().getName());

	}

	@BeforeClass
	public void browserLaunch() {

		String browser = Util.getPropertyfileData("browser");
		Logutil.info("Browser is : " + browser);
	//	webLaunch(browser);

	}

	@BeforeMethod
	public void getMethodname(Method method) throws Exception {

		String childtestname = ProjectUtility.getTestcaseDesc(Constants.TESTCASEFILEPATH, Constants.TESTCASEFILENAME,
				Constants.TESTCASERESULTSHEET, method.getName());
		Logutil.startTestCase("Start " + childtestname);
		Logutil.info("Test case Desc for method name " + method.getName() + " is " + childtestname);
		childtest = parenttest.createNode(childtestname);
		MyScreenRecorder.startRecording(childtestname);

	}

	public void testStep(String status, String stepdesc) {

		if (status.equalsIgnoreCase("INFO")) {
			childtest.log(Status.INFO, MarkupHelper.createLabel(stepdesc, ExtentColor.YELLOW));
		} else if (status.equalsIgnoreCase("ERROR")) {
			childtest.log(Status.ERROR, MarkupHelper.createLabel(stepdesc, ExtentColor.PURPLE));
		} else if (status.equalsIgnoreCase("FAIL")) {
			childtest.log(Status.FAIL, MarkupHelper.createLabel(stepdesc, ExtentColor.RED));
		} else if (status.equalsIgnoreCase("FATAL")) {
			childtest.log(Status.FATAL, MarkupHelper.createLabel(stepdesc, ExtentColor.ORANGE));
		} else if (status.equalsIgnoreCase("PASS")) {
			childtest.log(Status.PASS, MarkupHelper.createLabel(stepdesc, ExtentColor.GREEN));
		} else if (status.equalsIgnoreCase("SKIP")) {
			childtest.log(Status.SKIP, MarkupHelper.createLabel(stepdesc, ExtentColor.BLUE));
		} else if (status.equalsIgnoreCase("WARNING")) {
			childtest.log(Status.WARNING, MarkupHelper.createLabel(stepdesc, ExtentColor.BLACK));
		}

	}

	@AfterMethod
	public void getResult(ITestResult result) throws Exception {
		ProjectUtility.updateTestresultinExcel(Constants.TESTCASEFILEPATH, Constants.TESTCASEFILENAME,
				Constants.TESTCASERESULTSHEET, result);

		if (result.getStatus() == ITestResult.FAILURE) {
			Logutil.error("Test case Failed");
			childtest.log(Status.FAIL, MarkupHelper
					.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
			childtest.fail(result.getThrowable());
			String screenshotPath = getfailedScreenshot(result.getName());
			childtest.addScreenCaptureFromPath(screenshotPath);
			Logutil.endTestCase("End" + result.getName());

			if (driver != null) {
				driver.quit();
			}

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			Logutil.info("Test case Passed");
			childtest.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " PASSED", ExtentColor.GREEN));
			Logutil.endTestCase("End" + result.getName());
		} else {
			childtest.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " SKIPPED", ExtentColor.ORANGE));
			childtest.skip(result.getThrowable());
			Logutil.endTestCase("End" + result.getName());
		}

		MyScreenRecorder.stopRecording();

	}

	public String getfailedScreenshot(String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots"
		// under src folder
		String destination = Constants.FAILEDSCREENSHOTPATH + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	@AfterClass
	public void teardownclass() {

		if (driver != null) {
			driver.quit();
		} 
	}

	@AfterSuite
	public void tearDownsuite() {
		dbo.dbConnectionclosed();
		extent.flush();

	}

}
