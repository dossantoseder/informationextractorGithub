package br.com.sants.service;

import java.util.List;
import java.util.Map;

import br.com.sants.model.Contributor;
import br.com.sants.model.Repositories;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ServiceRepository {
	@GET("/search/repositories?q=topic:covid-19+language:java")//https://api.github.com/search/repositories?q=language:java&sort=stars&order=desc
	Call<Repositories> listRepositories(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec, @QueryMap Map<String, String> paramsMap, @Query("per_page") int per_page);
	
	@GET("repos/{login}/{name}/contributors")//https://api.github.com/repos/Tencent/tinker/contributors
	Call<List<Contributor>> listContributorsRepository(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec, @Path("login") String login, @Path("name") String name, @Query("per_page") int per_page);
	
	@GET("repos/{login}/{name}/contributors")//https://api.github.com/repos/Tencent/tinker/contributors
	Call<List<Contributor>> listContributorsRepository(@Path("login") String login, @Path("name") String name, @Query("page") int page, @Query("per_page") int per_page);
}


