package com.dsktp.sora.weatherfarm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import java.util.HashSet;
import java.util.Set;

import retrofit2.http.DELETE;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class AppUtils
{
    private static final String DEBUG_TAG = "#AppUtils";


    public static void saveValues(Context context,long lastUpdated)
    {
           PreferenceManager.getDefaultSharedPreferences(context).edit().putLong("last_updated_key",lastUpdated).apply();
    }
    public static long getLastUpdated(Context context)
    {
        //todo to be implemented

        long lastUpdated = PreferenceManager.getDefaultSharedPreferences(context).getLong("last_updated_key",-1);
        Log.d(DEBUG_TAG,"Last updated = " + lastUpdated );
        return lastUpdated;

    }
    public  static void saveSelectedPosition(Place place,Context context)
    {
        //todo to be implemented
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("selected_place_name_key", String.valueOf(place.getName())).apply();
        sharedPreferences.edit().putString("selected_place_lat_key", String.valueOf(place.getLatLng().latitude)).apply();
        sharedPreferences.edit().putString("selected_place_lon_key", String.valueOf(place.getLatLng().longitude)).apply();

        Log.d(DEBUG_TAG,"Saving values.. name = "+ place.getName() + " lat = " + place.getLatLng().latitude + " lon = " + place.getLatLng().longitude);
    }

    public static String[] getSelectedPosition(Context context)
    {
        //todo to be implemented
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] values = new String[3];

        values[0] = sharedPreferences.getString("selected_place_name_key","Unknown name");
        values[1] = sharedPreferences.getString("selected_place_lat_key","Unkown lat");
        values[2] = sharedPreferences.getString("selected_place_lon_key","Unknown long");

        Log.d(DEBUG_TAG,"Getting values.. name = "+ values[0] + " lat = " + values[1] + " lon = " + values[2]);
        return values;
    }

    public static void saveCurrentPosition(Context context)
    {
        //todo to be implemented
    }

    public void getCurrentPosition(Context context)
    {
        //todo to be implemented
    }
}
