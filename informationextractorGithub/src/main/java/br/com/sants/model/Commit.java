package br.com.sants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "node_id", "commit", "committer", "message", "tree", "comment_count", "verification",
		"html_url", "comments_url", "committer", "parents" })
public class Commit {
	private String sha;
	private String url;
	private Author Author;
	private String repository;
	private String owner;

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Author getAuthor() {
		return Author;
	}

	public void setAuthor(Author author) {
		Author = author;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	

}
