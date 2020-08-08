package br.com.sants.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "total_count", "incomplete_results" })
public class Repositories {
	private List<Repository> repository;

	public Repositories() {
		repository = new ArrayList<Repository>();
	}
	
	public List<Repository> getItems() {
		return this.repository;
	}

	public void setItems(ArrayList<Repository> repository) {
		this.repository = repository;
	}

}
