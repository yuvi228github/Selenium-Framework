package com.utility;

import java.io.File;




import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class Exceloperations {

	static File file;
	static FileInputStream fileinputstream;
	static FileOutputStream fout;
	static Workbook book = null;
	static Sheet sheet;

	// Get new File
	public static File getFile(String filePath, String fileName) {

		file = new File(filePath + File.separator + fileName);

		return file;
	}

	// Get new FileinputStream
	public static FileInputStream getFileinputStream(File file) {

		try {
			fileinputstream = new FileInputStream(file);
		} catch (FileNotFoundException e) {

			Logutil.info(e);
		}

		return fileinputstream;

	}

	// Get new FileOutputStream
	public static FileOutputStream getFileoutputStream(File file) {

		try {
			fout = new FileOutputStream(file);
		} catch (FileNotFoundException e) {

			Logutil.info(e);
		}

		return fout;
	}


	// Get extension of Excel file
		public static String getExtension(String fileName) {

			return fileName.substring(fileName.indexOf('.'));

		}

		// Get Workbook
		public static Workbook getWorkbook(File file, FileInputStream fileinputstream, String fileExtensionName) {

			if (!file.exists()) {
				if (fileExtensionName.equals(".xlsx")) {

					try {
						book = new XSSFWorkbook(fileinputstream);

					} catch (IOException e) {

						Logutil.info(e);
					}
				} else {
					try {
						book = new HSSFWorkbook(fileinputstream);

					} catch (IOException e) {

						Logutil.info(e);
					}
				}
			} else {
				try {
					book = WorkbookFactory.create(fileinputstream);

				} catch (EncryptedDocumentException | IOException e) {

					Logutil.info(e);
				}
			}

			return book;
		}

		// Get sheet
		public static Sheet getSheetbyName(Workbook book, String sheetName) {

			return book.getSheet(sheetName);

		}

		// Get Last Column number of Excel
		public static int getlastcellNumber(Sheet sheet) {

			return sheet.getRow(0).getLastCellNum();

		}

		// Get Last Row number of Excel
		public static int getLastrowNumber(Sheet sheet) {

			return sheet.getLastRowNum();

		}

		public static String getStringvalue(Sheet sheet, int rownumber, int columnNumber) {

			return sheet.getRow(rownumber).getCell(columnNumber).toString();

		}

	// General method to Read all TestData From Excel File
	public static Object[][] getTestData(String filePath, String fileName, String sheetName) {

		file = getFile(filePath, fileName);
		fileinputstream = getFileinputStream(file);
		String fileext = getExtension(fileName);
		book = getWorkbook(file, fileinputstream, fileext);
		sheet = getSheetbyName(book, sheetName);

		int lastNRowNumber = getLastrowNumber(sheet);
		int lastCellNumber = getlastcellNumber(sheet);

		Object[][] data = new Object[lastNRowNumber][lastCellNumber];
		for (int i = 0; i < lastNRowNumber; i++) {
			for (int k = 0; k < sheet.getRow(i).getLastCellNum(); k++) {

				try {
					String header = getStringvalue(sheet, 0, k);

					Row row = sheet.getRow(i + 1);
					Cell cell = row.getCell(k);

					if (!cell.equals("")) {
						if (cell.getCellType() == CellType.STRING) {
							data[i][k] = cell.getStringCellValue();
						} else if (cell.getCellType() == CellType.NUMERIC) {
							data[i][k] = cell.getNumericCellValue();
						}
					}

					Logutil.info("Data of " + header + " is " + data[i][k]);
				}

				catch (Exception e) {

					Logutil.info(e);
				}
			}
		}

		return data;

	}

	// Read Specific row / Column data of Excel file
		public static String readExcel(String filePath, String fileName, String sheetName, int rowNumber,
				int columnNumber) {
			file = getFile(filePath, fileName);
			fileinputstream = getFileinputStream(file);
			String fileext = getExtension(fileName);
			book = getWorkbook(file, fileinputstream, fileext);
			sheet = getSheetbyName(book, sheetName);

			return getStringvalue(sheet, rowNumber, columnNumber);

		}



		public static void writeData(String filePath, String fileName, String sheetName, int rownum, int colnum,
				String cellvalue) {
			file = getFile(filePath, fileName);
			fileinputstream = getFileinputStream(file);
			String fileext = getExtension(fileName);
			book = getWorkbook(file, fileinputstream, fileext);
			sheet = getSheetbyName(book, sheetName);

			Row row = sheet.getRow(rownum);
			Cell cell = row.createCell(colnum);
			cell.setCellValue(cellvalue);

			FileOutputStream fout = getFileoutputStream(file);

			try {
				book.write(fout);
				book.close();

			} catch (IOException e) {

				Logutil.info(e);

			}

		}
		
		// Get column Index as per Column Title
		public static int getColumnIndex(String filePath, String fileName, String sheetName, String columnHeader) {

			file = getFile(filePath, fileName);
			fileinputstream = getFileinputStream(file);
			String fileext = getExtension(fileName);
			book = getWorkbook(file, fileinputstream, fileext);
			sheet = getSheetbyName(book, sheetName);

			int lastcell = Exceloperations.getlastcellNumber(sheet);
			int matchcolindex = 0;

			for (int col = 0; col < lastcell; col++) {

				String mdval = Exceloperations.getStringvalue(sheet, 0, col).toLowerCase();

				if (mdval.equalsIgnoreCase(columnHeader)) {
					matchcolindex = col;

				}

			}

			return matchcolindex;

		}


}
