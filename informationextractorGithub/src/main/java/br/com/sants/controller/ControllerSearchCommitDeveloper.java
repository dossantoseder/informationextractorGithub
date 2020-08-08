package br.com.sants.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.sants.model.Commit;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceCommit;
import br.com.sants.util.GenerateXLS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerSearchCommitDeveloper implements Callback<List<Commit>> {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	private String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private String accessToken = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	String author = "fiqryq";

	public void start() {
		workbook = generateXLS.getInstance();
		String owner = "fiqryq";
		String repo = "Pantaucovid";

		int perPage = 100;

		ServiceCommit serviceCommit = new RetrofitLauncher().getServiceCommit();
		Call<List<Commit>> call = serviceCommit.listCommitAuthor(accessToken, API_VERSION_SPEC, owner, repo, author,
				perPage);
		call.enqueue(this);

	}

	 @Override
	    public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
	    	System.out.println("URL: " + response.raw().request().url()+ "\n");
	    	String filename = "/home/pereira/Documentos/dados_git/commit/"+ author+ "_listCommitDeveloper.xls";
	    	int iterator = 1;
	    	System.out.println("TOTAL: "+ response.body().size());
	        if(response.isSuccessful()) {
	            List<Commit> listCommitDeveloper = response.body();
	            sheet = generateXLS.openXLS(rowhead());
				fillLine(listCommitDeveloper , iterator);
				generateXLS.createXLS(filename);
	            //listRepositoriesDeveloper.forEach(listRepositories -> System.out.println(listRepositories.toString()));
	        } else {
	            System.out.println("Error" + response.errorBody());
	        }
	    }

	    @Override
	    public void onFailure(Call<List<Commit>> call, Throwable t) {
	        t.printStackTrace();
	    }
	    
	    public Map<Integer, String> rowhead() {
			Map<Integer, String> map = new HashMap<Integer, String>();

			map.put(0, "Sha");
			map.put(1, "Url");
			map.put(2, "Author");

			return map;
		}
	    
	    public void fillLine(List<Commit> listCommitDeveloper, int iterator) {
			for (Commit commit : listCommitDeveloper) {
				row = sheet.createRow((short) iterator);

				row.createCell(0).setCellValue(commit.getSha());
				row.createCell(1).setCellValue(commit.getUrl());
				row.createCell(2).setCellValue(commit.getAuthor().getLogin());

				iterator++;
			}
		}

}
