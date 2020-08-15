package br.com.sants.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.com.sants.model.Contributor;
import br.com.sants.model.Owner;
import br.com.sants.model.Repository;
import br.com.sants.util.GenerateXLS;

public class DAOListDevelopersRepository {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private static HSSFSheet sheet = null;
	private HSSFRow row;
	private static int iterator = 1;
	public String repository;
	private String owner;
	private List<Contributor> listDeveloper;
	
	public DAOListDevelopersRepository(String repository, String owner, List<Contributor> listDeveloper) {
		this.repository = repository;
		this.owner = owner;
		workbook = generateXLS.getInstance();
		defineColumns();
		this.listDeveloper = listDeveloper;
	}
	
	public void saveKeyCommits() {
		String filename = "/home/pereira/Documentos/dados_git/repo/listDevelopersRepository.xls";
		generateXLS.createXLS(filename);
	}
	
	public void defineColumns() {
		if (sheet == null)
			sheet = generateXLS.openXLS(rowhead(), this.repository);
	}
	
	 public void fillLine() {
			for (Contributor developer : listDeveloper) {
				row = sheet.createRow((short) iterator);
				
				row.createCell(0).setCellValue(this.repository);
				row.createCell(1).setCellValue(developer.getId());
				row.createCell(2).setCellValue(developer.getLogin());
				row.createCell(3).setCellValue(developer.getContributions());
				row.createCell(4).setCellValue(this.owner);

				iterator++;
			}
			saveKeyCommits();
		}
	    
	    public Map<Integer, String> rowhead() {
			Map<Integer, String> map = new HashMap<Integer, String>();
			
			map.put(0, "Repository");
			map.put(1, "Identifier");
			map.put(2, "Developer");
			map.put(3, "Contributions");
			map.put(4, "Owner");

			return map;
		}
	    
	 // Ler arquivos com os lista de desenvolvedores de um projeto
		public Map<String, String> readFileDevelopersRepository(String fileName) {
			Map<String, String> mapaRepository = new HashMap<String, String>();
			try {
				FileInputStream arquivo = new FileInputStream(new File(fileName));
				HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
				HSSFSheet sheetCommits = workbook.getSheetAt(0);

				Iterator<Row> rowIterator = sheetCommits.iterator();

				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Iterator<Cell> cellIterator = row.cellIterator();

					Repository repository = new Repository();

					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getColumnIndex()) {
						case 1:
							repository.setName(cell.getStringCellValue());
							// System.out.println(cell.getCellType());
							// Chamar método para escrever xls com nova tabela de dados de um contribuitor

							break;
						case 2:
							Owner owner = new Owner();
							owner.setLogin(cell.getStringCellValue());
							repository.setOwner(owner);
							break;
						}
					}
				}
				arquivo.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Arquivo Excel não encontrado!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return mapaRepository;

		}
}
