package com.Dataprovider;

import org.testng.annotations.DataProvider;

import com.ExtentReportTest;
import com.utility.*;
import com.utility.Exceloperations;

public class DataproviderTest extends ExtentReportTest {

	@DataProvider(name = "getData")
	public Object[][] getTestData() {

		Object data[][] = Exceloperations.getTestData(Constants.TESTDATAFILEPATH, Constants.TESTDATAFILENAME,
				Constants.TESTCASERESULTSHEET);

		Logutil.info("Data Fetch Succesfully!!!");
		return data;
	}
}
