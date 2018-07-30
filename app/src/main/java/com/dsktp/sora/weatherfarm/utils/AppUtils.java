package com.dsktp.sora.weatherfarm.utils;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class AppUtils
{
    private static final String DEBUG_TAG = "#AppUtils";
    private SharedPreferences sharedPreferences;

    public AppUtils(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public  void saveValues(int lastUpdated)
    {
           sharedPreferences.edit().putInt("last_updated_key",lastUpdated).apply();
    }
    public  int getLastUpdated()
    {
        //todo to be implemented

        int lastUpdated = sharedPreferences.getInt("last_updated_key",-1);
        Log.d(DEBUG_TAG,"Last updated = " + lastUpdated );
        return lastUpdated;

    }
    public static void saveCurrentPosition()
    {
        //todo to be implemented
    }
}
