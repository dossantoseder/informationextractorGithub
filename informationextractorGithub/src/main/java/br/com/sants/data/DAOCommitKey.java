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

import br.com.sants.model.Commit;
import br.com.sants.model.Owner;
import br.com.sants.model.Repository;
import br.com.sants.util.GenerateXLS;

public class DAOCommitKey {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private static HSSFSheet sheet = null;
	private HSSFRow row;
	List<Commit> listCommitDeveloper;
	private static int iterator = 1;
	private String owner;
	private String repo;

	public DAOCommitKey(List<Commit> listCommitDeveloper, String owner, String repo) {
		this.listCommitDeveloper = listCommitDeveloper;
		this.owner = owner;
		this.repo = repo;
		workbook = generateXLS.getInstance();
		defineColumns();
	}

	public void saveKeyCommits() {
		String filename = "/home/pereira/Documentos/dados_git/commit/listCommitDeveloper.xls";
		generateXLS.createXLS(filename);
	}

	public Map<Integer, String> rowhead() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(0, "Developer");
		map.put(1, "Author");
		map.put(2, "Sha");
		map.put(3, "Owner");
		map.put(4, "Repository");

		return map;
	}

	public void fillLine() {
		for (Commit commit : listCommitDeveloper) {
			row = sheet.createRow((short) this.iterator);

			row.createCell(0).setCellValue(commit.getAuthor().getId());
			row.createCell(1).setCellValue(commit.getAuthor().getLogin());
			row.createCell(2).setCellValue(commit.getSha());
			row.createCell(3).setCellValue(owner);
			row.createCell(4).setCellValue(repo);

			this.iterator++;
		}
		saveKeyCommits();
	}

	public void defineColumns() {
		if (sheet == null)
			sheet = generateXLS.openXLS(rowhead(), "Commitment");
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
