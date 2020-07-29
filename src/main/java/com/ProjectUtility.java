package com;

import java.io.File;

import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.testng.ITestResult;

import com.utility.Constants;
import com.utility.Exceloperations;
import com.utility.Logutil;

public class ProjectUtility {

	// Get Test case Description

	public static String getTestcaseDesc(String filePath, String fileName, String sheetName, String method) {

		File file = Exceloperations.getFile(filePath, fileName);
		FileInputStream inputstream = Exceloperations.getFileinputStream(file);
		String fileext = Exceloperations.getExtension(fileName);
		Workbook book = Exceloperations.getWorkbook(file, inputstream, fileext);
		Sheet sheet = Exceloperations.getSheetbyName(book, sheetName);
		int rowCount = Exceloperations.getLastrowNumber(sheet);
		String testcasename = null;
		String testcasedesc = null;
		for (int i = 1; i < rowCount + 1; i++) {

			testcasename = Exceloperations.getStringvalue(sheet, i, 2);

			if (testcasename.equalsIgnoreCase(method)) {
				testcasedesc = Exceloperations.getStringvalue(sheet, i, 1);
				return testcasedesc;
			}

		}

		testcasedesc = method;
		return testcasedesc;

	}

	// Method to update Test Result in Test case Excel File.
	public static String updateTestresultinExcel(String filePath, String fileName, String sheetName,
			ITestResult result) {

		Logutil.info("Start updateTestresultinExcel");
		File file = Exceloperations.getFile(filePath, fileName);
		FileInputStream inputstream = Exceloperations.getFileinputStream(file);
		String fileext = Exceloperations.getExtension(fileName);
		Workbook book = Exceloperations.getWorkbook(file, inputstream, fileext);
		Sheet sheet = Exceloperations.getSheetbyName(book, sheetName);

		int rowCount = Exceloperations.getLastrowNumber(sheet);
		String testresultcolumnName = Constants.TESTCASERESULTCOLUMNNAME;

		int colindex = Exceloperations.getColumnIndex(filePath, fileName, sheetName, testresultcolumnName);

		String testcasename = null;
		for (int i = 1; i < rowCount + 1; i++) {

			testcasename = Exceloperations.getStringvalue(sheet, i, 2);

			if (testcasename.equalsIgnoreCase(result.getName())) {

				if (result.getStatus() == ITestResult.FAILURE) {

					Exceloperations.writeData(filePath, fileName, sheetName, i, colindex, "Fail");
				} else if (result.getStatus() == ITestResult.SUCCESS) {

					Exceloperations.writeData(filePath, fileName, sheetName, i, colindex, "Pass");
				} else {

					Exceloperations.writeData(filePath, fileName, sheetName, i, colindex, "Skipped");
				}

			}

		}
		Logutil.info("End updateTestresultinExcel");
		return testcasename;
	}

}
