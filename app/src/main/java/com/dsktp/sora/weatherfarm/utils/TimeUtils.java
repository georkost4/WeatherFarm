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
public class TimeUtils
{
    private static final String DEBUG_TAG = "#TimeUtils";


    public static List<WeatherForecastPOJO> filterListByDay(List<WeatherForecastPOJO> list)
    {
        String tomorrow = unixToDay((Calendar.getInstance().getTimeInMillis())/1000 + 86400);
        String tomorrow1 = unixToDay((System.currentTimeMillis()/1000 + 86400*2));
        String tomorrow2 = unixToDay(System.currentTimeMillis()/1000 + 86400*3);
        String tomorrow3 = unixToDay(System.currentTimeMillis()/1000 + 86400*4);

        List<WeatherForecastPOJO> filteredList = new ArrayList<>();
        filteredList.add(list.get(0));
        boolean flagTommorrow = false;
        boolean flagTomorrow1 = false;
        boolean flagTomorrow2 = false;
        boolean flagTomorrow3 = false;

        Log.d(DEBUG_TAG,"List size = " + list.size());
        for(int i=0;i<list.size();i++)
        {
            if(unixToDay(list.get(i).getDt()).equals(tomorrow))
            {
                if(!flagTommorrow)
                {
                    Log.d(DEBUG_TAG,"Added for tomorrow");
                    filteredList.add(list.get(i));
                    flagTommorrow = true;
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

    public static long secondsEllapsedSinceSync(long timeInMilli)
    {
        long currentTime = System.currentTimeMillis();
        Log.d(DEBUG_TAG,"Current time in millis " + currentTime);
        Log.d(DEBUG_TAG,"Last updated time in millis " + timeInMilli);
        long ellapsedTime = currentTime - timeInMilli;

        Log.d(DEBUG_TAG,"Ellapsed seconds = " + ellapsedTime);
        return ellapsedTime;
    }


    public static String unixToDate(long timeInMilli)
    {
        return new java.text.SimpleDateFormat("MMM d").format(new java.util.Date (timeInMilli*1000));
    }

    public static String unixToDateTime(long timeInMilli)
    {
        return new java.text.SimpleDateFormat("MMM d HH:mm").format(new java.util.Date (timeInMilli*1000));
    }

    public static String unixToDay(long timeInMilli)
    {
        return new java.text.SimpleDateFormat("EEEE").format(new java.util.Date (timeInMilli*1000));

    }

    public static String getCurrentDay()
    {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

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
