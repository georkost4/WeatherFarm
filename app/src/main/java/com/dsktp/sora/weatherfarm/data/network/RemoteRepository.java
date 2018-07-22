package com.dsktp.sora.weatherfarm.data.network;

import android.util.Log;
import com.dsktp.sora.weatherfarm.BuildConfig;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherResponsePOJO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class RemoteRepository
{

    public WeatherResponsePOJO getForecast()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        CurrentWeatherWebService service = retrofit.create(CurrentWeatherWebService.class);

        Call<WeatherResponsePOJO> responsePOJOCall = service.currentWeatherLatLongForecast("41.725584","26.425015", BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<WeatherResponsePOJO>() {
            @Override
            public void onResponse(Call<WeatherResponsePOJO> call, Response<WeatherResponsePOJO> response) {

                if(response.isSuccessful()) {
                    int time = response.body().getDt();
                    Log.d("DEBUG", "time = " + time);
                }
                else
                {
                    Log.d("DEBUG","Response error body = " + response.errorBody());
                    Log.d("DEBUG","Response error  = " + response.toString());
                    Log.d("DEBUG","Respone message = " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<WeatherResponsePOJO> call, Throwable t) {
                Log.e("DEBUG","There was an error");
                t.printStackTrace();
            }
        });

        return null;
    }

}
