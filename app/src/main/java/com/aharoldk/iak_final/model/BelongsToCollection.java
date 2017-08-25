package com.aharoldk.iak_final.model;

import com.google.gson.annotations.SerializedName;

public class BelongsToCollection{

	@SerializedName("backdrop_path")
	private String backdropPath;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("poster_path")
	private String posterPath;

	@Override
 	public String toString(){
		return 
			"BelongsToCollection{" + 
			"backdrop_path = '" + backdropPath + '\'' + 
			",name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",poster_path = '" + posterPath + '\'' + 
			"}";
		}

	public BelongsToCollection(String backdropPath, String name, int id, String posterPath) {
		this.backdropPath = backdropPath;
		this.name = name;
		this.id = id;
		this.posterPath = posterPath;
	}

	public String getBackdropPath() {
		return backdropPath;
	}

	public void setBackdropPath(String backdropPath) {
		this.backdropPath = backdropPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPosterPath() {
		return posterPath;
	}

	public void setPosterPath(String posterPath) {
		this.posterPath = posterPath;
	}
}