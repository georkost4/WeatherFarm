package com.dsktp.sora.weatherfarm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_CURRENT_PLACE_LATITUDE_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_CURRENT_PLACE_LONGTITUDE_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_IS_POLYGON_LIST_SYNCED;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class AppUtils
{
    private static final String DEBUG_TAG = "#AppUtils";


    public static void saveLastUpdatedValue(Context context, long lastUpdated)
    {
           PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(Constants.PREFERENCES_SAVE_LAST_UPDATED_KEY,lastUpdated).apply();
    }
    public static long getLastUpdated(Context context)
    {
        //todo to be implemented

        long lastUpdated = PreferenceManager.getDefaultSharedPreferences(context).getLong(Constants.PREFERENCES_SAVE_LAST_UPDATED_KEY,-1);
        Log.d(DEBUG_TAG,"Last updated = " + lastUpdated );
        return lastUpdated;

    }
    public  static void saveSelectedPosition(Place place,Context context)
    {
        //todo to be implemented
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(Constants.PREFERENCES_SELECTED_PLACE_NAME_KEY, String.valueOf(place.getName())).apply();
        sharedPreferences.edit().putString(Constants.PREFERENCES_SELECTED_PLACE_LATITUDE_KEY, String.valueOf(place.getLatLng().latitude)).apply();
        sharedPreferences.edit().putString(Constants.PREFERENCES_SELECTED_PLACE_LONGTITUDE_KEY, String.valueOf(place.getLatLng().longitude)).apply();

        Log.d(DEBUG_TAG,"Saving values.. name = "+ place.getName() + " lat = " + place.getLatLng().latitude + " lon = " + place.getLatLng().longitude);
    }

    public static String[] getSelectedPosition(Context context)
    {
        //todo to be implemented
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] values = new String[3];

        values[0] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_NAME_KEY,"Unknown name");
        values[1] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LATITUDE_KEY,"Unkown lat");
        values[2] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LONGTITUDE_KEY,"Unknown long");

        Log.i(DEBUG_TAG,"Getting values.. name = "+ values[0] + " lat = " + values[1] + " lon = " + values[2]);
        return values;
    }

    public static void saveCurrentPosition(Context context, Location location)
    {
        //todo use reverse geo coding to find the locantion name
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        sharedPreferences.edit().putString("selected_place_name_key", String.valueOf(place.getName())).apply();
        sharedPreferences.edit().putString(PREFERENCES_CURRENT_PLACE_LATITUDE_KEY, String.valueOf(location.getLatitude())).apply();
        sharedPreferences.edit().putString(PREFERENCES_CURRENT_PLACE_LONGTITUDE_KEY, String.valueOf(location.getLongitude())).apply();
    }

    public void getCurrentPosition(Context context)
    {
        //todo to be implemented
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] values = new String[2];

        values[0] = sharedPreferences.getString(PREFERENCES_CURRENT_PLACE_LATITUDE_KEY,"Unkown lat");
        values[1] = sharedPreferences.getString(PREFERENCES_CURRENT_PLACE_LONGTITUDE_KEY,"Unknown long");
    }

    public static void setPolygonListBeenSynced(Context context)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREFERENCES_IS_POLYGON_LIST_SYNCED,true).apply();
    }
    public static boolean hasThePolygonListSynced(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREFERENCES_IS_POLYGON_LIST_SYNCED,false);
    }

}
