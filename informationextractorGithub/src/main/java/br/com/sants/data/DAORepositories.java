package br.com.sants.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import br.com.sants.model.Owner;
import br.com.sants.model.Repositories;
import br.com.sants.model.Repository;
import br.com.sants.util.GenerateXLS;

public class DAORepositories {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private HSSFRow row;
	List<String> kOwner;
	private static HSSFSheet sheet = null;
	private static int iterator = 1;
	private Repositories repositories;
	
	public DAORepositories() {
		kOwner = new ArrayList<String>();
	}
	
	public DAORepositories(Repositories repositories) {
		this.repositories = repositories;
		kOwner = new ArrayList<String>();
		workbook = generateXLS.getInstance();
		defineColumns();
	}
	
	public void saveKeyCommits() {
		String filename = "/home/pereira/Documentos/dados_git/searchRepositories.xls";
		generateXLS.createXLS(filename);
	}
	
	public void defineColumns() {
		if (sheet == null)
			sheet = generateXLS.openXLS(rowhead(), "Repositoriesinit");
	}
	
	public Map<Integer, String> rowhead() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(0, "Identifier");
		map.put(1, "Name");
		map.put(2, "Created at");
		map.put(3, "Language");
		map.put(4, "Stargazers count");
		map.put(5, "Owner");
		map.put(6, "Commits_url");
		map.put(7, "Downloads URL");
		map.put(8, "HTML UR");
		map.put(9, "Contributors URL");

		return map;
	}
	
	public void fillLine() {
		for (Repository repository : repositories.getItems()) {
			// System.out.print(repository.toString());
			row = sheet.createRow((short) iterator);

			row.createCell(0).setCellValue(repository.getId());
			row.createCell(1).setCellValue(repository.getName());
			row.createCell(2).setCellValue(repository.getCreated_at());
			row.createCell(3).setCellValue(repository.getLanguage());
			row.createCell(4).setCellValue(repository.getStargazers_count());
			row.createCell(5).setCellValue(repository.getOwner().getLogin());
			row.createCell(6).setCellValue(repository.getCommits_url());
			row.createCell(7).setCellValue(repository.getDownloads_url());
			row.createCell(8).setCellValue(repository.getHtml_url());
			row.createCell(9).setCellValue(repository.getContributors_url());

			iterator++;
		}
		saveKeyCommits();
	}
	
	// Ler arquivos com os projetos dos softwares
		public Map<String, String> readFileRepository(String fileName) {
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
					if ((!repository.getName().equals("Name")) && (!repository.getName().equals(""))) {
						mapaRepository.put(repository.getOwner().getLogin(), repository.getName());
						kOwner.add(repository.getOwner().getLogin());
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
