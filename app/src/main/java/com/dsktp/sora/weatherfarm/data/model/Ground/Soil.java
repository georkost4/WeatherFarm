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
@Entity(tableName = "CurrentSoilDataTable")
public class Soil
{
    @PrimaryKey
    @ColumnInfo(name = "time")
    private int dt;
    @ColumnInfo(name = "depth_temperature")
    private double t10; // 10cm inside ground temperature
    private double moisture;
    @ColumnInfo(name = "ground_temperature")
    private double t0; // ground temperature

    public Soil(int dt, double t10, double moisture, double t0) {
        this.dt = dt;
        this.t10 = t10;
        this.moisture = moisture;
        this.t0 = t0;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public double getT10() {
        return t10;
    }

    public void setT10(double t10) {
        this.t10 = t10;
    }

    public double getMoisture() {
        return moisture;
    }

    public void setMoisture(double moisture) {
        this.moisture = moisture;
    }

    public double getT0() {
        return t0;
    }

    public void setT0(double t0) {
        this.t0 = t0;
    }
}
