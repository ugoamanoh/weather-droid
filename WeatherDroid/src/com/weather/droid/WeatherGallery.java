package com.weather.droid;

import java.io.IOException;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

public class WeatherGallery extends Activity {

	private Gallery gallery;
	private ImageView imgView;
	Button btn;

	private Integer[] Imgid = { R.drawable.clear, R.drawable.clear_1,
			R.drawable.clear_2, R.drawable.clear_3, R.drawable.clear_4,
			R.drawable.clear_5, R.drawable.sunny_0, R.drawable.sunny_1,
			R.drawable.sunny_2, R.drawable.sunny_3, R.drawable.sunny_4,
			R.drawable.sunny_5, R.drawable.sunny_6, R.drawable.rainy_0,
			R.drawable.rainy_1, R.drawable.rainy_2, R.drawable.rainy_3,
			R.drawable.rainy_4, R.drawable.rainy_5, R.drawable.rainy_6,
			R.drawable.rainy_7, R.drawable.rainy_8, R.drawable.rainy_9,
			R.drawable.cloudy_0, R.drawable.cloudy_1, R.drawable.cloudy_2,
			R.drawable.cloudy_3, R.drawable.cloudy_4, R.drawable.cloudy_5,
			R.drawable.cloudy_6, R.drawable.cloudy_7, R.drawable.snow_0,
			R.drawable.snow_1, R.drawable.snow_2, R.drawable.snow_3,
			R.drawable.snow_4, R.drawable.snow_5, R.drawable.snow_6,
			R.drawable.snow_7, R.drawable.snow_8, R.drawable.snow_9,
			R.drawable.fog_0, R.drawable.fog_1, R.drawable.fog_2,
			R.drawable.fog_3, R.drawable.fog_4, R.drawable.fog_5,
			R.drawable.fog_6, R.drawable.fog_7, R.drawable.fog_8, };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.gallery);

		imgView = (ImageView) findViewById(R.id.ImageView01);
		imgView.setBackgroundResource(Imgid[0]);
		
		btn = (Button) findViewById(R.id.button1);

		gallery = (Gallery) findViewById(R.id.examplegallery);
		gallery.setAdapter(new AddImgAdp(this));

		gallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				imgView.setBackgroundResource(Imgid[position]);
			}
		});

		btn.setOnClickListener(new View.OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	
	        	try {
	        	    // Set background from a resource
	        	    WallpaperManager.getInstance(WeatherGallery.this).setResource(Imgid[gallery.getSelectedItemPosition()]);
	        	    

	        	} catch (IOException e) {
	        	// TODO Auto-generated catch block
	        	e.printStackTrace();
	        	}


	        }
	    });
	}
	
	

	public class AddImgAdp extends BaseAdapter {
		int GalItemBg;
		private Context cont;

		public AddImgAdp(Context c) {
			cont = c;
			TypedArray typArray = obtainStyledAttributes(R.styleable.GalleryTheme);
			GalItemBg = typArray.getResourceId(
					R.styleable.GalleryTheme_android_galleryItemBackground, 0);
			typArray.recycle();
		}

		public int getCount() {
			return Imgid.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}
		
		
		public View getView(int position, View convertView, ViewGroup parent) {
			
			//creating image view and setting background
			ImageView imgView = new ImageView(cont);
			imgView.setImageResource(Imgid[position]);
			imgView.setLayoutParams(new Gallery.LayoutParams(80, 70));
			imgView.setScaleType(ImageView.ScaleType.FIT_XY);
			imgView.setBackgroundResource(GalItemBg);

			return imgView;
		}
		
		
	}

}
