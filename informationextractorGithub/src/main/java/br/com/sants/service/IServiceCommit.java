package br.com.sants.service;

import java.util.List;

import br.com.sants.model.Commit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IServiceCommit {
	
	@GET("repos/{owner}/{repo}/commits")
	Call<List<Commit>> listCommitAuthor(@Header("Authorization") String accessToken,
			@Header("Accept") String apiVersionSpec, @Path("owner") String owner, @Path("repo") String repo,
			@Query("author") String author, @Query("page") int page, @Query("per_page") int per_page);

}
