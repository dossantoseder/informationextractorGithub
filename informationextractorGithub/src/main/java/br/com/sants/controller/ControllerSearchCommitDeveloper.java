package br.com.sants.controller;

import java.util.List;

import br.com.sants.data.CommitKeyDAO;
import br.com.sants.model.Commit;
import br.com.sants.model.Contributor;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceCommit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControllerSearchCommitDeveloper implements Callback<List<Commit>> {
	private Contributor developer;
	 public ControllerSearchCommitDeveloper(Contributor d){
		 this.developer = d;
	 }

	public void start() {
		int perPage = 100;
		int page = 1;

		ServiceCommit serviceCommit = new RetrofitLauncher().getServiceCommit();
		Call<List<Commit>> call = serviceCommit.listCommitAuthor(this.developer.getOwner(), this.developer.getRepository(), this.developer.getLogin(),
				page, perPage);
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
	       /* facadeCommitKey = new DAOCommitKey(listCommitDeveloper, owner, repo, numConsultations);
	        facadeCommitKey.fillLine();*/
	        CommitKeyDAO commitKeyDAO = new CommitKeyDAO();
	        commitKeyDAO.add(listCommitDeveloper, this.developer.getRepository(), this.developer.getOwner());
	    }

	    @Override
	    public void onFailure(Call<List<Commit>> call, Throwable t) {
	        t.printStackTrace();
	    }
	 
}
