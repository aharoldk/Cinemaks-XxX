package com.aharoldk.iak_final.pojo.Trailer;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Trailer{

	public Trailer() {
	}

	@SerializedName("id")
	private int id;

	@SerializedName("results")
	private List<ResultsItem> results;

	@Override
 	public String toString(){
		return 
			"Trailer{" + 
			"id = '" + id + '\'' + 
			",results = '" + results + '\'' + 
			"}";
		}

	public Trailer(int id, List<ResultsItem> results) {
		this.id = id;
		this.results = results;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ResultsItem> getResults() {
		return results;
	}

	public void setResults(List<ResultsItem> results) {
		this.results = results;
	}
}