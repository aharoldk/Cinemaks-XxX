package com.aharoldk.iak_final.pojo;

import com.google.gson.annotations.SerializedName;

public class Dates{

	@SerializedName("maximum")
	private String maximum;

	@SerializedName("minimum")
	private String minimum;

	@Override
 	public String toString(){
		return 
			"Dates{" + 
			"maximum = '" + maximum + '\'' + 
			",minimum = '" + minimum + '\'' + 
			"}";
		}

	public Dates(String maximum, String minimum) {
		this.maximum = maximum;
		this.minimum = minimum;
	}

	public String getMaximum() {
		return maximum;
	}

	public void setMaximum(String maximum) {
		this.maximum = maximum;
	}

	public String getMinimum() {
		return minimum;
	}

	public void setMinimum(String minimum) {
		this.minimum = minimum;
	}
}