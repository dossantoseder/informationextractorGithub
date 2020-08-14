package br.com.sants.controller;

import java.util.List;

import br.com.sants.data.DAOCommitKey;
import br.com.sants.model.Commit;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceCommit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerSearchCommitDeveloper implements Callback<List<Commit>> {
	private final String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private final String ACCESSTOKEN = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	private String author;//Deve vim da lista de desenvolvedores do repositório
	private String owner;
	private String repo;
	private DAOCommitKey facadeCommitKey;
	
	 public ControllerSearchCommitDeveloper(String owner, String repo, String author){
		 this.owner = owner;
		 this.repo = repo;
		 this.author = author;
	 }

	public void start() {
		int perPage = 100;

		ServiceCommit serviceCommit = new RetrofitLauncher().getServiceCommit();
		Call<List<Commit>> call = serviceCommit.listCommitAuthor(ACCESSTOKEN, API_VERSION_SPEC, this.owner, this.repo, this.author,
				perPage);
		call.enqueue(this);

	}

	 @Override
	    public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
	    	System.out.println("URL: " + response.raw().request().url()+ "\n");
	    	List<Commit> listCommitDeveloper = null;
	    	System.out.println("TOTAL: "+ response.body().size());
	        if(response.isSuccessful()) {
	           listCommitDeveloper = response.body();
	            //listRepositoriesDeveloper.forEach(listRepositories -> System.out.println(listRepositories.toString()));
	        } else {
	            System.out.println("Error" + response.errorBody());
	        }
	        facadeCommitKey = new DAOCommitKey(listCommitDeveloper, owner, repo);
	        facadeCommitKey.fillLine();
	    }

	    @Override
	    public void onFailure(Call<List<Commit>> call, Throwable t) {
	        t.printStackTrace();
	    }
	 
}
