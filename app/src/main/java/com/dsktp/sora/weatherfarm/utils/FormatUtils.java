package com.dsktp.sora.weatherfarm.utils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 2/8/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class FormatUtils
{
    public static String formatToCelsiousSing(String unformattedTemp)
    {
        return unformattedTemp + " °C";
    }

    public static String formatHumidity(double humidity)
    {
        return humidity + " %";
    }

    public static String formatPressure(double pressure)
    {
        return pressure + " hPa";
    }

    public static String formatWindSpeed(double windSpeed)
    {
        return windSpeed + " m/s";
    }

    public static String formatWindDegrees(double windDegrees)
    {
        return windDegrees + " deg";
    }

    public static String formatCloudiness(double cloudiness)
    {
        return cloudiness + " %";
    }
}
