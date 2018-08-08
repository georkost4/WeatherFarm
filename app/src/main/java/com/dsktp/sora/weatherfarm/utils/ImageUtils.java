package com.dsktp.sora.weatherfarm.utils;

import com.dsktp.sora.weatherfarm.R;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class ImageUtils
{

    /**
     * This method takes a string input containing the weather description so the
     * input could be something like " clouds" . The method checks if the description
     * contains some keywords and returns the closest to that description int resource
     * of that weatherDescription.
     * @param weatherDescription The weather description string
     * @return int representing a drawable icon resource
     */
    public static int getIcon(String weatherDescription)
    {
        //todo add afternoon and night icons
        if(weatherDescription.contains("rain")){ return R.drawable.ic_rain; }
        else if(weatherDescription.contains("light rain")) return R.drawable.ic_light_rain;
        else if(weatherDescription.contains("clear")) return R.drawable.ic_clear;
        else if(weatherDescription.contains("few cloud")||weatherDescription.contains("broken clouds")) return R.drawable.ic_light_clouds;
        else if(weatherDescription.contains("cloud")) return R.drawable.ic_cloudy;
        else if(weatherDescription.contains("fog")) return R.drawable.ic_fog;
        else if(weatherDescription.contains("snow")) return R.drawable.ic_snow;
        else if(weatherDescription.contains("storm")) return R.drawable.ic_storm;
        return R.drawable.ic_image_placeholder;

    }

}
