package com.aharoldk.iak_final.model;

import com.google.gson.annotations.SerializedName;

public class ProductionCompaniesItem{

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@Override
 	public String toString(){
		return 
			"ProductionCompaniesItem{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}

	public ProductionCompaniesItem(String name, int id) {
		this.name = name;
		this.id = id;
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
}