package com.dsktp.sora.weatherfarm.utils;

import java.text.DecimalFormat;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class TempUtils
{

    public static String kelvinToFahrenheit(double kelvinTemperature)
    {
        //todo to be implemented
        return null;
    }

    public static String kelvinToCelsius(double kelvinTemperature)
    {
        //todo to be implemented
        DecimalFormat df2 = new DecimalFormat(".1");
        double temperature = kelvinTemperature-272.15;
        return df2.format(temperature);
    }


}
