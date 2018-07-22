package com.dsktp.sora.weatherfarm.data.model.Forecast;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class WeatherResponsePOJO
{
    private int dt;
    private ArrayList<Weather> weather;
    private Main main;
    private Wind wind;
    private Cloud clouds;

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

//    public Weather getWeather() {
//        return weather;
//    }

//    public void setWeather(Weather weather) {
//        this.weather = weather;
//    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }
}
