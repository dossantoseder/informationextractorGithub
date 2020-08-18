package br.com.sants.service;

import br.com.sants.model.User;

import java.util.List;

import br.com.sants.model.Repository;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceDeveloper {

	@GET("users/{user}")
	Call<User> getUser(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec, @Path("user") String username);

	@GET("users/{user}/repos")//https://api.github.com/users/MisterBooo/repos
	Call<List<Repository>> listRepositoriesDeveloper(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec, @Path("user") String user, @Query("page") int page, @Query("per_page") int per_page);
	
	@GET("users/{user}/repos")//https://api.github.com/users/MisterBooo/repos
	Call<List<Repository>> listRepositoriesDeveloper(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec, @Path("user") String user, @Query("per_page") int per_page);

}
