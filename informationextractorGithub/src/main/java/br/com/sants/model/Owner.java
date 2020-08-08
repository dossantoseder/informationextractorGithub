package br.com.sants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "node_id", "node_id", "avatar_url", "gravatar_id", "url", "html_url", "followers_url",
		"following_url", "gists_url", "starred_url", "subscriptions_url", "organizations_url", "events_url",
		"received_events_url", "type", "site_admin", "repos_url" })
public class Owner {
	private long id;
	private String login;
	// private String repos_url;

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
	/*
	 * public String getRepos_url() { return repos_url; } public void
	 * setRepos_url(String repos_url) { this.repos_url = repos_url; }
	 */

}
