package br.com.sants.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.sants.data.RepositoriesDeveloperDAO;
import br.com.sants.model.Contributor;
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
	private final String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private final String ACCESSTOKEN = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	private GenerateXLS generateXLS = new GenerateXLS();
	private HSSFWorkbook workbook;
	private HSSFSheet sheet = null;
	private HSSFRow row;
	//private int page = 2;
	private Contributor developer;
	
	
	public ControllerListRepositoriesDeveloper(Contributor d) {
		this.developer = d;
		
	}
	
    public void start() {//método deve receber uma lista ou map(repositorio, desenvolvedor) de desenvolvedores de um projeto
    	workbook = generateXLS.getInstance();
    	int perPage = 100;
	
    	ServiceDeveloper serviceUser = new RetrofitLauncher().getUserCollaborator();
        Call<List<Repository>>  call = serviceUser.listRepositoriesDeveloper(ACCESSTOKEN, API_VERSION_SPEC, developer.getLogin(), perPage);
        //Call<List<Repository>>  call = serviceUser.listRepositoriesDeveloper(login, page,perPage);
        call.enqueue(this);

    }

    @Override
    public void onResponse(Call<List<Repository>> call, Response<List<Repository>> response) {
    	System.out.println("URL: " + response.raw().request().url()+ "\n");
    	//String filename = "/home/pereira/Documentos/dados_git/repositories/rn-contact/"+login+"_listRepositoriesDeveloper.xls";
    	//String filename = "/home/pereira/Documentos/dados_git/repositories/covsense/"+login+"_2_listRepositoriesDeveloper.xls";
    	int iterator = 1;
    	//System.out.println("TOTAL: "+ response.body().size());
    	List<Repository> listRepositoriesDeveloper = null;
        if(response.isSuccessful()) {
             listRepositoriesDeveloper = response.body();
            //listRepositoriesDeveloper.forEach(listRepositories -> System.out.println(listRepositories.toString()));
        } else {
            System.out.println("Error" + response.errorBody());
        }
        RepositoriesDeveloperDAO repositoriesDeveloperDAO = new RepositoriesDeveloperDAO();
        repositoriesDeveloperDAO.add(listRepositoriesDeveloper, this.developer);
        /*sheet = generateXLS.openXLS(rowhead(), "Repositories");
		fillLine(listRepositoriesDeveloper, iterator);
		generateXLS.createXLS(filename);*/
    }

    @Override
    public void onFailure(Call<List<Repository>> call, Throwable t) {
        t.printStackTrace();
    }
    


}