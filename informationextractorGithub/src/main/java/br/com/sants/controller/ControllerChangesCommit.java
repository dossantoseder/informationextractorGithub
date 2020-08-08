package br.com.sants.controller;

import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import br.com.sants.model.Author;
import br.com.sants.model.Commit;
import br.com.sants.model.CommitChanges;
import br.com.sants.model.Files;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceCommit;
import br.com.sants.util.GenerateXLS;

public class ControllerChangesCommit implements Callback<CommitChanges> {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	private String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private String accessToken = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	String ref;
	private String language = ".java";
	private static final String fileName = "/home/pereira/Documentos/dados_git/commit/nome_arquivo.xls";
	Map<String, String> mapaNomes = new HashMap<String, String>();

	public void start() {
		workbook = generateXLS.getInstance();
		String owner = "fiqryq";
		String repo = "Pantaucovid";
		int perPage = 100;
		ref = "fa9405848bf9e306a01a101fc1c1ece1b698ec07";

		ServiceCommit serviceCommit = new RetrofitLauncher().getServiceCommit();
		Call<CommitChanges> call = serviceCommit.referenceCommit(accessToken, API_VERSION_SPEC, owner, repo, ref,
				perPage);
		call.enqueue(this);
	}

	@Override
	public void onResponse(Call<CommitChanges> call, Response<CommitChanges> response) {
		System.out.println("URL: " + response.raw().request().url() + "\n");
		String filename = "/home/pereira/Documentos/dados_git/commit/" + ref + "_.xls";
		int iterator = 1;

		if (response.isSuccessful()) {
			CommitChanges commitChanges = response.body();
			sheet = generateXLS.openXLS(rowhead());
			fillLine(commitChanges, iterator);
			generateXLS.createXLS(filename);
		} else {
			System.out.println("ERROR: " + response.errorBody());
		}
	}

	@Override
	public void onFailure(Call<CommitChanges> call, Throwable t) {
		t.printStackTrace();
	}

	public Map<Integer, String> rowhead() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(0, "Changes");
		map.put(1, "Status");
		map.put(2, "Filename");
		map.put(3, "SHA");
		map.put(4, "Author");

		return map;
	}

	public void fillLine(CommitChanges commitChanges, int iterator) {
		for (Files changes : commitChanges.getFiles()) {
			row = sheet.createRow((short) iterator);
			if (changes.getFilename().toLowerCase().contains(language.toLowerCase())) {
				row.createCell(0).setCellValue(changes.getChanges());
				row.createCell(1).setCellValue(changes.getStatus());
				row.createCell(2).setCellValue(changes.getFilename());
				row.createCell(3).setCellValue(commitChanges.getSha());
				row.createCell(4).setCellValue(commitChanges.getAuthor().getId());
			}
			if (changes.getFilename().toLowerCase().contains(language.toLowerCase()))
				iterator++;
		}
	}

	public Map<String, String> openXLS() throws IOException {
		try {
			FileInputStream arquivo = new FileInputStream(new File(fileName));
			HSSFWorkbook workbook = new HSSFWorkbook(arquivo);
			HSSFSheet sheetAlunos = workbook.getSheetAt(0);

			Iterator<Row> rowIterator = sheetAlunos.iterator();

			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();

				Commit commit = new Commit();

				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getColumnIndex()) {
					case 0:
						commit.setSha(cell.getStringCellValue());
						break;
					case 2:
						Author author = new Author();
						author.setLogin(cell.getStringCellValue());
						commit.setAuthor(author);
						break;
					}
				}
				if(!commit.getSha().equals("Sha"))
				mapaNomes.put(commit.getSha(), commit.getAuthor().getLogin());
			}
			arquivo.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Arquivo Excel não encontrado!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapaNomes;

	}

}
