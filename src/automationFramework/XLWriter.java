/**
 * 
 */
package automationFramework;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * @author tetambes
 *
 */
public class XLWriter {
	
	private static HSSFWorkbook workbook;	
	// relative location of file + Time stamp based file name (.xls)
	//	String fileName = "test-output\\" + (new SimpleDateFormat("dd-MM-yy--hh_mm_aa").format(Calendar.getInstance().getTime())) + ".xls";
	private static HSSFCell cell;
	
	DateFormat dateFormat = new SimpleDateFormat("dd_MM_yy__hhaa");
	String capture_Date = dateFormat.format(new Date());
	String destDir = "Test Report/Test Results";
	String fileName = capture_Date + ".xls";
	
	
	public void setTestOutputFile() throws Exception
	{
		try {
			workbook = new HSSFWorkbook();
			workbook.createSheet("Result Document");
			HSSFSheet spreadSheet = workbook.getSheet("Result Document");
			HSSFRow row1 = spreadSheet.createRow((short) 0);
			
			// Creating rows with labels for Test Case name and result.
			cell = row1.createCell(0);
			cell.setCellValue(new HSSFRichTextString("Test Name")); 				
			cell = row1.createCell(1);
			cell.setCellValue(new HSSFRichTextString("Test Output"));
			// Creating destination directory
			new File(destDir).mkdirs();
			FileOutputStream file1 = new FileOutputStream(destDir + "/" + fileName); 					// Creating new file
			workbook.write(file1); 																	// printing the label in the new row
			file1.close(); 																			// closing the new file
			System.out.println("The labels are now printed in the excel sheet");
		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
		}
	}
	
	
	public void write_Test_Result(int row, String testCaseName, String testResult) {

		try{
			HSSFSheet spreadSheet = workbook.getSheet("Result Document");
			System.out.println("Sheet: "+spreadSheet.getSheetName());
			if(spreadSheet.isSelected())
			{ 
				HSSFRow row2 = spreadSheet.createRow((short)row);
				// write output and Test Case name.
				cell = row2.createCell(0);
				cell.setCellValue(new HSSFRichTextString(testCaseName));
				cell = row2.createCell(1);
				cell.setCellValue(new HSSFRichTextString(testResult));
			}
			else{
				System.out.println("Row is field with result data.. Please select next row..");
				int newRow = ++row;
				HSSFRow row3 = spreadSheet.createRow((short)newRow);
				cell = row3.createCell(0);
				cell.setCellValue(new HSSFRichTextString(testCaseName));
				cell = row3.createCell(1);
				cell.setCellValue(new HSSFRichTextString(testResult));
			}
			
			// write test result xl file
			FileOutputStream outFile = new FileOutputStream(destDir + "/" + fileName); 							// Creating new file
			workbook.write(outFile); 																			// printing the data in the new file
			outFile.flush();
			outFile.close(); 																					// closing the new file
			System.out.println("The Result are now printed in the excel sheet");
			} 
		catch (Exception e) {
				e.getCause();
				e.printStackTrace();
			}
		}

	
/*	public void write_Cell_data(int row, String testCase, String testResult) {

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			// relative location of file + Time stamp based file name (.xls)
			String fileName = "test-output\\"
					+ (new SimpleDateFormat("dd-MM-yy--hh_mm_aa").format(Calendar.getInstance().getTime())) + ".xls";
			System.out.println(fileName);
			FileOutputStream file1 = new FileOutputStream(new File(fileName));
			HSSFSheet spreadSheet = workbook.createSheet("Result Document");
			HSSFRow row1 = spreadSheet.createRow((short) 0);
			HSSFRow row2 = spreadSheet.createRow((short) row);
			HSSFCell cell;
			// Creating rows and filling them with data
			// Creating labels for Test Case name and result.
			cell = row1.createCell(0);
			cell.setCellValue(new HSSFRichTextString("Test Name")); 				
			cell = row1.createCell(1);
			cell.setCellValue(new HSSFRichTextString("Test Output"));
			
			// write output and Test Case name.
			cell = row2.createCell(0);
			cell.setCellValue(new HSSFRichTextString(testCase));
			cell = row2.createCell(1);
			cell.setCellValue(new HSSFRichTextString(testResult));

			// Please see below and compare
			file1.close(); // Closing the file
			FileOutputStream outFile = new FileOutputStream(new File(fileName)); // Creating new file
			workbook.write(outFile); // printing the data in the new file
			outFile.close(); // closing the new file
			System.out.println("The Result are now printed in the excel sheet");
		} catch (Exception e) {
			e.getCause();
			e.printStackTrace();
		}
	}
*/
	
}
