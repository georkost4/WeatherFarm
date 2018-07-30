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

    public static int getIcon(String icon)
    {
        //todo to be implemented
        if(icon.contains("rain")){ return R.drawable.ic_rain; }
        else if(icon.contains("light rain")) return R.drawable.ic_light_rain;
        else if(icon.contains("clear")) return R.drawable.ic_clear;
        else if(icon.contains("few cloud")||icon.contains("broken clouds")) return R.drawable.ic_light_clouds;
        else if(icon.contains("cloud")) return R.drawable.ic_cloudy;
        else if(icon.contains("fog")) return R.drawable.ic_fog;
        else if(icon.contains("snow")) return R.drawable.ic_snow;
        else if(icon.contains("storm")) return R.drawable.ic_storm;
        return R.drawable.ic_image_placeholder;

    }

}
