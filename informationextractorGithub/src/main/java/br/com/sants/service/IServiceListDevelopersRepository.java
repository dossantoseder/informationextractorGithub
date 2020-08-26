package br.com.sants.service;

import java.util.List;

import br.com.sants.model.Contributor;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServiceListDevelopersRepository {
	@GET("repos/{login}/{name}/contributors")
	Call<List<Contributor>> listContributorsRepository(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec, @Path("login") String login, @Path("name") String name, @Query("per_page") int per_page);

}
