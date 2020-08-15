package br.com.sants.controller;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import br.com.sants.data.DAODevelopers;
import br.com.sants.model.Repositories;
import br.com.sants.model.Repository;
import br.com.sants.model.User;
import br.com.sants.service.ServiceDeveloper;
import br.com.sants.util.GenerateXLS;
import br.com.sants.service.RetrofitLauncher;

public class ControllerSearchDeveloper implements Callback<User>, br.com.sants.util.EventListener{
	private String developer;
	private String API_VERSION_SPEC;
	private String ACCESSTOKEN;
	
	public ControllerSearchDeveloper(){
		this.developer = "github-sheriff";
		this.API_VERSION_SPEC = "application/vnd.github.v3+json";
		this.ACCESSTOKEN  = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	}

	public void start() {
        ServiceDeveloper collaboratorService = new RetrofitLauncher().getUserCollaborator();
        //Substituir parametro estático por variavel que e preenchida com valores buscados da leitura de um arquivo.
        Call<User> call = collaboratorService.getUser(ACCESSTOKEN, API_VERSION_SPEC, developer);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
    	System.out.println("URL: " + response.raw().request().url()+ "\n");
		User developer = null;
		
        if(response.isSuccessful()) {
        	developer = response.body();
        	//System.out.println(developer.toString());
        	
        } else {
            System.out.println("ERROR: "+ response.errorBody());
        }
		DAODevelopers DAODevelopers = new DAODevelopers();
		DAODevelopers.fillLine(developer);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        t.printStackTrace();
    }
    
    @Override
    public void update(String eventType) {
    	start();
    }

}
