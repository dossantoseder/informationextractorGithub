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
		int iterator = 1;
		Repositories repositories = null;

		if (response.isSuccessful()) {
			repositories = response.body();
			
		} else {
			System.out.println("ERROR: " + response.errorBody());
		}
	}

	@Override
	public void onFailure(Call<Repositories> call, Throwable t) {
		t.printStackTrace();
	}
}
