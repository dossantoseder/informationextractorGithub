package br.com.sants.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "node_id", "private", "license", "forks", "description", "fork", "url", "forks_url", "keys_url",
		"collaborators_url", "teams_url", "hooks_url", "issue_events_url", "events_url", "assignees_url",
		"branches_url", "tags_url", "blobs_url", "git_tags_url", "git_refs_url", "trees_url", "statuses_url",
		"languages_url", "stargazers_url", "subscribers_url", "subscription_url", "git_commits_url", "comments_url",
		"issue_comment_url", "contents_url", "compare_url", "merges_url", "archive_url", "issues_url", "pulls_url",
		"milestones_url", "notifications_url", "labels_url", "releases_url", "deployments_url", "updated_at",
		"pushed_at", "git_url", "ssh_url", "clone_url", "svn_url", "homepage", "size", "watchers_count", "has_issues",
		"has_projects", "has_downloads", "has_wiki", "has_pages", "forks_count", "mirror_url", "archived", "disabled",
		"open_issues_count", "description", "full_name", "open_issues", "watchers",
		"default_branch", "score" })
public class Repository {
	private long id;
	private String name;
	private String html_url;
	private String language;
	private Owner owner;
	private String contributors_url;
	private String commits_url;
	private String downloads_url;
	private String created_at;
	private String stargazers_count;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHtml_url() {
		return html_url;
	}

	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public String getContributors_url() {
		return contributors_url;
	}

	public void setContributors_url(String contributors_url) {
		this.contributors_url = contributors_url;
	}

	public String getCommits_url() {
		return commits_url;
	}

	public void setCommits_url(String commits_url) {
		this.commits_url = commits_url;
	}

	public String getDownloads_url() {
		return downloads_url;
	}

	public void setDownloads_url(String downloads_url) {
		this.downloads_url = downloads_url;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	
	

	public String getStargazers_count() {
		return stargazers_count;
	}

	public void setStargazers_count(String stargazers_count) {
		this.stargazers_count = stargazers_count;
	}

	@Override
	public String toString() {
		return "Identifier: " + this.id + ", Name: " + this.name + ", HTML URL: " + this.html_url + ", Language: "
				+ this.language + ", Owner: " + this.owner.getLogin() + ", Contributors URL: " + this.contributors_url
				+ ", Commits URL: " + this.commits_url + ", Downloads URL: " + this.downloads_url + ", Created at: "
				+ this.created_at + ", Stargazers count: " + this.stargazers_count;
	}

}
