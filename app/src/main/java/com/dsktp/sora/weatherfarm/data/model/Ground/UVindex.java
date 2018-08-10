package com.dsktp.sora.weatherfarm.data.model.Ground;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class represents a UVindex object that is a part of the API response
 * when you request UV index forecast data. It also implements the Parcelable
 * interface so we can write the object into Room Database
 */
@Entity(tableName = "CurrentUltraVioletDataTable")
public class UVindex
{
    @PrimaryKey
    @ColumnInfo(name = "time")
    private int dt;
    @ColumnInfo(name = "uvIndex")
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
