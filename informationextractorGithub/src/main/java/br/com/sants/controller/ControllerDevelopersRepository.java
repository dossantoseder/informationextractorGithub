package br.com.sants.controller;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.sants.data.DevelopersDAO;
import br.com.sants.data.DevelopersRepositoryDAO;
import br.com.sants.model.Contributor;
import br.com.sants.model.Repository;
import br.com.sants.model.User;
import br.com.sants.service.IServiceDeveloper;
import br.com.sants.service.IServiceListDevelopersRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ControllerDevelopersRepository implements Callback<List<Contributor>> {
	private Repository repository;
	private String API_VERSION_SPEC;
	private String ACCESSTOKEN;

	static final String BASE_URL = "https://api.github.com/";

	public ControllerDevelopersRepository(Repository repo) {
		this.repository = repo;
		this.API_VERSION_SPEC = "application/vnd.github.v3+json";
		this.ACCESSTOKEN = "9ab5ff381ba2c6510a5662b8de793f0b123cb939";
	}

	public void start() {
		int perPage = 100;
		Gson gson = new GsonBuilder().setLenient().create();

		Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
				.addConverterFactory(GsonConverterFactory.create(gson)).build();

		IServiceListDevelopersRepository gerritAPI = retrofit.create(IServiceListDevelopersRepository.class);

		Call<List<Contributor>> call = gerritAPI.listContributorsRepository(this.ACCESSTOKEN, this.API_VERSION_SPEC,
				this.repository.getOwner().getLogin(), this.repository.getName(), perPage);
		call.enqueue(this);

	}

	@Override
	public void onResponse(Call<List<Contributor>> call, Response<List<Contributor>> response) {
		DevelopersRepositoryDAO developersRepositoryDAO = new DevelopersRepositoryDAO();
		if (response.isSuccessful()) {
			List<Contributor> developers = response.body();
			developersRepositoryDAO.add(developers, this.repository.getName(), this.repository.getOwner().getLogin());
		} else {
			System.out.println(response.errorBody());
		}
	}

	@Override
	public void onFailure(Call<List<Contributor>> call, Throwable t) {
		t.printStackTrace();
	}

}
