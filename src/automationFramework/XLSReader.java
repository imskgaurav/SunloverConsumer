package automationFramework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;




public class XLSReader {
  	

public static class XlUtil {

		private static HSSFSheet ExcelWSheet;

		private static HSSFWorkbook ExcelWBook;

		private static  HSSFCell Cell;

		private static  HSSFRow Row;

	//This method is to set the File path and to open the Excel file, Pass Excel Path and Sheetname as Arguments to this method

	public void setExcelFile(String Path,String SheetName) throws Exception {

			try {

   			// Open the Excel file

			FileInputStream ExcelFile = new FileInputStream(Path);

			// Access the required test data sheet

			ExcelWBook = new HSSFWorkbook(ExcelFile);

			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			} catch (Exception e){

				throw (e);

			}

	}

	//This method is to read the test data from the Excel cell, in this we are passing parameters as Row num and Col num

    public String getCellData(int RowNum, int ColNum) throws Exception{

			try{
				
			String CellData = "";
  			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);

  			/*String CellData = Cell.getStringCellValue();
  			System.out.println("Cell Data value: "+CellData);*/
  			
			if (Cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				CellData = String.format("%.0f", Cell.getNumericCellValue());
			}

			if (Cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
				CellData = String.format("%s", Cell.getRichStringCellValue());
			}

  			return CellData;

  			}catch (Exception e){

				return"";

  			}

    }

	//This method is to write in the Excel cell, Row num and Col num are the parameters

	public void setCellData(String Result,  int RowNum, int ColNum) throws Exception	{

			try{
				ExcelWSheet = ExcelWBook.getSheet("Output");

				Row  = ExcelWSheet.getRow(RowNum);

				Cell = Row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.RETURN_BLANK_AS_NULL);

				if (Cell == null) {

					Cell = Row.createCell(ColNum);
	
					Cell.setCellValue(Result);

				} else {

					Cell.setCellValue(Result);

				}

  // Constant variables Test Data path and Test Data file name

  				FileOutputStream fileOut = new FileOutputStream(EnvConfiguration.Path_TestData + EnvConfiguration.File_TestData);

  				ExcelWBook.write(fileOut);

  				fileOut.flush();

					fileOut.close();

				}catch(Exception e){

					throw (e);

			}

		}
	
	
	// This method is to set Excel cell Type format
	@SuppressWarnings("static-access")
	public static void setCellType(String Path, String SheetName) throws IOException {

		FileInputStream fis = new FileInputStream(Path);

		// Finds the workbook instance for XLSX file
		HSSFWorkbook myWorkBook = new HSSFWorkbook(fis);

		// Return first sheet from the XLSX workbook
		HSSFSheet mySheet = myWorkBook.getSheet(SheetName);

		// Get iterator to all the rows in current sheet
		Iterator<org.apache.poi.ss.usermodel.Row> rowIterator = mySheet.iterator();

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			org.apache.poi.ss.usermodel.Row row = rowIterator.next();

			// For each row, iterate through each columns
			Iterator<org.apache.poi.ss.usermodel.Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {

				org.apache.poi.ss.usermodel.Cell cell = cellIterator.next();
				int cellType = cell.getCellType();

				if (cellType == Cell.CELL_TYPE_STRING) {
					System.out.print(cell.getStringCellValue() + "\t");
				} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
					System.out.print(cell.getNumericCellValue() + "\t");
				} else if (cellType == Cell.CELL_TYPE_STRING) {
					System.out.print(cell.getStringCellValue() + "\t");
				} else {
					System.out.println("");
				}
			}
		}
	}

}

}
