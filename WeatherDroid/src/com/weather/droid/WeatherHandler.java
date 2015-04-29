package com.weather.droid;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class WeatherHandler extends DefaultHandler {

	
	//Variables
	private boolean in_forecast_information = false;
	private boolean in_current_conditions = false;
	private boolean in_forecast_conditions = false;

	private SetWeather mySetWeather = new SetWeather();

	
	//Getter & Setter
	public SetWeather getSetWeather() 
	{
		return this.mySetWeather;
	}

	
	//Methods
	@Override
	public void startDocument() throws SAXException 
	{
	}
	
	@Override
	public void endDocument() throws SAXException 
	{
		// Nothing to do
	}

	/**
	 * Gets be called on opening tags like: <tag> Can provide attribute(s), when
	 * xml was like: <tag attribute="attributeValue">
	 */
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) throws SAXException
	{
		if (localName.equals("forecast_information"))
		{
			this.in_forecast_information = true;
		}
		else if (localName.equals("current_conditions"))
		{
			this.in_current_conditions = true;

		}
		else if (localName.equals("forecast_conditions"))
		{
			this.in_forecast_conditions = true;

		}
		
		/*
		 * Getting values from XML document and passing them to set methods in the SetWeather 
		 * class
		 */
		else if (localName.equals("city")) 
		{
			if (this.in_forecast_information)
			{
				String attrValue = atts.getValue("data");
				mySetWeather.setCity(attrValue);
			}			
		}
		else if (localName.equals("forecast_date")) 
		{
			if (this.in_forecast_information) {
				String attrValue1 = atts.getValue("data");
				mySetWeather.setDate(attrValue1);
			}
		}
		else if (localName.equals("wind_condition")) 
		{
			if (this.in_current_conditions) 
			{
				String attrValue2 = atts.getValue("data");
				mySetWeather.setWind(attrValue2);
			}
		}
		else if (localName.equals("temp_f")) 
		{
			if (this.in_current_conditions) 
			{
				String attrValue2 = atts.getValue("data");
				mySetWeather.setTemp(attrValue2);
			}
		}
		else if (localName.equals("humidity")) 
		{
			if (this.in_current_conditions) 
			{
				String attrValue2 = atts.getValue("data");
				mySetWeather.setHumidity(attrValue2);
			}
		}
		else if (localName.equals("condition")) 
		{	
				String attrValue2 = atts.getValue("data");
				mySetWeather.setForecast(attrValue2);
		}		
		else if (localName.equals("high")) 
		{
			if (this.in_forecast_conditions) {
				String attrValue2 = atts.getValue("data");
				mySetWeather.setHigh(attrValue2);
			}
		}
		else if (localName.equals("low")) 
		{
			if (this.in_forecast_conditions) {
				String attrValue2 = atts.getValue("data");
				mySetWeather.setLow(attrValue2);
			}
		}
		else if (localName.equals("day_of_week")) 
		{
			if (this.in_forecast_conditions) {
				String attrValue2 = atts.getValue("data");
				mySetWeather.setDay_Of_Week(attrValue2);
			}
		}
		
		
	}

	/**
	 * Gets be called on closing tags like: </tag>
	 */
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		if (localName.equals("forecast_information")) {
			this.in_forecast_information = false;
		} else if (localName.equals("current_conditions")) {
			this.in_current_conditions = false;
		} else if (localName.equals("forecast_conditions")) {
			this.in_forecast_conditions = false;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {

	}

}
