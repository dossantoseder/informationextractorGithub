package br.com.sants.util;

import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class GenerateXLS {
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow rowhead;

	public GenerateXLS() {
		workbook = new HSSFWorkbook();
	}

	public GenerateXLS(HSSFWorkbook workbook) {
		this.workbook = workbook;
	}

	public static HSSFWorkbook getInstance() {
		return null;
	}

	public HSSFSheet openXLS(Map<Integer, String> rowheadValue, String repositories) {
		try {
			sheet = workbook.createSheet(repositories);
			rowhead = sheet.createRow((short) 0);

			for (Map.Entry m : rowheadValue.entrySet()) {
				//System.out.println("Dados: " + m.getValue().toString());
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

	public void readFile(HSSFSheet mySheet, int index) {
		Iterator<Row> rowIterator = mySheet.iterator(); // Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next(); // For each row, iterate through each columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (cell.getColumnIndex() == index)// for example of c
				{
					System.out.println("done");
				}
			}
		}
	}

}
