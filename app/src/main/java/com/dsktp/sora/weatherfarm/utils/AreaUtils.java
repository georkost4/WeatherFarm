package com.dsktp.sora.weatherfarm.utils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contains util methods for Area manipulation
 */
public class AreaUtils
{

    /**
     * This method takes input in m^2 and return hectares
     * @param squareMeters The value in m^2
     * @return double representing the input in hectares
     */
    public static double squareMetersToHectares(double squareMeters)
    {
        // 1ha = 10000 m^2
        return squareMeters / 10000;
    }
}
