package br.com.sants.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.sants.model.Commit;
import br.com.sants.model.Contributor;
import br.com.sants.model.User;
import br.com.sants.util.GenerateXLS;

public class DAODevelopers {
	//private GenerateXLS generateXLS = new GenerateXLS();
	private GenerateXLS generateXLS;
	private HSSFWorkbook workbook;
	private static HSSFSheet sheet = null;
	private HSSFRow row;
	private static int iterator = 1;
	
	public DAODevelopers() {
		workbook = new HSSFWorkbook();
		generateXLS = new GenerateXLS(workbook);
		defineColumns();
	}
	
	public void saveKeyCommits() {
		String filename = "/home/pereira/Documentos/dados_git/developer/searchDeveloper.xls";
		generateXLS.createXLS(filename);
	}
	
	public void defineColumns() {
		if (sheet == null)
			sheet = generateXLS.openXLS(rowhead(), "Developers");
	}
	
	 public Map<Integer, String> rowhead() {
			Map<Integer, String> map = new HashMap<Integer, String>();

			map.put(0, "Identifier");
			map.put(1, "Login");
			map.put(2, "Number repository");
			map.put(3, "Created at");
			map.put(4, "Updated at");

			return map;
		}
	    
	    public void fillLine(User developer) {
				row = sheet.createRow((short) this.iterator);

				row.createCell(0).setCellValue(developer.getId());
				row.createCell(1).setCellValue(developer.getLogin());
				row.createCell(2).setCellValue(developer.getPublic_repos());
				row.createCell(3).setCellValue(developer.getCreated_at());
				row.createCell(4).setCellValue(developer.getUpdated_at());
				this.iterator++;
				saveKeyCommits();
		}
		

}
