package br.com.sants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "node_id", "avatar_url", "gravatar_id", "url", "html_url", "followers_url", "following_url",
		"gists_url", "starred_url", "subscriptions_url", "organizations_url", "repos_url", "events_url",
		"received_events_url", "type", "site_admin", "name", "email","date"})
public class Author {
	
	private String login;
	private int id;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getId() {
		return id;
	}

	public void setId(int identifier) {
		this.id = identifier;
	}

}
