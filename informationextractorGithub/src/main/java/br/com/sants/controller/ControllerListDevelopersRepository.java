package br.com.sants.controller;

import java.util.List;
import java.util.Map;

import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceRepository;
import br.com.sants.util.EventManager;
import br.com.sants.data.DevelopersRepositoryDAO;
import br.com.sants.model.Contributor;
import br.com.sants.model.Repository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerListDevelopersRepository implements Callback<List<Contributor>> {
	//public String repository;
	//private String owner;
	public br.com.sants.util.EventManager events;
	private int count = 0;
	private Repository repo;
	
	public ControllerListDevelopersRepository(Repository repo){
		this.events = new EventManager("getdevelopers", "getrepository");
		this.repo = repo;
	} 
	private int page = 2;
	private String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private String accessToken  = "9ab5ff381ba2c6510a5662b8de793f0b123cb939"; 

    public void start() {
    	int perPage = 100;
    	//events.addObserver("getdevelopers", new ControllerSearchDeveloper());
			  ServiceRepository  serviceRepository = new RetrofitLauncher().getContributorsRepository();
			  Call<List<Contributor>>  call = serviceRepository.listContributorsRepository(accessToken, API_VERSION_SPEC, repo.getOwner().getLogin(), repo.getName(), perPage);
			  //Call<List<Contributor>>  call = serviceRepository.listContributorsRepository(accessToken, API_VERSION_SPEC, repo.getOwner().getLogin(), repo.getName(), page, perPage);
			  call.enqueue(this);
			//  this.count++;
    }

    @Override
    public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
    	System.out.println("URL: " + response.raw().request().url()+ "\n");
    	System.out.println("TOTAL: "+ response.body().size());
    	int val = response.body().size() - 1;
    	List<Contributor> listDeveloper = null;
    	
        if(response.isSuccessful()) {
            listDeveloper = response.body();
			//events.notifyObservers("getdevelopers");
            
        } else {
            System.out.println("Error" + response.errorBody());
        }
        System.out.println("CONTADOR FIM: "+ this.count);
        
        DevelopersRepositoryDAO developersRepositoryDAO = new DevelopersRepositoryDAO();
        developersRepositoryDAO.add(listDeveloper, repo.getName(), repo.getOwner().getLogin());
    }

    @Override
    public void onFailure(Call<List<Contributor>> call, Throwable t) {
        t.printStackTrace();
    }
    
   
}
