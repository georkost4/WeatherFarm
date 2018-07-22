package com.dsktp.sora.weatherfarm.data.model.Ground;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class UVindex
{
    private int dt;
    private float uvi; // ultraviolet index

    public UVindex(int dt, float uvi) {
        this.dt = dt;
        this.uvi = uvi;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public float getUvi() {
        return uvi;
    }

    public void setUvi(float uvi) {
        this.uvi = uvi;
    }
}
