package com.aharoldk.iak_final.pojo.Movie;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Home{

	public Home() {
	}

	@SerializedName("page")
	private int page;

	@SerializedName("total_pages")
	private int totalPages;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("total_results")
	private int totalResults;

	@Override
 	public String toString(){
		return 
			"Home{" + 
			"page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			",total_results = '" + totalResults + '\'' + 
			"}";
		}

	public Home(int page, int totalPages, List<ResultsItem> results, int totalResults) {
		this.page = page;
		this.totalPages = totalPages;
		this.results = results;
		this.totalResults = totalResults;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<ResultsItem> getResults() {
		return results;
	}

	public void setResults(List<ResultsItem> results) {
		this.results = results;
	}

	public int getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}


}