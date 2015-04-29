package com.weather.droid;

import java.util.ArrayList;

public class SetWeather {
	
	private String temp = null;
	private String wind = null;
	private String humidity = null;
	private String city = null;
	private String date = null;
	private ArrayList<String>high=new ArrayList<String>();
	private ArrayList<String>low=new ArrayList<String>();
	private ArrayList<String>forecast=new ArrayList<String>();
	private ArrayList<String>day_of_week=new ArrayList<String>();
    	
	/*
	 * Creating getters and setters
	 */
	public String getTemp() 
	{
		return temp;
	}	
	public void setTemp(String temp) 
	{
		this.temp = temp;
	}	
	public String getWind() 
	{
		return wind;
	}	
	public void setWind(String wind) 
	{
		this.wind = wind;
	}	
	public String getHumidity() 
	{
		return humidity;
	}	
	public void setHumidity(String humidity) 
	{
		this.humidity = humidity;
	}	
	public String getCity() 
	{
		return city;
	}
	public void setCity(String city) 
	{
		this.city = city;
	}
	public String getDate() 
	{
		return date;
	}
	public void setDate(String date) 
	{
		this.date = date;
	}
	public ArrayList<String> getHigh() 
	{
		return high;
	}
	public void setHigh(String high) 
	{
		this.high.add(high);
	}
	public ArrayList<String> getLow() 
	{
		return low;
	}
	public void setLow(String low) 
	{
		this.low.add(low);
	}
	public ArrayList<String> getForecast() 
	{
		return forecast;
	}
	public void setForecast(String forecast) 
	{
		this.forecast.add(forecast);
	}
	public ArrayList<String> getDay_Of_Week() 
	{
		return day_of_week;
	}
	public void setDay_Of_Week(String day_of_week) 
	{
		this.day_of_week.add(day_of_week);
	}
	

}
