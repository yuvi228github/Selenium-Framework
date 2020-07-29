package com.utility;

import java.io.File;

public class Constants {

	public static final String URL = "http://www.google.com";
	public static final String DBURL = "";
	public static final String FILESEPARATOR = File.separator;
	static final String USERDIR = "user.dir";
	private static final String USERDIRPATH = System.getProperty(USERDIR) + FILESEPARATOR;
	public static final String TESTRESOURCEPATH = USERDIRPATH + "src" + FILESEPARATOR + "test" + FILESEPARATOR
			+ "resources" + FILESEPARATOR;
	public static final String MAINRESOURCEPATH = USERDIRPATH + "src" + FILESEPARATOR + "main" + FILESEPARATOR
			+ "resources" + FILESEPARATOR;

	public static final String TESTCASEFILEPATH = TESTRESOURCEPATH;
	public static final String TESTCASERESULTSHEET = "testcasesresult";
	public static final String TESTCASEFILENAME = "Testcases.xlsx";
	public static final String TESTCASERESULTCOLUMNNAME = "Test Result";
	public static final String TESTDATAFILEPATH = TESTRESOURCEPATH;
	public static final String TESTDATAFILENAME = "TestData.xls";

	public static final String EXTENTREPORT = USERDIRPATH + "extentreport.html";
	public static final String TESTSCREENSHOTPATH = USERDIRPATH + "TestScreenshots" + FILESEPARATOR;
	public static final String FAILEDSCREENSHOTPATH = USERDIRPATH + "FailedTestsScreenshots" + FILESEPARATOR;

	public static final String EDGEDRIVERPATH = USERDIRPATH + "MicrosoftWebDriver.exe";
	
	public static final String CONFIGPROPERTYFILE = "config.properties";

}
