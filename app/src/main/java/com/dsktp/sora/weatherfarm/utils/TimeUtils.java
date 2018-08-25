package com.dsktp.sora.weatherfarm.utils;

import android.util.Log;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contains Time methods for formatting ang getting time data
 */
public class TimeUtils
{
    private static final String DEBUG_TAG = "#TimeUtils";

    /**
     * This method takes the List containing the 5 day forecast for a place
     * and returns a list of 5 object's containing weather data for the first 3h of the day
     * For example the returning list will contain , lets say the time now is 6 am on Sunday.
     * The returning list will contain list.get(0) WeatherForecastPOJO for 6 am Sunday.
     * The list.get(1) will contain data for 12 am Monday.
     * The list.get(2) will contain data for 12 am Tuesday.
     * The list.get(3) will contain data for 12 am Wednesday.
     * The list.get(4) will contain data for 12 am Thursday.
     * @param list The list with the 5 day forecast items
     * @return The 5 day forecast list.
     */
    public static List<WeatherForecastPOJO> filterListByDay(List<WeatherForecastPOJO> list)
    {
        String tomorrow = unixToDay((Calendar.getInstance().getTimeInMillis())/1000 + 86400);
        String tomorrow1 = unixToDay((System.currentTimeMillis()/1000 + 86400*2));
        String tomorrow2 = unixToDay(System.currentTimeMillis()/1000 + 86400*3);
        String tomorrow3 = unixToDay(System.currentTimeMillis()/1000 + 86400*4);

        List<WeatherForecastPOJO> filteredList = new ArrayList<>();
        filteredList.add(list.get(0));
        boolean flagTomorrow = false;
        boolean flagTomorrow1 = false;
        boolean flagTomorrow2 = false;
        boolean flagTomorrow3 = false;

        Log.d(DEBUG_TAG,"List size = " + list.size());
        // filter out the days from the 5 day list
        // by checking if it meets the if condition
        // the first one will be list(0)
        for(int i=0;i<list.size();i++)
        {
            if(unixToDay(list.get(i).getDt()).equals(tomorrow))
            {
                if(!flagTomorrow)
                {
                    Log.d(DEBUG_TAG,"Added for tomorrow");
                    filteredList.add(list.get(i));
                    flagTomorrow = true;
                }
            }
            if(unixToDay(list.get(i).getDt()).equals(tomorrow1))
            {
                if(!flagTomorrow1)
                {
                    Log.d(DEBUG_TAG,"Added for tomorrow1");
                    filteredList.add(list.get(i));
                    flagTomorrow1 = true;
                }
            }
            if(unixToDay(list.get(i).getDt()).equals(tomorrow2))
            {
                if(!flagTomorrow2)
                {
                    Log.d(DEBUG_TAG,"Added for tomorrow2");
                    filteredList.add(list.get(i));
                    flagTomorrow2 = true;
                }

            }
            if(unixToDay(list.get(i).getDt()).equals(tomorrow3))
            {
                if(!flagTomorrow3)
                {
                    Log.d(DEBUG_TAG,"Added for tomorrow3");
                    filteredList.add(list.get(i));
                    flagTomorrow3 = true;
                }

            }

        }
        return filteredList;

    }

    /**
     * This method compares the time we last synced with the current time
     * To decrease the repeating calls to the server.
     * @param timeInMilli The time in miliseconds since last update
     * @return The deference between now and last time synced
     */
    public static long secondsEllapsedSinceSync(long timeInMilli)
    {
        long currentTime = System.currentTimeMillis();
        Log.d(DEBUG_TAG,"Current time in millis " + currentTime);
        Log.d(DEBUG_TAG,"Last updated time in millis " + timeInMilli);
        long ellapsedTime = currentTime - timeInMilli;

        Log.d(DEBUG_TAG,"Ellapsed seconds = " + ellapsedTime);
        return ellapsedTime;
    }

    /**
     * This method takes input the time in millis and returns the time in human readable
     * form e.g for input 1533581302000 the method returns Aug 6
     * @param timeInMilli The time in milliseconds
     * @return in formatted text Month Day ( Aug 6)
     */
    public static String unixToDate(long timeInMilli)
    {
        return new java.text.SimpleDateFormat("MMM d").format(new java.util.Date (timeInMilli*1000));
    }

    /**
     * This method takes input the time in millis and returns the time in human readable
     * form e.g for input 1533581302000 the method returns Aug 6 21:50
     * @param timeInMilli The time in milliseconds
     * @return in formatted text Month Day Hour:minutes ( Aug 6 21:50)
     */
    public static String unixToDateTime(long timeInMilli)
    {
        return new java.text.SimpleDateFormat("MMM d HH:mm").format(new java.util.Date (timeInMilli*1000));
    }

    /**
     * This method takes input the time in millis and returns the time in human readable
     * form e.g for input 1533581302000 the method returns Monay
     * @param timeInMilli The time in milliseconds
     * @return in formatted text Day ( Monday)
     */
    public static String unixToDay(long timeInMilli)
    {
        return new java.text.SimpleDateFormat("EEEE").format(new java.util.Date (timeInMilli*1000));

    }

    /**
     * todo refactor the javadoc
     * This method takes input the time in millis and returns the time in human readable
     * form e.g for input 1533581302000 the method returns Monay
     * @param timeInMilli The time in milliseconds
     * @return in formatted text Day ( Monday)
     */
    public static String unixToDayTime(long timeInMilli)
    {
        return new java.text.SimpleDateFormat("EEEE HH:mm").format(new java.util.Date (timeInMilli*1000));

    }

    /**
     * This method returns the current day of the week
     * @return String containing the current day of the week
     */
    public static String getDay(int day)
    {
        Calendar calendar = Calendar.getInstance();

        String mera = "null";
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
        return mera;
    }

}
