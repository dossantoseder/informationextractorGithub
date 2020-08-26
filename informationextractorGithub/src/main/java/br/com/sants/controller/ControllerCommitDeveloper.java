package br.com.sants.controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.sants.data.CommitKeyDAO;
import br.com.sants.model.Commit;
import br.com.sants.model.Contributor;
import br.com.sants.service.IServiceCommit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerCommitDeveloper implements Callback<List<Commit>> {
	private Contributor developer;
	private String API_VERSION_SPEC;
	private String ACCESSTOKEN;

	static final String BASE_URL = "https://api.github.com/";

	public ControllerCommitDeveloper(Contributor d) {
		this.developer = d;
		this.API_VERSION_SPEC = "application/vnd.github.v3+json";
		this.ACCESSTOKEN = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	}

	public void start() {
		int perPage = 100;
		 int page = 0;
		Gson gson = new GsonBuilder().setLenient().create();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create(gson)).build();

		IServiceCommit gerritAPI = retrofit.create(IServiceCommit.class);

		Call<List<Commit>> call = gerritAPI.listCommitAuthor(ACCESSTOKEN, API_VERSION_SPEC, this.developer.getOwner(),
				this.developer.getRepository(), this.developer.getLogin(), page, perPage);
		call.enqueue(this);

	}

	@Override
	public void onResponse(Call<List<Commit>> call, Response<List<Commit>> response) {
		System.out.println("URL: " + response.raw().request().url() + "\n");
		CommitKeyDAO commitKeyDAO = new CommitKeyDAO();
		if (response.isSuccessful()) {
			List<Commit> commits = response.body();
			commitKeyDAO.add(commits, this.developer.getRepository(), this.developer.getOwner());
		} else {
			System.out.println(response.errorBody());
		}
	}

	@Override
	public void onFailure(Call<List<Commit>> call, Throwable t) {
		t.printStackTrace();
	}

}
