package com.dsktp.sora.weatherfarm.data.model.Polygons;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
@Entity(tableName = "polygonTable")
public class PolygonInfoPOJO
{
    @NonNull
    @PrimaryKey
    private String id;
    private GeoJSON geo_json;
    private String name;
    private double[] center;
    private double area;
    private String user_id;

    public PolygonInfoPOJO() {
    }

    public String getId() {
        return id;
    }

    public GeoJSON getGeo_json() {
        return geo_json;
    }

    public String getName() {
        return name;
    }

    public double[] getCenter() {
        return center;
    }

    public double getArea() {
        return area;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGeo_json(GeoJSON geo_json) {
        this.geo_json = geo_json;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCenter(double[] center) {
        this.center = center;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
