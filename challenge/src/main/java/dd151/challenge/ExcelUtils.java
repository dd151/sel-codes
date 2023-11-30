package dd151.challenge;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	private final String path = System.getProperty("user.dir") + "//target//testOutput.xlsx";

	public void writeDataToFile(String sheetName, List<ArrayList<String>> data) {
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName);
		XSSFFont font = wb.createFont();
		font.setBold(true);
		XSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
		cellStyle.setFont(font);

		for (int i = 0; i < data.size(); i++) {
			Row row = sheet.createRow(i);
			for (int j = 0; j < data.get(0).size(); j++) {
				Cell cell = row.createCell(j);
				if (i == 0)
					cell.setCellStyle(cellStyle);
				cell.setCellValue(data.get(i).get(j));
			}
		}
		try {
			FileOutputStream fis = new FileOutputStream(new File(path));
			wb.write(fis);
			wb.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
