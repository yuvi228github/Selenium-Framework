Technology : Java (8 or above)
Test Management : TestNG 
Automation Tool : Selenium
Build Tool: Maven
Framework : POM (Page Object Model )
Test Reporting : Extent Report

Other Utilities : 
Excel and Property file reader to fetch data from (.xls , .xlsx , .properties).
Implement project utility to define project specific common methods (To pass/Fail in Testcase excel file ).
Event Listner to highlight element while perform operation like (click, getText , sendkeys).
Constants to declare resourse file path or filenames.
DBOperation to connect Database and perform query.
Logutil for loggers.
Util for generic methods for click, wait, sendkeys, refresh, int-string conversion, scroll, sendmail , takescreenshots.
MyscreenRecorder to Record Testcase execution.

================================================================
Execution should Start from POM.XML 

1.> Declare xml file which use in test management usually (testng.xml)
2.> Configure testng.xml file as per required test execution.
3.> Define Constants like Testdatafile path, filename, sheetname , Columntitle
4.>	Add require data in Test data.
	Add require data in Test case file. (To update Test result in excel , need to give column title in Constant.java file)
	Need to Write Testcase method name in Testcase file under 'Method Name' column.
	Add require data in properties file. (For URL , Browser )
5.> Beforesuite, AfterSuite, Before method , After Method declare in ExtentReportTest class.
6.> Every pageclass should extend Baseclass to fetch driver instance.
7.> Every testclass should extend ExtentReportTest to generate ExtentReport of Testcase execution.
