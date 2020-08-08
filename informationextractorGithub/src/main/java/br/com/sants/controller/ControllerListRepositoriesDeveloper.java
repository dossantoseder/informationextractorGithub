package br.com.sants.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.sants.model.Repositories;
import br.com.sants.model.Repository;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceDeveloper;
import br.com.sants.util.GenerateXLS;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *Lista os repositŕorio de um desenvolvedor 
 **/
public class ControllerListRepositoriesDeveloper implements Callback<List<Repository>> {
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	//private int page = 2;
	
	private String login = "github-sheriff";
	
    public void start() {//método deve receber uma lista ou map(repositorio, desenvolvedor) de desenvolvedores de um projeto
    	workbook = generateXLS.getInstance();
    	int perPage = 100;
	
    	ServiceDeveloper serviceUser = new RetrofitLauncher().getUserCollaborator();
        Call<List<Repository>>  call = serviceUser.listRepositoriesDeveloper(login, perPage);
        //Call<List<Repository>>  call = serviceUser.listRepositoriesDeveloper(login, page,perPage);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
    	System.out.println("URL: " + response.raw().request().url()+ "\n");
    	String filename = "/home/pereira/Documentos/dados_git/repositories/rn-contact/"+login+"_listRepositoriesDeveloper.xls";
    	//String filename = "/home/pereira/Documentos/dados_git/repositories/covsense/"+login+"_2_listRepositoriesDeveloper.xls";
    	int iterator = 1;
    	System.out.println("TOTAL: "+ response.body().size());
        if(response.isSuccessful()) {
            List<Repository> listRepositoriesDeveloper = response.body();
            sheet = generateXLS.openXLS(rowhead());
			fillLine(listRepositoriesDeveloper, iterator);
			generateXLS.createXLS(filename);
            //listRepositoriesDeveloper.forEach(listRepositories -> System.out.println(listRepositories.toString()));
        } else {
            System.out.println("Error" + response.errorBody());
        }
    }

    @Override
    public void onFailure(Call<List<Repository>> call, Throwable t) {
        t.printStackTrace();
    }
    
    public Map<Integer, String> rowhead() {
		Map<Integer, String> map = new HashMap<Integer, String>();

		map.put(0, "Identifier");
		map.put(1, "Name");
		map.put(2, "Language");
		map.put(3, "Owner");

		return map;
	}
    
    public void fillLine(List<Repository> listRepositoriesDeveloper, int iterator) {
		for (Repository repository : listRepositoriesDeveloper) {
			row = sheet.createRow((short) iterator);

			row.createCell(0).setCellValue(repository.getId());
			row.createCell(1).setCellValue(repository.getName());
			row.createCell(2).setCellValue(repository.getLanguage());
			row.createCell(3).setCellValue(repository.getOwner().getLogin());

			iterator++;
		}
	}

}