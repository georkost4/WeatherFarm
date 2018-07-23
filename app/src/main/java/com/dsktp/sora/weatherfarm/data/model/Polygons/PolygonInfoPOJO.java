package com.dsktp.sora.weatherfarm.data.model.Polygons;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class PolygonInfoPOJO
{
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
}
