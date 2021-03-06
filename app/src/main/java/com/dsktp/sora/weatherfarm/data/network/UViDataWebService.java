package com.dsktp.sora.weatherfarm.data.network;

import com.dsktp.sora.weatherfarm.data.model.Ground.Soil;
import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 22/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This interface defines the methods that the Retrofit library will use
 * to send and receive UV index data from the Agro Monitoring API. The following
 * methods define the web endpoint along with any Path and Query variables.
 */
interface UViDataWebService
{

    @GET("uvi")
    Call<UVindex> currentUViDataLatLongForecast(@Query("lat") String latitude, @Query("lon") String longtitude, @Query("appid") String API_KEY);

    @GET("uvi")
    Call<UVindex> currentUViDataPolygonForecast(@Query("polyid") String polygonID,@Query("appid") String API_KEY);
}
