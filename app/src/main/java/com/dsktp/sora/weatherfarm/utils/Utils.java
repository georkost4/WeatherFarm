package com.dsktp.sora.weatherfarm.utils;

import java.util.Date;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 25/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class Utils
{
    public static class Time
    {
        public static Date unixToDate(long timeInMilli)
        {
            //todo to be implemented
            return null;
        }

    }
    public static class Temperature
    {
        public static double kelvinToFahrenheit(double kelvinTemperature)
        {
            //todo to be implemented
            return 0;
        }

        public static double kelvingToCelcius(double kelvinTemperature)
        {
            //todo to be implemented
            return 0;
        }
    }
    public static class Area
    {
        public static double squareMetersToHectares(double squareMeters)
        {
            // 1ha = 10000 m^2
            return squareMeters / 10000;
        }
    }
}
