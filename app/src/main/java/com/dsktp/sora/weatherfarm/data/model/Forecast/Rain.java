package com.dsktp.sora.weatherfarm.data.model.Forecast;

import com.google.gson.annotations.SerializedName;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class Rain
{
    @SerializedName("3h")
    private double threeHourRainVolume;

    public Rain(double threeHourRainVolume) {
        this.threeHourRainVolume = threeHourRainVolume;
    }

    public Rain() {
    }

    public double getThreeHourRainVolume() {
        return threeHourRainVolume;
    }

    public void setThreeHourRainVolume(double threeHourRainVolume) {
        this.threeHourRainVolume = threeHourRainVolume;
    }
}
