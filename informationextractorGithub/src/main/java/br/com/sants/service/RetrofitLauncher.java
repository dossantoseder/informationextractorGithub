package br.com.sants.service;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitLauncher {
	private final Retrofit retrofit;
	private final String API_URL = "https://api.github.com/";
	/*private final String CLIENT_ID = "Iv1.b52521425153f7b1";
	private final String CLIENT_SECRET = "1cee69035ef6e29829d13d6a0d729313df2c7042";
	private final String GET = "https://github.com/login/oauth/authorize";*/
	

	public RetrofitLauncher() {
		this.retrofit = new Retrofit.Builder()
				.baseUrl(API_URL)
				.addConverterFactory(JacksonConverterFactory.create())
				.build();
	}
	
	public ServiceDeveloper getUserCollaborator() {
		return retrofit.create(ServiceDeveloper.class);
	}
	
	public ServiceRepository getRepositories() {
		return retrofit.create(ServiceRepository.class);
	}
	
	public ServiceRepository getContributorsRepository() {
		return retrofit.create(ServiceRepository.class);
	}

	public ServiceCommit getServiceCommit() {
		return retrofit.create(ServiceCommit.class);
	}
}
