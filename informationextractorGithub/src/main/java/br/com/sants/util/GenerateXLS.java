package br.com.sants.util;

import java.io.FileOutputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class GenerateXLS {
	private static HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow rowhead;

	public GenerateXLS() {
		workbook = new HSSFWorkbook();
	}
	
    public static HSSFWorkbook getInstance() {
        return workbook;
    }

	public HSSFSheet openXLS(Map<Integer, String> rowheadValue, String repositories) {
		try {
			sheet = workbook.createSheet(repositories);
			rowhead = sheet.createRow((short) 0);

			for (Map.Entry m : rowheadValue.entrySet()) {
				rowhead.createCell(m.getKey().hashCode()).setCellValue(m.getValue().toString());
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return sheet;
	}

	public void createXLS(String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(filename);

			workbook.write(fileOut);
			fileOut.close();

			System.out.println("XLS gerado!");
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}

}
