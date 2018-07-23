package com.dsktp.sora.weatherfarm.data.network;

import android.util.Log;
import com.dsktp.sora.weatherfarm.BuildConfig;
import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastResponsePOJO;
import com.dsktp.sora.weatherfarm.data.model.Ground.Soil;
import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;
import com.dsktp.sora.weatherfarm.data.model.Polygons.Coordinates;
import com.dsktp.sora.weatherfarm.data.model.Polygons.GeoJSON;
import com.dsktp.sora.weatherfarm.data.model.Polygons.Geometry;
import com.dsktp.sora.weatherfarm.data.model.Polygons.Point;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonProperties;
import com.dsktp.sora.weatherfarm.data.model.Polygons.SendPolygonPOJO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.PUT;


/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class RemoteRepository
{
    private  String DEBUG_TAG = "#" + getClass().getSimpleName();

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

    public void removePolygon()
    {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PolygonWebService polygonWebService = retrofitBuilder.create(PolygonWebService.class);

        Call<ResponseBody> request = polygonWebService.deletePolygon("5b559f2c002a87000908ca8e",BuildConfig.AgroMonitorAPIKey);

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    if(response.code() == 204)
                    {
                        Log.d(DEBUG_TAG,"The polygon was successfully removed from the server");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void getListOfPolygons()
    {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PolygonWebService polygonWebService = retrofitBuilder.create(PolygonWebService.class);

        Call<List<PolygonInfoPOJO>> request = polygonWebService.getListOfPolygons(BuildConfig.AgroMonitorAPIKey);

        request.enqueue(new Callback<List<PolygonInfoPOJO>>() {
            @Override
            public void onResponse(Call<List<PolygonInfoPOJO>> call, Response<List<PolygonInfoPOJO>> response) {
                if(response.isSuccessful())
                {
                    Log.d(DEBUG_TAG,"Getting list of polygons request if successful");
                    List<PolygonInfoPOJO> listOfPolygons = response.body();
                    Log.d(DEBUG_TAG,"Number of polygons = " + listOfPolygons.size());
                    Log.d(DEBUG_TAG,"Name of Second Polygon = " + listOfPolygons.get(1).getName());
                }
            }

            @Override
            public void onFailure(Call<List<PolygonInfoPOJO>> call, Throwable t) {

            }
        });
    }

    public void getPolygonInfo()
    {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PolygonWebService polygonWebService = retrofitBuilder.create(PolygonWebService.class);

        Call<PolygonInfoPOJO> request = polygonWebService.getPolygonInfo("5b559f2c002a87000908ca8e",BuildConfig.AgroMonitorAPIKey);

        request.enqueue(new Callback<PolygonInfoPOJO>() {
            @Override
            public void onResponse(Call<PolygonInfoPOJO> call, Response<PolygonInfoPOJO> response) {
                if(response.isSuccessful())
                {
                    Log.d(DEBUG_TAG,"The response info for the polygon is successful");

                    double[] center = response.body().getCenter();
                    Log.d(DEBUG_TAG,"Center of the polygon = " + center[0] + " , " + center[1]);
                }
            }

            @Override
            public void onFailure(Call<PolygonInfoPOJO> call, Throwable t) {

            }
        });
    }


    public void sendPolygon()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.agromonitoring.com/agro/1.0/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        PolygonWebService service = retrofit.create(PolygonWebService.class);



        SendPolygonPOJO polygonPOJO = new SendPolygonPOJO();
        polygonPOJO.setName("Rizia1");


        double[][] coordinatesArray = new double[5][2];

        coordinatesArray[0][0] = 41.621653;
        coordinatesArray[0][1] = 26.434190;

        coordinatesArray[1][0] = 41.623153;
        coordinatesArray[1][1] = 26.435756;

        coordinatesArray[2][0] = 41.624701;
        coordinatesArray[2][1] = 26.433063;

        coordinatesArray[3][0] = 41.623177;
        coordinatesArray[3][1] = 26.431121;

        coordinatesArray[4][0] = 41.621653;
        coordinatesArray[4][1] = 26.434190;


        List<double[][]> pointList = new ArrayList<>();

        pointList.add(coordinatesArray);

        Geometry polygonGeometry = new Geometry(pointList);
        GeoJSON data = new GeoJSON(new PolygonProperties(),polygonGeometry);

        polygonPOJO.setGeo_json(data);


        Call<SendPolygonPOJO> responsePOJOCall = service.sendPolygon(BuildConfig.AgroMonitorAPIKey,polygonPOJO);

        Log.d("DEBUG",bodyToString(responsePOJOCall.request().body()));

        responsePOJOCall.enqueue(new Callback<SendPolygonPOJO>() {
            @Override
            public void onResponse(Call<SendPolygonPOJO> call, Response<SendPolygonPOJO> response) {

                if(response.isSuccessful()) {
                    Log.d("DEBUG", "Response message from polygon =  " + response.toString());
                }
                else
                {
                    try {
                        Log.d("DEBUG", String.valueOf(call.request().body().contentLength()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("DEBUG","Response error body = " + response.errorBody());
                    Log.d("DEBUG","Response error  = " + response.toString());
                    Log.d("DEBUG","Respone message = " + response.message() );
                    Log.d("DEBUG","Respone message = " + response.body() );
                    Log.d("DEBUG","Respone message = " + response.raw() );

                }
            }

            @Override
            public void onFailure(Call<SendPolygonPOJO> call, Throwable t) {
                Log.e("DEBUG","There was an error");
                t.printStackTrace();
            }
        });


    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "Could not read request body data";
        }
    }

}
