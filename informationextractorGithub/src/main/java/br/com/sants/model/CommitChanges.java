package br.com.sants.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "node_id", "message", "tree", "url", "comment_count", "verification",
		"html_url", "comments_url", "committer", "parents", "stats", "name" })
public class CommitChanges {
	private String sha;
	private Author Author;
	private List<Files> files;
	private Commit commit;

	public CommitChanges() {
		files = new ArrayList<Files>();
	}

	public List<Files> getFiles() {
		return files;
	}

	public void setFiles(List<Files> files) {
		this.files = files;
	}

	public String getSha() {
		return sha;
	}

	public void setSha(String sha) {
		this.sha = sha;
	}

	public Author getAuthor() {
		return Author;
	}

	public void setAuthor(Author author) {
		Author = author;
	}

	public Commit getCommit() {
		return commit;
	}

	public void setCommit(Commit commit) {
		this.commit = commit;
	}

}
