package br.com.sants.controller;

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
	private String API_VERSION_SPEC = "application/vnd.github.v3+json";
	private String ACCESSTOKEN = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	// private static final String fileName =
	// "/home/pereira/Documentos/dados_git/commit/nome_arquivo.xls";
	Map<String, String> mapaNomes = new HashMap<String, String>();
	private Commit commit;
	private CommitChanges commitChanges = new CommitChanges();

	public ControllerChangesCommit(Commit c) {
		this.commit = c;
		this.API_VERSION_SPEC = "application/vnd.github.v3+json";
		this.ACCESSTOKEN = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	}

	public void start() {
		int perPage = 100;
		ServiceCommit serviceCommit = new RetrofitLauncher().getServiceCommit();
		Call<CommitChanges> call = serviceCommit.referenceCommit(this.ACCESSTOKEN, this.API_VERSION_SPEC, this.commit.getOwner(),
				this.commit.getRepository(), this.commit.getSha(), perPage);
		call.enqueue(this);
	}

	@Override
	public void onResponse(Call<CommitChanges> call, Response<CommitChanges> response) {
		System.out.println("URL: " + response.raw().request().url() + "\n");

		if (response.isSuccessful()) {
			commitChanges = response.body();

		} else {
			System.out.println("ERROR: " + response.errorBody());
		}
		System.out.println(commitChanges.getSha());
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
