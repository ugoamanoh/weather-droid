package com.weather.droid;

import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainView extends Activity implements
		OnSharedPreferenceChangeListener {

	//creating instances of view objects
	private TextView temp;
	private TextView humidity;
	private TextView wind;
	private TextView city;
	private TextView date;
	private TextView condition;
	
	private TextView low;
	private TextView high;
	private TextView next_day_condition;
	
	private TextView day3;
	private TextView day3_low;
	private TextView day3_high;
	private TextView day3_condition;
	private ImageView day3_condition_img;
	
	private TextView day4;
	private TextView day4_low;
	private TextView day4_high;
	private TextView day4_condition;
	private ImageView day4_condition_img;
	
	private TextView day5;
	private TextView day5_low;
	private TextView day5_high;
	private TextView day5_condition;
	private ImageView day5_condition_img;
	
	private ImageView condition_img;
	private ImageView later_condition_img;
	
	//creating instance of WeatherAnimation class
	private WeatherAnimation myWeatherAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mainview);
		
		
		temp = (TextView) findViewById(R.id.temp);
		humidity = (TextView) findViewById(R.id.humidity);
		wind = (TextView) findViewById(R.id.wind);
		city = (TextView) findViewById(R.id.city);
		date = (TextView) findViewById(R.id.date);
		condition = (TextView) findViewById(R.id.condition);
		condition_img = (ImageView) findViewById(R.id.imageView1);
		myWeatherAnimation = new WeatherAnimation();
		
		low = (TextView) findViewById(R.id.lowtemp);
		high = (TextView) findViewById(R.id.hightemp);
		next_day_condition = (TextView) findViewById(R.id.next_day_condition);
		later_condition_img = (ImageView) findViewById(R.id.condition_img);
		
		day3 = (TextView) findViewById(R.id.day3);
		day3_low = (TextView) findViewById(R.id.day3_lowtemp);
		day3_high = (TextView) findViewById(R.id.day3_hightemp);
		day3_condition = (TextView) findViewById(R.id.day3_condition);
		day3_condition_img = (ImageView) findViewById(R.id.day3_condition_img);
		
		day4 = (TextView) findViewById(R.id.day4);
		day4_low = (TextView) findViewById(R.id.day4_lowtemp);
		day4_high = (TextView) findViewById(R.id.day4_hightemp);
		day4_condition = (TextView) findViewById(R.id.day4_condition);
		day4_condition_img = (ImageView) findViewById(R.id.day4_condition_img);
		
		day5 = (TextView) findViewById(R.id.day5);
		day5_low = (TextView) findViewById(R.id.day5_lowtemp);
		day5_high = (TextView) findViewById(R.id.day5_hightemp);
		day5_condition = (TextView) findViewById(R.id.day5_condition);
		day5_condition_img = (ImageView) findViewById(R.id.day5_condition_img);
		
		/* Performing a check on location preference to make sure user has input a city name. 
		 * User is directed to preferences screen if it is found empty
		 */
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		if (prefs.getString("location", null) == null) {
			startActivity(new Intent(this, Preferences.class));
			return;
		}
		
		new MyAsyncTask().execute();
	}
	
	/*
	 * Using MyAsyncTask to handle the long running code in the background and separate from the UI thread
	 */
	private class MyAsyncTask extends AsyncTask<Integer, Integer, SetWeather> {

		protected SetWeather doInBackground(Integer... counter) {
			SetWeather myData = new SetWeather();

			try {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(MainView.this);
				prefs.registerOnSharedPreferenceChangeListener(MainView.this);
				
				/*
				 * Passing location from preferences into URL
				 */
				String input_city = prefs.getString("location", " ");
				String location = "http://www.google.com/ig/api?weather="
						+ input_city;
				
				/** Send URL to parse XML Tags */
				URL url = new URL(location.replace(" ", "%20"));
				
				/** Handling XML */
				SAXParserFactory spf = SAXParserFactory.newInstance();
				SAXParser sp = spf.newSAXParser();
				XMLReader xr = sp.getXMLReader();
				
				/**
				 * Creating handler to handle XML tags
				 */
				WeatherHandler myWeatherHandler = new WeatherHandler();
				xr.setContentHandler(myWeatherHandler);
				
				/* Parse XML */
				xr.parse(new InputSource(url.openStream()));
				myData = myWeatherHandler.getSetWeather();

				return myData;

			} catch (Exception e) {
				// Log.e("!!!!!!!!!! MyAsyncTask doInBackground error",
				// e.getMessage(), e);
				e.printStackTrace();
				return myData;
			}
		}
		
		/*
		 * 
		 * Updating UI on post-execute
		 */
		protected void onPostExecute(SetWeather myData) {

			try {
				SharedPreferences prefs = PreferenceManager
						.getDefaultSharedPreferences(MainView.this);

				TemperatureConverter convert = new TemperatureConverter();
				
				//convert temperature values to celcius if selected in preferences
				if (prefs.getString("unit", "F").equals("C")) {
					temp.setText("Temp: " +convert.farenheitToCelcius(myData.getTemp()) + " \u00B0C");
					low.setText(convert.farenheitToCelcius(myData.getLow().get(
							0)));
					high.setText(convert.farenheitToCelcius(myData.getHigh()
							.get(0)));
					day3_low.setText(convert.farenheitToCelcius(myData.getLow().get(
							1)));
					day3_high.setText(convert.farenheitToCelcius(myData.getHigh()
							.get(1)));
					day4_low.setText(convert.farenheitToCelcius(myData.getLow().get(
							2)));
					day4_high.setText(convert.farenheitToCelcius(myData.getHigh()
							.get(2)));
					day5_low.setText(convert.farenheitToCelcius(myData.getLow().get(
							3)));
					day5_high.setText(convert.farenheitToCelcius(myData.getHigh()
							.get(3)));
			
				} else if (prefs.getString("unit", "F").equals("F")) {
					temp.setText("Temp: " + myData.getTemp() +" \u00B0F");
					low.setText(myData.getLow().get(0));
					high.setText(myData.getHigh().get(0));
					day3_low.setText(myData.getLow().get(1));
					day3_high.setText(myData.getHigh().get(1));
					day4_low.setText(myData.getLow().get(2));
					day4_high.setText(myData.getHigh().get(2));
					day5_low.setText(myData.getLow().get(3));
					day5_high.setText(myData.getHigh().get(3));
				}

				Log.d("+++++++++++++ MyAsyncTask onPostExecute",
						myData.toString());
				
				
				wind.setText(myData.getWind());
				humidity.setText(myData.getHumidity());
				date.setText("Date: " +myData.getDate());
				city.setText("City: " +myData.getCity());
				condition.setText(myData.getForecast().get(0));
				next_day_condition.setText(myData.getForecast().get(1));
				day3_condition.setText(myData.getForecast().get(2));
				day4_condition.setText(myData.getForecast().get(3));
				day5_condition.setText(myData.getForecast().get(4));
				day3.setText(myData.getDay_Of_Week().get(1).toUpperCase());
				day4.setText(myData.getDay_Of_Week().get(2).toUpperCase());
				day5.setText(myData.getDay_Of_Week().get(3).toUpperCase());		

				
		        //checking returned forecast and displaying animation
				if (myData.getForecast().get(0).equals("Rain Showers")
						|| myData.getForecast().get(0).equals("Chance of Rain")
						|| myData.getForecast().get(0).equals("Rain")
						|| myData.getForecast().get(0).equals("Showers")
						|| myData.getForecast().get(0).equals("Light Rain")
						|| myData.getForecast().get(0).equals("Chance of Showers")
						|| myData.getForecast().get(0).equals("Scattered Showers")) 
				{
					myWeatherAnimation.rainy_Animation(condition_img);
				}
				else if (myData.getForecast().get(0).equals("Partly Cloudy")
						|| myData.getForecast().get(0).equals("Mostly Cloudy")
						|| myData.getForecast().get(0).equals("Cloudy")
						|| myData.getForecast().get(0).equals("Overcast")) 
				{
					myWeatherAnimation.cloudy_Animation(condition_img);
				}
				else if (myData.getForecast().get(0).equals("Thunderstorm")
						|| myData.getForecast().get(0).equals("Chance of Storm")
						|| myData.getForecast().get(0).equals("Storm")) 
				{
					myWeatherAnimation.storm_Animation(condition_img);
				}
				
				else if (myData.getForecast().get(0).equals("Partly Sunny")
						|| myData.getForecast().get(0).equals("Mostly Sunny")
						|| myData.getForecast().get(0).equals("Sunny")) 
				{
					myWeatherAnimation.sunny_Animation(condition_img);
				}
				else if (myData.getForecast().get(0).equals("Chance of Snow")
						|| myData.getForecast().get(0).equals("Snow")
						|| myData.getForecast().get(0).equals("Snow Showers")
						|| myData.getForecast().get(0).equals("Icy")
						|| myData.getForecast().get(0).equals("Haze")
						|| myData.getForecast().get(0).equals("Flurries")) 
				{
					myWeatherAnimation.snow_Animation(condition_img);
				}
				else if (myData.getForecast().get(0).equals("Clear")) 
				{
					myWeatherAnimation.clear_Animation(condition_img);
				}
				else if (myData.getForecast().get(0).equals("Fog")) 
				{
					myWeatherAnimation.fog_Animation(condition_img);
				}
							
				//Setting image for next day conditions
				if (myData.getForecast().get(1).equals("Rain Showers")
						|| myData.getForecast().get(1).equals("Chance of Rain")
						|| myData.getForecast().get(1).equals("Rain")
						|| myData.getForecast().get(1).equals("Showers")
						|| myData.getForecast().get(1).equals("Light Rain")
						|| myData.getForecast().get(1).equals("Chance of Showers")
						|| myData.getForecast().get(1).equals("Scattered Showers")) 
				{
					later_condition_img.setImageResource(R.drawable.chance_of_rain);
				}
				else if (myData.getForecast().get(1).equals("Thunderstorm")
						|| myData.getForecast().get(1).equals("Chance of Storm")
						|| myData.getForecast().get(1).equals("Storm")) 
				{
					later_condition_img.setImageResource(R.drawable.storm);
				}
				else if (myData.getForecast().get(1).equals("Partly Cloudy")
						|| myData.getForecast().get(1).equals("Mostly Cloudy")
						|| myData.getForecast().get(1).equals("Cloudy")
						|| myData.getForecast().get(1).equals("Overcast")) 
				{
					later_condition_img.setImageResource(R.drawable.cloudy);
				}
				else if (myData.getForecast().get(1).equals("Partly Sunny")
						|| myData.getForecast().get(1).equals("Mostly Sunny")
						|| myData.getForecast().get(1).equals("Sunny")) 
				{
					later_condition_img.setImageResource(R.drawable.sunny);
				}
				else if (myData.getForecast().get(1).equals("Chance of Snow")
						|| myData.getForecast().get(1).equals("Snow")
						|| myData.getForecast().get(1).equals("Snow Showers")
						|| myData.getForecast().get(1).equals("Icy")
						|| myData.getForecast().get(1).equals("Haze")
						|| myData.getForecast().get(1).equals("Flurries")) 
				{
					later_condition_img.setImageResource(R.drawable.snow);
				}
				else if (myData.getForecast().get(1).equals("Clear")) 
				{
					later_condition_img.setImageResource(R.drawable.mostly_sunny);
				}
				else if (myData.getForecast().get(1).equals("Fog")) 
				{
					later_condition_img.setImageResource(R.drawable.fog);
				}
				
				//Setting image for day3 conditions
				if (myData.getForecast().get(2).equals("Rain Showers")
						|| myData.getForecast().get(2).equals("Chance of Rain")
						|| myData.getForecast().get(2).equals("Rain")
						|| myData.getForecast().get(2).equals("Showers")
						|| myData.getForecast().get(2).equals("Light Rain")
						|| myData.getForecast().get(2).equals("Chance of Showers")
						|| myData.getForecast().get(2).equals("Scattered Showers")) 
				{
					day3_condition_img.setImageResource(R.drawable.chance_of_rain);
				}
				else if (myData.getForecast().get(2).equals("Thunderstorm")
						|| myData.getForecast().get(2).equals("Chance of Storm")
						|| myData.getForecast().get(2).equals("Storm")) 
				{
					day3_condition_img.setImageResource(R.drawable.storm);
				}
				else if (myData.getForecast().get(2).equals("Partly Cloudy")
						|| myData.getForecast().get(2).equals("Mostly Cloudy")
						|| myData.getForecast().get(2).equals("Cloudy")
						|| myData.getForecast().get(2).equals("Overcast")) 
				{
					day3_condition_img.setImageResource(R.drawable.cloudy);
				}
				else if (myData.getForecast().get(2).equals("Partly Sunny")
						|| myData.getForecast().get(2).equals("Mostly Sunny")
						|| myData.getForecast().get(2).equals("Sunny")) 
				{
					day3_condition_img.setImageResource(R.drawable.sunny);
				}
				else if (myData.getForecast().get(2).equals("Chance of Snow")
						|| myData.getForecast().get(2).equals("Snow")
						|| myData.getForecast().get(2).equals("Snow Showers")
						|| myData.getForecast().get(2).equals("Icy")
						|| myData.getForecast().get(2).equals("Haze")
						|| myData.getForecast().get(2).equals("Flurries")) 
				{
					day3_condition_img.setImageResource(R.drawable.snow);
				}
				else if (myData.getForecast().get(2).equals("Clear")) 
				{
					day3_condition_img.setImageResource(R.drawable.mostly_sunny);
				}
				else if (myData.getForecast().get(2).equals("Fog")) 
				{
					day3_condition_img.setImageResource(R.drawable.fog);
				}
				
				//Setting image for day4 conditions
				if (myData.getForecast().get(3).equals("Rain Showers")
						|| myData.getForecast().get(3).equals("Chance of Rain")
						|| myData.getForecast().get(3).equals("Rain")
						|| myData.getForecast().get(3).equals("Showers")
						|| myData.getForecast().get(3).equals("Light Rain")
						|| myData.getForecast().get(3).equals("Chance of Showers")
						|| myData.getForecast().get(3).equals("Scattered Showers")) 
				{
					day4_condition_img.setImageResource(R.drawable.chance_of_rain);
				}
				else if (myData.getForecast().get(3).equals("Thunderstorm")
						|| myData.getForecast().get(3).equals("Chance of Storm")
						|| myData.getForecast().get(3).equals("Storm")) 
				{
					day4_condition_img.setImageResource(R.drawable.storm);
				}
				else if (myData.getForecast().get(3).equals("Partly Cloudy")
						|| myData.getForecast().get(3).equals("Mostly Cloudy")
						|| myData.getForecast().get(3).equals("Cloudy")
						|| myData.getForecast().get(3).equals("Overcast")) 
				{
					day4_condition_img.setImageResource(R.drawable.cloudy);
				}
				else if (myData.getForecast().get(3).equals("Partly Sunny")
						|| myData.getForecast().get(3).equals("Mostly Sunny")
						|| myData.getForecast().get(3).equals("Sunny")) 
				{
					day4_condition_img.setImageResource(R.drawable.sunny);
				}
				else if (myData.getForecast().get(3).equals("Chance of Snow")
						|| myData.getForecast().get(3).equals("Snow")
						|| myData.getForecast().get(3).equals("Snow Showers")
						|| myData.getForecast().get(3).equals("Icy")
						|| myData.getForecast().get(3).equals("Haze")
						|| myData.getForecast().get(3).equals("Flurries")) 
				{
					day4_condition_img.setImageResource(R.drawable.snow);
				}
				else if (myData.getForecast().get(3).equals("Clear")) 
				{
					day4_condition_img.setImageResource(R.drawable.mostly_sunny);
				}
				else if (myData.getForecast().get(3).equals("Fog")) 
				{
					day4_condition_img.setImageResource(R.drawable.fog);
				}
				
				//Setting image for day5 conditions
				if (myData.getForecast().get(4).equals("Rain Showers")
						|| myData.getForecast().get(4).equals("Chance of Rain")
						|| myData.getForecast().get(4).equals("Rain")
						|| myData.getForecast().get(4).equals("Showers")
						|| myData.getForecast().get(4).equals("Light Rain")
						|| myData.getForecast().get(4).equals("Chance of Showers")
						|| myData.getForecast().get(4).equals("Scattered Showers")) 
				{
					day5_condition_img.setImageResource(R.drawable.chance_of_rain);
				}
				else if (myData.getForecast().get(4).equals("Thunderstorm")
						|| myData.getForecast().get(4).equals("Chance of Storm")
						|| myData.getForecast().get(4).equals("Storm")) 
				{
					day5_condition_img.setImageResource(R.drawable.storm);
				}
				else if (myData.getForecast().get(4).equals("Partly Cloudy")
						|| myData.getForecast().get(4).equals("Mostly Cloudy")
						|| myData.getForecast().get(4).equals("Cloudy")
						|| myData.getForecast().get(4).equals("Overcast")) 
				{
					day5_condition_img.setImageResource(R.drawable.cloudy);
				}
				else if (myData.getForecast().get(4).equals("Partly Sunny")
						|| myData.getForecast().get(4).equals("Mostly Sunny")
						|| myData.getForecast().get(4).equals("Sunny")) 
				{
					day5_condition_img.setImageResource(R.drawable.sunny);
				}
				else if (myData.getForecast().get(4).equals("Chance of Snow")
						|| myData.getForecast().get(4).equals("Snow")
						|| myData.getForecast().get(4).equals("Snow Showers")
						|| myData.getForecast().get(4).equals("Icy")
						|| myData.getForecast().get(4).equals("Haze")
						|| myData.getForecast().get(4).equals("Flurries")) 
				{
					day5_condition_img.setImageResource(R.drawable.snow);
				}
				else if (myData.getForecast().get(4).equals("Clear")) 
				{
					day5_condition_img.setImageResource(R.drawable.mostly_sunny);
				}
				else if (myData.getForecast().get(4).equals("Fog")) 
				{
					day5_condition_img.setImageResource(R.drawable.fog);
				}
				
			} catch (Exception e) {
				
				/*
				 * Catching error in city name or failed internet connection and toasting a message for use to check settings
				 */
				Toast.makeText(MainView.this,
						"Failed! Check User and Internet Settings",
						Toast.LENGTH_LONG).show();
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		new MyAsyncTask().execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			startActivity(new Intent(this, Preferences.class));
			break;

		case R.id.item2:
			update();
			break;

		case R.id.item3:
			finish();
			break;

		case R.id.item4:
			startActivity(new Intent(this, WeatherGallery.class));
			break;
		}
		return true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		update();

	}
	
	
	public void update() {
		new MyAsyncTask().execute();
	}

}
