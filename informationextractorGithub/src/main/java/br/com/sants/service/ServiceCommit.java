package br.com.sants.service;

import java.util.List;

import br.com.sants.model.Commit;
import br.com.sants.model.CommitChanges;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceCommit {

	@GET("repos/{owner}/{repo}/commits")
	Call<List<Commit>> listCommit(@Header("Authorization") String accessToken, @Header("Accept") String apiVersionSpec,
			@Path("owner") String owner, @Path("repo") String repo, @Query("per_page") int per_page);

	@GET("repos/{owner}/{repo}/commits")
	Call<List<Commit>> listCommitAuthor(@Header("Authorization") String accessToken,
			@Header("Accept") String apiVersionSpec, @Path("owner") String owner, @Path("repo") String repo,
			@Query("author") String author, @Query("per_page") int per_page);

	@GET("repos/{owner}/{repo}/commits/{ref}")
	Call<CommitChanges> referenceCommit(@Header("Authorization") String accessToken,
			@Header("Accept") String apiVersionSpec, @Path("owner") String owner, @Path("repo") String repo,
			@Path("ref") String ref, @Query("per_page") int per_page); 

}
