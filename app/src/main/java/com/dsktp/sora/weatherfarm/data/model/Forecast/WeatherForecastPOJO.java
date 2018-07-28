package com.dsktp.sora.weatherfarm.data.model.Forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 22/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

@Entity(tableName = "weatherForecastTable")
public class WeatherForecastPOJO
{
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private int dt;
    private ArrayList<Weather> weather;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Cloud clouds;
    private Snow snow;

    public Snow getSnow() {
        return snow;
    }

    public int getDt() {
        return dt;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }
}
