package br.com.sants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "node_id", "avatar_url", "gravatar_id", "url", "url", "html_url", "followers_url",
		"following_url", "gists_url", "starred_url", "subscriptions_url", "organizations_url", "events_url",
		"received_events_url", "type", "site_admin", "name", "company", "blog", "location", "email", "hireable", "bio",
		"twitter_username", "followers", "following", "public_gists" })
public class User {
	private long id;
	private String login;
	private int public_repos;
	// URL da lista de repositótios do colaborador
	private String repos_url;
	private String created_at;
	private String updated_at;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getPublic_repos() {
		return public_repos;
	}

	public void setPublic_repos(int public_repos) {
		this.public_repos = public_repos;
	}

	public String getRepos_url() {
		return repos_url;
	}

	public void setRepos_url(String repos_url) {
		this.repos_url = repos_url;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	@Override
	public String toString() {
		return "Identifier: " + this.id + ", Login: " + this.login + ", Number repository: " + this.public_repos
				+ ", Repositories URL: " + this.repos_url + ", Updated at: " + this.updated_at + ", Created at: "
				+ this.created_at;
	}
}
