package com.weather.droid;

import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.widget.ImageView;


public class WeatherAnimation 
{
	
	public void clear_Animation(ImageView img)
	{		
		//passing animation to background of imageview
        img.setBackgroundResource(R.anim.anime);
        Log.v("myFilter","Animation Drawing");
        
        //creating fame animation
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
	}
	
	public void cloudy_Animation(ImageView img)
	{
		
        img.setBackgroundResource(R.anim.cloudy_animation);
        Log.v("myFilter","Animation Drawing");
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        //frameAnimation.setCallback(img);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
	}
	
	public void fog_Animation(ImageView img)
	{
		
        img.setBackgroundResource(R.anim.fog_animation);
        Log.v("myFilter","Animation Drawing");
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        //frameAnimation.setCallback(img);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
	}
	
	public void rainy_Animation(ImageView img)
	{
		
        img.setBackgroundResource(R.anim.rain_animation);
        Log.v("myFilter","Animation Drawing");
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        //frameAnimation.setCallback(img);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
	}
	
	public void snow_Animation(ImageView img)
	{
		
        img.setBackgroundResource(R.anim.snow_animation);
        Log.v("myFilter","Animation Drawing");
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        //frameAnimation.setCallback(img);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
	}
	
	public void storm_Animation(ImageView img)
	{
		
        img.setBackgroundResource(R.anim.storm_animation);
        Log.v("myFilter","Animation Drawing");
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        //frameAnimation.setCallback(img);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
	}
	
	public void sunny_Animation(ImageView img)
	{
		
        img.setBackgroundResource(R.anim.sunny_animation);
        Log.v("myFilter","Animation Drawing");
        AnimationDrawable frameAnimation = (AnimationDrawable)img.getBackground();
        //frameAnimation.setCallback(img);
        frameAnimation.setVisible(true, true);
        frameAnimation.start();
	}
	
}
