package com.dsktp.sora.weatherfarm.data.model.Polygons;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public  class Geometry {
    private final String type = "Polygon";

    private List<double[][]> coordinates;

//    public Geometry(List<List<Coordinates>> coordinates) {
//        this.coordinates = coordinates;
//    }


    public Geometry(List<double[][]> coordinates) {
        this.coordinates = coordinates;
    }

    public String getType() {
        return type;
    }




}