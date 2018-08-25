package com.dsktp.sora.weatherfarm.utils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

import android.arch.persistence.room.TypeConverter;
import android.util.Log;

import com.dsktp.sora.weatherfarm.data.model.Forecast.Cloud;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Main;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Rain;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Snow;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Weather;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Wind;
import com.dsktp.sora.weatherfarm.data.model.Polygons.GeoJSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class contain's method's for converting a ArrayList<E> into a JSON string and vice versa. This
 * is done so we can store them to the local Room database.
 */
public class Converter
{
    @TypeConverter
    public static GeoJSON GeoJSONFromString(String value) {
        Type listType = new TypeToken<GeoJSON>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String GeoJSONToString(GeoJSON geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }


    @TypeConverter
    public static double[] arrayFromString(String value) {
        Type listType = new TypeToken<double[]>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String arrayToString(double[] array) {
        Gson gson = new Gson();
        String json = gson.toJson(array);
        return json;
    }


    @TypeConverter
    public static Weather WeatherFromString(String value) {
        Type listType = new TypeToken<Weather>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String WeatherToString(Weather geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }


    @TypeConverter
    public static Main MainFromString(String value) {
        Type listType = new TypeToken<Main>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String MainToString(Main geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }


    @TypeConverter
    public static Snow SnowFromString(String value) {
        Type listType = new TypeToken<Snow>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String SnownToString(Snow geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }


    @TypeConverter
    public static Rain RainFromString(String value) {
        Type listType = new TypeToken<Rain>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String RainToString(Rain geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }


    @TypeConverter
    public static Cloud CloudFromString(String value) {
        Type listType = new TypeToken<Cloud>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String CloudToString(Cloud geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }


    @TypeConverter
    public static Wind WindFromString(String value) {
        Type listType = new TypeToken<Wind>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String WindToString(Wind geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }


    @TypeConverter
    public static ArrayList<Weather> WeatherListFromString(String value) {
        Type listType = new TypeToken<ArrayList<Weather>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }


    @TypeConverter
    public static String MainToString(ArrayList<Weather> geoJSONObject) {
        Gson gson = new Gson();
        String json = gson.toJson(geoJSONObject);
        return json;
    }

}
