package com.dsktp.sora.weatherfarm.data.network;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastResponsePOJO;

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
public interface WeatherWebService
{
    @GET("weather")
    Call<WeatherForecastResponsePOJO> currentWeatherLatLongForecast(@Query("lat") String latitude,@Query("lon") String longtitude,@Query("appid") String API_KEY);

    @GET("weather")
    Call<WeatherForecastResponsePOJO> currentWeatherPolygonForecast(@Query("polyid") String polygonID,@Query("appid") String API_KEY);

    @GET("weather/forecast")
    Call<List<WeatherForecastResponsePOJO>> WeatherLatLongForecast(@Query("lat") String latitude, @Query("lon") String longtitude, @Query("appid") String API_KEY);

    @GET("weather/forecast")
    Call<List<WeatherForecastResponsePOJO>> WeatherPolygonForecast(@Query("polyid") String polygonID,@Query("appid") String API_KEY);


}
