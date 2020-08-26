package br.com.sants.service;

import java.util.List;

import br.com.sants.model.Commit;
import br.com.sants.model.CommitChanges;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServiceCommit {

	@Headers({ "Authorization: token be8f8bb1d9a3455f9cde63a891ef55750aafb3dc",
			"Accept: application/vnd.github.v3.full+json", "User-Agent: informationextractor" })
	@GET("repos/{owner}/{repo}/commits")
	Call<List<Commit>> listCommit(@Path("owner") String owner, @Path("repo") String repo,
			@Query("per_page") int per_page);

	@Headers({ "Authorization: token be8f8bb1d9a3455f9cde63a891ef55750aafb3dc",
			"Accept: application/vnd.github.v3.full+json", "User-Agent: informationextractor" })
	@GET("repos/{owner}/{repo}/commits")
	Call<List<Commit>> listCommitAuthor(@Path("owner") String owner, @Path("repo") String repo,
			@Query("author") String author, @Query("page") int page, @Query("per_page") int per_page);

	@Headers({ "Authorization: token be8f8bb1d9a3455f9cde63a891ef55750aafb3dc",
			"Accept: application/vnd.github.v3.full+json", "User-Agent: informationextractor" })
	@GET("repos/{owner}/{repo}/commits/{ref}")
	Call<CommitChanges> referenceCommitAuthor(@Path("owner") String owner, @Path("repo") String repo,
			@Path("ref") String ref, @Query("per_page") int per_page);

}
