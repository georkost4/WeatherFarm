package com.dsktp.sora.weatherfarm.data.network;

import android.util.Log;
import com.dsktp.sora.weatherfarm.BuildConfig;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastResponsePOJO;
import com.dsktp.sora.weatherfarm.data.model.Ground.Soil;
import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;

import java.util.List;

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
    public void getForecastLatLon() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        WeatherWebService service = retrofit.create(WeatherWebService.class);

        Call<List<WeatherForecastResponsePOJO>> responsePOJOCall = service.WeatherLatLongForecast("41.725584", "26.425015", BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<List<WeatherForecastResponsePOJO>>() {
            @Override
            public void onResponse(Call<List<WeatherForecastResponsePOJO>> call, Response<List<WeatherForecastResponsePOJO>> response) {

                if (response.isSuccessful()) {
                    int time = response.body().get(0).getDt();
                    Log.d("DEBUG", "time in first element = " + time);

                    int time1 = response.body().get(4).getDt();
                    Log.d("DEBUG", "time in third element = " + time1);
                } else {
                    Log.d("DEBUG", "Response error body = " + response.errorBody());
                    Log.d("DEBUG", "Response error  = " + response.toString());
                    Log.d("DEBUG", "Respone message = " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<WeatherForecastResponsePOJO>> call, Throwable t) {
                Log.e("DEBUG", "There was an error");
                t.printStackTrace();
            }
        });
    }

        public void getForecastPolygon()
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();



            WeatherWebService service = retrofit.create(WeatherWebService.class);

            Call<List<WeatherForecastResponsePOJO>> responsePOJOCall = service.WeatherPolygonForecast("5b508976002a87000908ca41", BuildConfig.AgroMonitorAPIKey);

            responsePOJOCall.enqueue(new Callback<List<WeatherForecastResponsePOJO>>() {
                @Override
                public void onResponse(Call<List<WeatherForecastResponsePOJO>> call, Response<List<WeatherForecastResponsePOJO>> response) {

                    if(response.isSuccessful()) {
                        int time = response.body().get(0).getDt();
                        Log.d("DEBUG", "time in first element = " + time);

                        int time1 = response.body().get(4).getDt();
                        Log.d("DEBUG", "time in third element = " + time1);
                    }
                    else
                    {
                        Log.d("DEBUG","Response error body = " + response.errorBody());
                        Log.d("DEBUG","Response error  = " + response.toString());
                        Log.d("DEBUG","Respone message = " + response.message() );
                    }
                }

                @Override
                public void onFailure(Call<List<WeatherForecastResponsePOJO>> call, Throwable t) {
                    Log.e("DEBUG","There was an error");
                    t.printStackTrace();
                }
            });

    }

    public void getCurrentForecast()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        WeatherWebService service = retrofit.create(WeatherWebService.class);

        Call<WeatherForecastResponsePOJO> responsePOJOCall = service.currentWeatherLatLongForecast("41.725584","26.425015", BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<WeatherForecastResponsePOJO>() {
            @Override
            public void onResponse(Call<WeatherForecastResponsePOJO> call, Response<WeatherForecastResponsePOJO> response) {

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
            public void onFailure(Call<WeatherForecastResponsePOJO> call, Throwable t) {
                Log.e("DEBUG","There was an error");
                t.printStackTrace();
            }
        });

    }

    public void getCurrentSoilData()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        SoilDataWebService service = retrofit.create(SoilDataWebService.class);

        Call<Soil> responsePOJOCall = service.currentSoilDataLatLongForecast("41.725584","26.425015", BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<Soil>() {
            @Override
            public void onResponse(Call<Soil> call, Response<Soil> response) {

                if(response.isSuccessful()) {
                    double moisture =  response.body().getMoisture();
                    Log.d("DEBUG", "Moisture = " + moisture);
                }
                else
                {
                    Log.d("DEBUG","Response error body = " + response.errorBody());
                    Log.d("DEBUG","Response error  = " + response.toString());
                    Log.d("DEBUG","Respone message = " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<Soil> call, Throwable t) {
                Log.e("DEBUG","There was an error");
                t.printStackTrace();
            }
        });

    }

    public void getCurrentUVIndex()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        UViDataWebService service = retrofit.create(UViDataWebService.class);

        Call<UVindex> responsePOJOCall = service.currentUViDataLatLongForecast("41.725584","26.425015", BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<UVindex>() {
            @Override
            public void onResponse(Call<UVindex> call, Response<UVindex> response) {

                if(response.isSuccessful()) {
                    double uvi = (int) response.body().getUvi();
                    Log.d("DEBUG", "UV index = " + uvi);
                }
                else
                {
                    Log.d("DEBUG","Response error body = " + response.errorBody());
                    Log.d("DEBUG","Response error  = " + response.toString());
                    Log.d("DEBUG","Respone message = " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<UVindex> call, Throwable t) {
                Log.e("DEBUG","There was an error");
                t.printStackTrace();
            }
        });

    }

}
