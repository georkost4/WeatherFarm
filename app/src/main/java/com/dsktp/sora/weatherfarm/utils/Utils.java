package com.dsktp.sora.weatherfarm.utils;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.dsktp.sora.weatherfarm.R;

import java.util.Calendar;
import java.util.Date;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 25/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class Utils
{
    private static final String DEBUG_TAG = "#Utils";

    public static class Time
    {


        public static String unixToDate(long timeInMilli)
        {
            //todo to be implemented

            return null;
        }

        public static String unixToDay(long timeInMilli)
        {
            String date = new java.text.SimpleDateFormat("EEEE").format(new java.util.Date (timeInMilli*1000));
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            String mera;
            switch (day) {
                case Calendar.SUNDAY: {
                    // Current day is Sunday
                    mera = "Sunday";
                    break;
                }

                case Calendar.MONDAY: {
                    mera = "Monday";
                    // Current day is Monday
                    break;
                }

                case Calendar.TUESDAY: {
                    mera = "Tuesday";
                    break;
                }
                case Calendar.WEDNESDAY:{
                    mera = "Wedenday";
                    break;
                }
                case Calendar.THURSDAY:{
                    mera = "Thursday";
                    break;
                }
                case Calendar.FRIDAY:{
                    mera = "Friday";
                    break;
                }
                case Calendar.SATURDAY:{
                    mera = "Saturday";
                    break;
                }
                    // etc.
                }
            mera = " dn kserw";
            Log.i(DEBUG_TAG,"Day = " + date);
            return mera;
        }

    }
    public static class Temperature
    {
        public static String kelvinToFahrenheit(double kelvinTemperature)
        {
            //todo to be implemented
            return null;
        }

        public static String kelvingToCelcius(double kelvinTemperature)
        {
            //todo to be implemented
            String temperature = String.valueOf(kelvinTemperature-272.15);
            return String.format(temperature,"%.2f");
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

    public static class Image
    {
        public static int getIcon(String icon)
        {
            //todo to be implemented
            return R.drawable.ic_image_placeholder;
        }
    }
}
