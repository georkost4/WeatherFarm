package com.dsktp.sora.weatherfarm.data.model.Polygons;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

public class PolygonPOJO
{
    private String name;
    private GeoJSON geo_json;



    public PolygonPOJO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeoJSON getGeo_json() {
        return geo_json;
    }

    public void setGeo_json(GeoJSON geo_json) {
        this.geo_json = geo_json;
    }








}



