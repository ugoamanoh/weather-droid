package com.weather.droid;

public class TemperatureConverter {
	
	public String farenheitToCelcius(String temp){
		
		int tempp = Integer.parseInt(temp);
		tempp=((int) ((5.0f / 9.0f) * (tempp - 32)));
		temp = String.valueOf(tempp);
		
		return temp;
		
	}

}
