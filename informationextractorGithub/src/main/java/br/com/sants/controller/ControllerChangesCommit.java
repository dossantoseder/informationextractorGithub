package br.com.sants.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import br.com.sants.data.ChangesCommitDAO;
import br.com.sants.model.Commit;
import br.com.sants.model.CommitChanges;
import br.com.sants.service.RetrofitLauncher;
import br.com.sants.service.ServiceCommit;

public class ControllerChangesCommit implements Callback<CommitChanges> {
	private String API_VERSION_SPEC;
	private String ACCESSTOKEN;
	Map<String, String> mapaNomes = new HashMap<String, String>();
	private Commit commit;
	private CommitChanges commitChanges = new CommitChanges();
	private int appID;
	private String clientID;
	private String clientSecret; 

	public ControllerChangesCommit(Commit c) {
		this.commit = c;
		this.API_VERSION_SPEC = "application/vnd.github.v3+json";
		this.ACCESSTOKEN = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
		//this.ACCESSTOKEN = "be8f8bb1d9a3455f9cde63a891ef55750aafb3dc";
		appID = 69080;
		clientID =  "Iv1.b52521425153f7b1";//client_id client_secret
		clientSecret =  "1cee69035ef6e29829d13d6a0d729313df2c7042";//client_secret
	}

	public void start() {
		int perPage = 100;
		ServiceCommit serviceCommit = new RetrofitLauncher().getServiceCommit();
		Call<CommitChanges> call = serviceCommit.referenceCommitAuthor(this.commit.getOwner(),
				this.commit.getRepository(), this.commit.getSha(), perPage);
		call.enqueue(this);
	}

	@Override
	public void onResponse(Call<CommitChanges> call, Response<CommitChanges> response) {
		System.out.println("URL: " + response.raw().request().url() + "\n");
		String page = "";
		if (response.isSuccessful()) {
			commitChanges = response.body();
		} else {
			try {
				System.out.println("ERROR: " + response.errorBody().string());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Página"+ page);
		if (commitChanges != null) {
			ChangesCommitDAO changesCommitDAO = new ChangesCommitDAO();
			changesCommitDAO.add(commitChanges, this.commit.getRepository());
		}

	}

	@Override
	public void onFailure(Call<CommitChanges> call, Throwable t) {
		t.printStackTrace();
	}
}
