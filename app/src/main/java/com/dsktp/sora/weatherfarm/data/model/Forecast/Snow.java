package com.dsktp.sora.weatherfarm.data.model.Forecast;

import com.google.gson.annotations.SerializedName;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class Snow
{
    @SerializedName("3h")
    private double threeHourSnowVolume;

    public Snow(double threeHourSnowVolume) {
        this.threeHourSnowVolume = threeHourSnowVolume;
    }

    public Snow() {
    }

    public double getThreeHourSnowVolume() {
        return threeHourSnowVolume;
    }

    public void setThreeHourSnowVolume(double threeHourSnowVolume) {
        this.threeHourSnowVolume = threeHourSnowVolume;
    }
}
