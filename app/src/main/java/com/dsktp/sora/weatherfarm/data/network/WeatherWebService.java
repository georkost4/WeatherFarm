package com.dsktp.sora.weatherfarm.data.network;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This interface defines the methods that the Retrofit library will use
 * to send and receive Weather Forecast data from the Agro Monitoring API.
 * The following methods define the web endpoint along with any Path and Query variables.
 */
interface WeatherWebService
{
    @GET("weather")
    Call<WeatherForecastPOJO> currentWeatherLatLongForecast(@Query("lat") String latitude, @Query("lon") String longtitude, @Query("appid") String API_KEY);

    @GET("weather")
    Call<WeatherForecastPOJO> currentWeatherPolygonForecast(@Query("polyid") String polygonID, @Query("appid") String API_KEY);

    @GET("weather/forecast")
    Call<List<WeatherForecastPOJO>> WeatherLatLongForecast(@Query("lat") String latitude, @Query("lon") String longtitude, @Query("appid") String API_KEY);

    @GET("weather/forecast")
    Call<List<WeatherForecastPOJO>> WeatherPolygonForecast(@Query("polyid") String polygonID, @Query("appid") String API_KEY);


}
