package com.dsktp.sora.weatherfarm.utils;
import java.text.DecimalFormat;


/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contains method to calculate temperature.
 */
public class TempUtils
{

    /**
     * This method takes input a double representing temperature in kelvin units.
     * Returns a String representing temperature in Fahrenheit
     * @param kelvinTemperature The double value of the temperature
     * @return String representing temperature in Fahrenheit
     */
    public static String kelvinToFahrenheit(double kelvinTemperature)
    {
        //todo to be implemented
        return null;
    }

    /**
     * This method takes input a double representing temperature in kelvin units.
     * Returns a String representing temperature in Celsius
     * @param kelvinTemperature The double value of the temperature
     * @return String representing temperature in Celsius
     */
    public static String kelvinToCelsius(double kelvinTemperature)
    {
        //todo to be implemented
        DecimalFormat df2 = new DecimalFormat(".1");
        double temperature = kelvinTemperature-272.15;
        return df2.format(temperature);
    }


}
