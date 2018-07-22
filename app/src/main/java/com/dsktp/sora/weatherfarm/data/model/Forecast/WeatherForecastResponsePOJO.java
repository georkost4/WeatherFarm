package com.dsktp.sora.weatherfarm.data.model.Forecast;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 22/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class WeatherForecastResponsePOJO
{
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
}
