package br.com.sants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "node_id", "avatar_url", "gravatar_id", "url", "html_url", "followers_url", "following_url",
		"gists_url", "starred_url", "subscriptions_url", "organizations_url", "events_url", "received_events_url",
		"type", "site_admin" })
public class Contributor {
	private long id;
	private String login;
	private int contributions;
	private String repos_url;

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

	public int getContributions() {
		return contributions;
	}

	public void setContributions(int contributions) {
		this.contributions = contributions;
	}

	public String getRepos_url() {
		return repos_url;
	}

	public void setRepos_url(String repos_url) {
		this.repos_url = repos_url;
	}

	@Override
	public String toString() {
		return "Identifier: " + this.id + ", Login: " + this.login + ", Contributions: " + this.contributions
				+ ", Repos URL: " + this.repos_url;
	}

}
