package com.dsktp.sora.weatherfarm.data.model.Polygons;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */


/**
 * This class represents a GeoJSON object that is a part of the API response
 * when you request the List of Polygons .
 */
public  class GeoJSON
{
    private final String type = "Feature";
    private PolygonProperties properties;
    private Geometry geometry;

    public GeoJSON(PolygonProperties properties, Geometry geometry) {
        this.properties = properties;
        this.geometry = geometry;
    }



    public String getType() {
        return type;
    }

    public PolygonProperties getProperties() {
        return properties;
    }

    public void setProperties(PolygonProperties properties)
    {
        this.properties = properties;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
