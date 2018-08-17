package com.dsktp.sora.weatherfarm.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.gms.location.places.Place;

import static com.dsktp.sora.weatherfarm.utils.Constants.NO_LATITUDE;
import static com.dsktp.sora.weatherfarm.utils.Constants.NO_LONGITUDE;
import static com.dsktp.sora.weatherfarm.utils.Constants.NO_PLACE;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_CONNECTIVITY_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_CURRENT_PLACE_LATITUDE_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_CURRENT_PLACE_LONGTITUDE_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_IS_POLYGON_LIST_SYNCED;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_UNITS_IMPERIAL_VALUE;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_UNITS_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.PREFERENCES_UNITS_METRIC_VALUE;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contains static helper methods to get and save values like last time fetched from server
 * save and get current position, save and get selected position.
 */
public class AppUtils
{
    private static final String DEBUG_TAG = "#AppUtils";


    /**
     * Saves into preference the time in millis that we last fetched data from the server
     * @param context The context used to access shared preferences
     * @param lastUpdated The time in millis
     */
    public static void saveLastUpdatedValue(Context context, long lastUpdated)
    {
           PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(Constants.PREFERENCES_SAVE_LAST_UPDATED_KEY,lastUpdated).apply();
    }

    /**
     * This method returns the value of last updated time in millis
     * @param context The context to access shared preferences
     * @return long representing the time in millis
     */
    public static long getLastUpdated(Context context)
    {
        long lastUpdated = PreferenceManager.getDefaultSharedPreferences(context).getLong(Constants.PREFERENCES_SAVE_LAST_UPDATED_KEY,-1);
        Log.d(DEBUG_TAG,"Last updated = " + lastUpdated );
        return lastUpdated;

    }

    /**
     * This method saves into preferences the selected location we entered to get weather forecast data
     * @param place The place object containing the name , latitude , longtitude of the place
     * @param context The context object
     */
    public  static void saveSelectedPosition(Place place,Context context)
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(Constants.PREFERENCES_SELECTED_PLACE_NAME_KEY, String.valueOf(place.getName())).apply();
        sharedPreferences.edit().putString(Constants.PREFERENCES_SELECTED_PLACE_LATITUDE_KEY, String.valueOf(place.getLatLng().latitude)).apply();
        sharedPreferences.edit().putString(Constants.PREFERENCES_SELECTED_PLACE_LONGTITUDE_KEY, String.valueOf(place.getLatLng().longitude)).apply();

        Log.d(DEBUG_TAG,"Saving values.. name = "+ place.getName() + " lat = " + place.getLatLng().latitude + " lon = " + place.getLatLng().longitude);
    }

    /**
     * This method return an array of string representing the selected position we entered to get weather forecast info.
     * The returnedValue[0] contains the name of the place
     * The returnedValue[1] contains the latitude of the place
     * The returnedValue[2] contains the longitude of the place
     * @param context The context to access the shared preferences
     * @return String[] containing the name,latitude,longitude of the place
     */
    public static String[] getSelectedPosition(Context context)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] values = new String[3];

        values[0] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_NAME_KEY,NO_PLACE);
        values[1] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LATITUDE_KEY,NO_LATITUDE);
        values[2] = sharedPreferences.getString(Constants.PREFERENCES_SELECTED_PLACE_LONGTITUDE_KEY, NO_LONGITUDE);

        Log.i(DEBUG_TAG,"Getting values.. name = "+ values[0] + " lat = " + values[1] + " lon = " + values[2]);
        return values;
    }

    /**
     * This method saves into shared preferences the current place we obtained from the device via gps or other method
     * @param context The context to access the shared preferences
     * @param location The location object that contains the latitude,longitude of the current place
     */
    public static void saveCurrentPosition(Context context, Location location)
    {
        //todo use reverse geo coding to find the locantion name
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        sharedPreferences.edit().putString("selected_place_name_key", String.valueOf(place.getName())).apply();
        sharedPreferences.edit().putString(PREFERENCES_CURRENT_PLACE_LATITUDE_KEY, String.valueOf(location.getLatitude())).apply();
        sharedPreferences.edit().putString(PREFERENCES_CURRENT_PLACE_LONGTITUDE_KEY, String.valueOf(location.getLongitude())).apply();
    }

    /**
     * This method return an array of string representing the current position we obtained from device to get weather forecast info.
     * The returnedValue[0] contains the latitude of the place
     * The returnedValue[1] contains the longitude of the place
     * @param context The context to access the shared preferences
     * @return String[] containing the latitude,longitude of the place
     */
    public static String[] getCurrentPosition(Context context)
    {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String[] values = new String[2];

        values[0] = sharedPreferences.getString(PREFERENCES_CURRENT_PLACE_LATITUDE_KEY,NO_LATITUDE);
        values[1] = sharedPreferences.getString(PREFERENCES_CURRENT_PLACE_LONGTITUDE_KEY, NO_LONGITUDE);
        return  values;
    }

    /**
     * This method saves into preferences a boolean value representing if we have synced the polygon list from the server
     * @param context THe context object to access the preferences
     */
    public static void setPolygonListBeenSynced(Context context)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREFERENCES_IS_POLYGON_LIST_SYNCED,true).apply();
    }

    /**
     * This method returns a boolean representing whether we have synced the list of polygons from the server
     * @param context The context to access the shared preferences
     * @return boolean representing if we have synced the data with the server.
     */
    public static boolean hasThePolygonListSynced(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREFERENCES_IS_POLYGON_LIST_SYNCED,false);
    }

    /**
     * This method saves the network state to shared preferences
     * @param context The context to access shared preferences
     * @param value The boolean value of whether we have internet connection or not
     */
    public static void saveNetworkState(Context context,boolean value)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREFERENCES_CONNECTIVITY_KEY,value).apply();
    }

    /**
     * This method returns the network state.
     * @param context The context to access the shared preferences
     * @return boolean representing whether we have internet access or not
     */
    public static boolean getNetworkState(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREFERENCES_CONNECTIVITY_KEY,false);
    }

    /**
     * This method retrieves the user preferred units . Imperial or
     * Metric.
     * @param context The Context object to access the shared preferences
     * @return String representing the user preferred units
     */
    public static String getUnitUserPreference(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREFERENCES_UNITS_KEY,PREFERENCES_UNITS_IMPERIAL_VALUE);

    }

    /**
     * This method saves the units preference for the user into.
     * shared app preferences for later user .It can
     * take two values either Metric or Imperial.
     * @param context The Context object to access the preferences
     * @param units The String values of the units
     */
    public static void saveUnitUserPreference(Context context,String units)
    {
        Log.i(DEBUG_TAG,"Setting the user preferred unit to = " + units);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(Constants.PREFERENCES_UNITS_KEY,units)
                .apply();

    }
}
