package com.dsktp.sora.weatherfarm.data.model.Polygons;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class represents a Geometry object that is a part of the API response
 * when you push a user - defined polygon to the server .
 */
public  class Geometry {
    private final String type = "Polygon";
    private List<double[][]> coordinates;


    public Geometry(List<double[][]> coordinates)
    {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }




}