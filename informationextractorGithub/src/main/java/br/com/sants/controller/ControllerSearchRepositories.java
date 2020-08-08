package br.com.sants.controller;

import java.util.HashMap;
import java.util.Map;
import java.io.FileOutputStream;

import br.com.sants.model.Repositories;
import br.com.sants.model.Repository;
import br.com.sants.service.ServiceRepository;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.util.GenerateXLS;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;

public class ControllerSearchRepositories implements Callback<Repositories> {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	private String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private String accessToken  = "9ab5ff381ba2c6510a5662b8de793f0b123cb939"; 

	public void start() {
		workbook = generateXLS.getInstance();
		Map<String, String> paramsMap = new HashMap<String, String>();

		paramsMap.put("sort", "stars");
		paramsMap.put("order", "desc");
		int perPage = 100;

		ServiceRepository repositoryService = new RetrofitLauncher().getRepositories();
		Call<Repositories> call = repositoryService.listRepositories(accessToken, API_VERSION_SPEC, paramsMap, perPage);

		call.enqueue(this);
	}

	@Override
	public void onResponse(Call<Repositories> call, Response<Repositories> response) {
		System.out.println("URL: " + response.raw().request().url() + "\n");
		String filename = "/home/pereira/Documentos/dados_git/searchRepositories.xls";
		int iterator = 1;

		if (response.isSuccessful()) {
			Repositories repositories = response.body();
			sheet = generateXLS.openXLS(rowhead());
			fillLine(repositories, iterator);
			generateXLS.createXLS(filename);
		} else {
			System.out.println("ERROR: " + response.errorBody());
		}
	}

	@Override
	public void onFailure(Call<Repositories> call, Throwable t) {
		t.printStackTrace();
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

	public void fillLine(Repositories repositories, int iterator) {
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
	}
}
