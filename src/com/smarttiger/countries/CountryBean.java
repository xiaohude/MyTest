package com.smarttiger.countries;

import java.io.Serializable;

public class CountryBean implements Serializable{
	private String code;
	private String name;
	private double latitude;
	private double longitude;
	
	public CountryBean(String code, String name, double latitude, double longitude) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public String getCode() {
		return code;
	}

	public CountryBean setCode(String code) {
		this.code = code;
		return this;
	}

	public String getName() {
		return name;
	}

	public CountryBean setName(String name) {
		this.name = name;
		return this;
	}

	public double getLatitude() {
		return latitude;
	}

	public CountryBean setLatitude(double latitude) {
		this.latitude = latitude;
		return this;
	}

	public double getLongitude() {
		return longitude;
	}

	public CountryBean setLongitude(double longitude) {
		this.longitude = longitude;
		return this;
	}
	
	public String toString() {
		return "code=" + code + "\n" +
				"name=" + name + "\n" +
				"latitude=" + latitude + "\n" +
				"longitude=" + longitude ;
	}
}
