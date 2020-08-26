package br.com.sants.service;

import java.util.List;

import br.com.sants.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServiceDeveloper {

	@GET("users/{user}")
	Call<User> loadChanges(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec, @Path("user") String username);
}
