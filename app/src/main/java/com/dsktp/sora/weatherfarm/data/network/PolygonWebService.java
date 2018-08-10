package com.dsktp.sora.weatherfarm.data.network;

import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.model.Polygons.SendPolygonPOJO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This interface defines the methods that the Retrofit library will use
 * to send and receive Polygon data from the Agro Monitoring API. The following
 * methods define the web endpoint along with any Path and Query variables.
 */
interface PolygonWebService
{
    @Headers({"Content-Type: application/json"})
    @POST("polygons")
    Call<PolygonInfoPOJO> sendPolygon(@Query("appid") String API_KEY, @Body SendPolygonPOJO body);

    @GET("polygons/{polyID}")
    Call<PolygonInfoPOJO> getPolygonInfo(@Path("polyID") String polyID, @Query("appid") String API_KEY);

    @GET("polygons")
    Call<List<PolygonInfoPOJO>> getListOfPolygons(@Query("appid") String API_KEY);

    @DELETE("polygons/{polyID}")
    Call<ResponseBody> deletePolygon(@Path("polyID") String polygonID,@Query("appid") String API_KEY);
}
