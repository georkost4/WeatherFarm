package com.dsktp.sora.weatherfarm.data.model.Polygons;

import com.google.gson.annotations.SerializedName;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
class Coordinates
{
    @SerializedName("1")
    private String latitude;
    @SerializedName("0")
    private String longtitude;

    public Coordinates(String latitude, String longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

//    @Override
//    public String toString() {
//        return "[" + latitude +","+ longtitude + "]" ;
//    }
}