package com.aharoldk.iak_final.model;

import com.google.gson.annotations.SerializedName;

public class ProductionCountriesItem{

	@SerializedName("iso_3166_1")
	private String iso31661;

	@SerializedName("name")
	private String name;

	@Override
 	public String toString(){
		return 
			"ProductionCountriesItem{" + 
			"iso_3166_1 = '" + iso31661 + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}

	public ProductionCountriesItem(String iso31661, String name) {
		this.iso31661 = iso31661;
		this.name = name;
	}

	public String getIso31661() {
		return iso31661;
	}

	public void setIso31661(String iso31661) {
		this.iso31661 = iso31661;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}