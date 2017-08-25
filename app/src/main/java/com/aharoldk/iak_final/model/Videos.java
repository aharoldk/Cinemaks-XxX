package com.aharoldk.iak_final.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Videos{

	@SerializedName("results")
	private List<ResultsItem> results;

	@Override
 	public String toString(){
		return 
			"Videos{" + 
			"results = '" + results + '\'' + 
			"}";
		}

	public Videos(List<ResultsItem> results) {
		this.results = results;
	}

	public List<ResultsItem> getResults() {
		return results;
	}

	public void setResults(List<ResultsItem> results) {
		this.results = results;
	}
}