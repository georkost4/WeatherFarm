package com.dsktp.sora.weatherfarm.utils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class AreaUtils
{
        public static double squareMetersToHectares(double squareMeters)
        {
            // 1ha = 10000 m^2
            return squareMeters / 10000;
        }
}
