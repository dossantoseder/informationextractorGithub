package br.com.sants.controller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.sants.model.Repositories;
import br.com.sants.model.Repository;
import br.com.sants.model.User;
import br.com.sants.service.ServiceDeveloper;
import br.com.sants.util.GenerateXLS;
import br.com.sants.service.RetrofitLauncher;

public class ControllerSearchDeveloper implements Callback<User>, br.com.sants.util.EventListener{
	private GenerateXLS generateXLS;
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	String developer;
	private String API_VERSION_SPEC;
	private String accessToken;
	
	public ControllerSearchDeveloper(){
		//generateXLS = new GenerateXLS();
		
		workbook = new HSSFWorkbook();
		generateXLS = new GenerateXLS(workbook);
		
		developer = "github-sheriff";
		API_VERSION_SPEC = "application/vnd.github.v3+json";
		accessToken  = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	}

	public void start() {
        ServiceDeveloper collaboratorService = new RetrofitLauncher().getUserCollaborator();
        //Substituir parametro estático por variavel que e preenchida com valores buscados da leitura de um arquivo.
        Call<User> call = collaboratorService.getUser(accessToken, API_VERSION_SPEC, developer);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
    	System.out.println("URL: " + response.raw().request().url()+ "\n");
    	String filename = "/home/pereira/Documentos/dados_git/developer/"+ developer+ "_searchDeveloper.xls";
		int iterator = 1;
		User developer = null;
		
        if(response.isSuccessful()) {
        	developer = response.body();
        	//System.out.println(developer.toString());
        	
        } else {
            System.out.println("ERROR: "+ response.errorBody());
        }
        sheet = generateXLS.openXLS(rowhead(), "Developers");
		fillLine(developer, iterator);
		generateXLS.createXLS(filename);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }
    
    public Map<Integer, String> rowhead() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(0, "Identifier");
		map.put(1, "Developer");
		map.put(2, "Number repository");
		map.put(3, "Created at");
		map.put(4, "Updated at");

		return map;
	}
    
    public void fillLine(User developer, int iterator) {
			row = sheet.createRow((short) iterator);

			row.createCell(0).setCellValue(developer.getId());
			row.createCell(1).setCellValue(developer.getLogin());
			row.createCell(2).setCellValue(developer.getPublic_repos());
			row.createCell(3).setCellValue(developer.getCreated_at());
			row.createCell(4).setCellValue(developer.getUpdated_at());
	}
    
    @Override
    public void update(String eventType) {
    	start();
    }

}
