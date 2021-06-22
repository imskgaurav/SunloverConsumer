package utilities;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

public class LockCell {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Test");

		Row row = sheet.createRow(0);

		CellStyle style = wb.createCellStyle();
		style.setLocked(true);
		Cell cell = row.createCell(0);
		cell.setCellStyle(style);
		sheet.protectSheet("");

	}

}
