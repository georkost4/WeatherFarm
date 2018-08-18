package com.dsktp.sora.weatherfarm.utils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 2/8/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

import android.content.Context;

/**
 * This class contains formatting methods for imperial/metric unit settings
 */
public class FormatUtils
{
    /**
     * This method takes an unformatted input and returns the input formatted with "°C" sign at the end
     * indicating the Celsius.
     * @param unformattedTemp The input temperature
     * @return formatted String with the "C" sign at the end
     */
    public static String formatTemperature(double unformattedTemp, Context context)
    {
        //todo change metric for celsious
        //todo changew imperial for fahrenheit
        String units = AppUtils.getUnitUserPreference(context);
        switch (units)
        {
            case Constants.PREFERENCES_UNITS_IMPERIAL_VALUE:
            {
                return TempUtils.kelvinToFahrenheit(unformattedTemp) + " F";

            }
            case Constants.PREFERENCES_UNITS_METRIC_VALUE:
            {
                return TempUtils.kelvinToCelsius(unformattedTemp) + " °C";
            }
            default:
            {
                return TempUtils.kelvinToCelsius(unformattedTemp) + " °C";
            }
        }

    }

    /**
     * This method takes a double input  representing the humidity and returns a formatted String
     * with the " %" sign at the end
     * @param humidity The humidity value
     * @return formatted String e.g (85 %)
     */
    public static String formatHumidity(double humidity)
    {
        return humidity + " %";
    }

    /**
     * This method takes input a double representing the pressure and returns
     * a formatted string with the " hPa" at the end to indicate the units of
     * the value
     * @param pressure The pressure value
     * @return String formatted with " hPa"
     */
    public static String formatPressure(double pressure)
    {
        return pressure + " hPa";
    }

    /**
     * This method takes a double representing the wind speed and returns a String
     * formatted with the correct unit. So the return value for input 23 could
     * be "23 m/s"
     * @param windSpeed The wind speed value
     * @return String formatted with correct units
     */
    public static String formatWindSpeed(double windSpeed,Context context)
    {
        String units = AppUtils.getUnitUserPreference(context);
        switch (units)
        {
            case Constants.PREFERENCES_UNITS_IMPERIAL_VALUE:
            {
                return windSpeed + " miles/h";
            }
            case Constants.PREFERENCES_UNITS_METRIC_VALUE:
            {

                return windSpeed + " m/s";
            }
            default:
            {
                return windSpeed + " m/s";
            }
        }
    }

    /**
     * This method takes a double representing the wind degree and returns a String
     * formatted with the correct unit. So the return value for input 23 could
     * be "23 deg"
     * @param windDegrees The wind degree value
     * @return String formatted with correct units
     */
    public static String formatWindDegrees(double windDegrees)
    {
        return windDegrees + " deg";
    }

    /**
     * This method takes a double representing the cloudiness and returns a String
     * formatted with the correct unit. So the return value for input 23 could
     * be "23 %"
     * @param cloudiness The cloudiness value
     * @return String formatted with correct units
     */
    public static String formatCloudiness(double cloudiness)
    {
        return cloudiness + " %";
    }
}
