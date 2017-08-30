package com.aharoldk.iak_final.pojo.Review;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review{
	public Review() {
	}

	@SerializedName("id")
	private int id;

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
			"Review{" + 
			"id = '" + id + '\'' + 
			",page = '" + page + '\'' + 
			",total_pages = '" + totalPages + '\'' + 
			",results = '" + results + '\'' + 
			",total_results = '" + totalResults + '\'' + 
			"}";
		}

	public Review(int id, int page, int totalPages, List<ResultsItem> results, int totalResults) {
		this.id = id;
		this.page = page;
		this.totalPages = totalPages;
		this.results = results;
		this.totalResults = totalResults;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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