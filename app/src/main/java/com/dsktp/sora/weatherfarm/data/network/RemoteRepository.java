package com.dsktp.sora.weatherfarm.data.network;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.dsktp.sora.weatherfarm.BuildConfig;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.model.Ground.Soil;
import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;
import com.dsktp.sora.weatherfarm.data.model.Polygons.GeoJSON;
import com.dsktp.sora.weatherfarm.data.model.Polygons.Geometry;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonProperties;
import com.dsktp.sora.weatherfarm.data.model.Polygons.SendPolygonPOJO;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.AppExecutors;
import com.dsktp.sora.weatherfarm.data.repository.PolygonDao;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.TimeUtils;
import com.dsktp.sora.weatherfarm.widget.MyWidgetProvider;
import com.google.android.gms.maps.model.LatLng;

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

import static com.dsktp.sora.weatherfarm.utils.Constants.BASE_AGRO_MONITORING_URL;


/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class RemoteRepository
{
    private  static  String DEBUG_TAG = "#RemoteRepository.java";
    private onFailure mMapCallback;
    private deliveryCallBack mPolyListCallback;
    private static RemoteRepository sInstance;

    private RemoteRepository() {

    }
    public static RemoteRepository getsInstance()
    {
        if(sInstance == null)
        {
            Log.d(DEBUG_TAG,"Created new instance");
            return sInstance = new RemoteRepository();
        }
        return sInstance;
    }

    public void setmPolyListCallback(deliveryCallBack mPolyListCallback) {
        this.mPolyListCallback = mPolyListCallback;
    }

    public void setmMapCallback(onFailure mMapCallback) {
        this.mMapCallback = mMapCallback;
    }


    public void getForecastLatLon(String lat, String lon, final Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        WeatherWebService service = retrofit.create(WeatherWebService.class);

        Call<List<WeatherForecastPOJO>> responsePOJOCall = service.WeatherLatLongForecast(lat, lon, BuildConfig.AgroMonitorAPIKey);



        if(TimeUtils.secondsEllapsedSinceSync(AppUtils.getLastUpdated(context))<20000) return; // if the ellapsed time is bigger than 10 seconds sync with the server
        Log.d(DEBUG_TAG,"Making a request for the forecast data to the server");
        //show the loading indicator

        Log.d(DEBUG_TAG,"Last updated = " + System.currentTimeMillis());
        AppUtils.saveLastUpdatedValue(context,System.currentTimeMillis());

        responsePOJOCall.enqueue(new Callback<List<WeatherForecastPOJO>>() {
            @Override
            public void onResponse(Call<List<WeatherForecastPOJO>> call, final Response<List<WeatherForecastPOJO>> response) {

                if (response.isSuccessful())
                {
                    final List<WeatherForecastPOJO> weatherForecastPOJO = response.body();
                    Log.d(DEBUG_TAG,"List size  from response = " + weatherForecastPOJO.size());
                    AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Log.d(DEBUG_TAG,"Deleting old data from database");
                            AppDatabase.getsDbInstance(context).weatherForecastDao().deleteOldData();
                            Log.d(DEBUG_TAG,"Inserting new data from database");
                            AppDatabase.getsDbInstance(context).weatherForecastDao().insertWeatherForecastEntry(weatherForecastPOJO);


                            //send a broadcast to update the Widget info
                            Intent intent = new Intent(context, MyWidgetProvider.class);
                            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
                            int[] ids = AppWidgetManager.getInstance(context.getApplicationContext()).getAppWidgetIds(new ComponentName(context.getApplicationContext(), MyWidgetProvider.class));
                            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
                            context.sendBroadcast(intent);


                        }
                    });
                }
                else
                {
                    Log.e(DEBUG_TAG, "Response message = " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<WeatherForecastPOJO>> call, Throwable t) {
                Log.e(DEBUG_TAG, "There was an error getting forecast for lat/lon");
                t.printStackTrace();
            }
        });
    }

        public void getForecastPolygon(String polygonID, final Context context)
        {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_AGRO_MONITORING_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();



            WeatherWebService service = retrofit.create(WeatherWebService.class);

            Call<List<WeatherForecastPOJO>> responsePOJOCall = service.WeatherPolygonForecast(polygonID, BuildConfig.AgroMonitorAPIKey);

            responsePOJOCall.enqueue(new Callback<List<WeatherForecastPOJO>>() {
                @Override
                public void onResponse(Call<List<WeatherForecastPOJO>> call, Response<List<WeatherForecastPOJO>> response) {

                    if(response.isSuccessful())
                    {
                        mPolyListCallback.populateList(response.body());
                    }
                    else
                    {
                        Log.d(DEBUG_TAG,"Respone message = " + response.message() );
                    }
                }

                @Override
                public void onFailure(Call<List<WeatherForecastPOJO>> call, Throwable t) {
                    Log.e(DEBUG_TAG,"There was an error getting forecast data for polygon");
                    t.printStackTrace();
                }
            });

    }

    public void getCurrentForecast(String lat, String lon, final Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        WeatherWebService service = retrofit.create(WeatherWebService.class);

        Call<WeatherForecastPOJO> responsePOJOCall = service.currentWeatherLatLongForecast(lat,lon, BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<WeatherForecastPOJO>() {
            @Override
            public void onResponse(Call<WeatherForecastPOJO> call, Response<WeatherForecastPOJO> response) {

                if(response.isSuccessful()) {
                    final WeatherForecastPOJO weatherForecastPOJO = response.body();
                    double humidity = weatherForecastPOJO.getMain().getHumidity();
                    Log.d(DEBUG_TAG,"Humidity = " + humidity);
                    AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
                        @Override
                        public void run() {
//                            AppDatabase.getsDbInstance(context).weatherForecastDao().insertWeatherForecastEntry(weatherForecastPOJO); //todo fix the list
                        }
                    });

                }
                else
                {
                    Log.d(DEBUG_TAG,"Respone message = " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<WeatherForecastPOJO> call, Throwable t) {
                Log.e(DEBUG_TAG,"There was an error getting current forecast data for lat/lon");
                t.printStackTrace();
            }
        });

    }

    public void getCurrentSoilData(String lat,String lon)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        SoilDataWebService service = retrofit.create(SoilDataWebService.class);

        Call<Soil> responsePOJOCall = service.currentSoilDataLatLongForecast(lat,lon, BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<Soil>() {
            @Override
            public void onResponse(Call<Soil> call, Response<Soil> response) {

                if(response.isSuccessful()) {
                    double moisture =  response.body().getMoisture();
                    Log.d(DEBUG_TAG, "Moisture = " + moisture);
                }
                else
                {
                    Log.d(DEBUG_TAG,"Respone message = " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<Soil> call, Throwable t) {
                Log.e(DEBUG_TAG,"There was an error getting the current soil data for lat/lon");
                t.printStackTrace();
            }
        });

    }

    public void getCurrentUVIndex(String lat , String lon)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        UViDataWebService service = retrofit.create(UViDataWebService.class);



        Call<UVindex> responsePOJOCall = service.currentUViDataLatLongForecast(lat,lon, BuildConfig.AgroMonitorAPIKey);

        responsePOJOCall.enqueue(new Callback<UVindex>() {
            @Override
            public void onResponse(Call<UVindex> call, Response<UVindex> response) {

                if(response.isSuccessful()) {
                    double uvi = (int) response.body().getUvi();
                    Log.d(DEBUG_TAG, "UV index = " + uvi);
                }
                else
                {
                    Log.d(DEBUG_TAG,"Respone message = " + response.message() );
                }
            }

            @Override
            public void onFailure(Call<UVindex> call, Throwable t) {
                Log.e(DEBUG_TAG,"There was an error getting the current UV index for lat/lon");
                t.printStackTrace();
            }
        });

    }

    public void removePolygon(final String polygonID, final Context context)
    {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PolygonWebService polygonWebService = retrofitBuilder.create(PolygonWebService.class);

        Call<ResponseBody> request = polygonWebService.deletePolygon(polygonID,BuildConfig.AgroMonitorAPIKey);

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    if(response.code() == 204)
                    {
                        Log.d(DEBUG_TAG,"The polygon was successfully removed from the server");
                        //remove the polygon also from the local database
                        final PolygonDao dao = AppDatabase.getsDbInstance(context).polygonDao();
                        AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                int rowsAffected = dao.deletePolygon(polygonID);
                                //show a toast to the user to confirm the deletion
                                Log.i(DEBUG_TAG,"Rows deleted = " + rowsAffected);
                            }
                        });
                    }
                    else
                    {
                        Log.e(DEBUG_TAG,"There was an error removing the polygon from the server");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t)
            {
                t.printStackTrace();
            }
        });
    }

    public void getListOfPolygons(final Context context)
    {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
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
                    final List<PolygonInfoPOJO> listOfPolygons = response.body();
                    AppUtils.setPolygonListBeenSynced(context); // save that the polygon list has been synced
                    AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getsDbInstance(context).polygonDao().insertPolygonList(listOfPolygons);
                        }
                    });
                    Log.d(DEBUG_TAG,"Number of polygons = " + listOfPolygons.size());
                }
            }

            @Override
            public void onFailure(Call<List<PolygonInfoPOJO>> call, Throwable t) {
                t.printStackTrace();
                Log.e(DEBUG_TAG,"There was an error retrieving polygon list from the server");
            }
        });
    }

    public void getPolygonInfo(String polygonID)
    {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PolygonWebService polygonWebService = retrofitBuilder.create(PolygonWebService.class);

        Call<PolygonInfoPOJO> request = polygonWebService.getPolygonInfo(polygonID,BuildConfig.AgroMonitorAPIKey);

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
                t.printStackTrace();
                Log.e(DEBUG_TAG,"There was an error retrieving info for a polygon");
            }
        });
    }


    public void sendPolygon(List<LatLng> points, String polygonName, final Context context)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        PolygonWebService service = retrofit.create(PolygonWebService.class);


        SendPolygonPOJO sendPolygonPOJO = new SendPolygonPOJO();
        sendPolygonPOJO.setName(polygonName);

        double[][] coordinatesArray = new double[5][2];


        coordinatesArray[0][0] = points.get(0).latitude;
        coordinatesArray[0][1] = points.get(0).longitude;

        coordinatesArray[1][0] = points.get(1).latitude;
        coordinatesArray[1][1] = points.get(1).longitude;

        coordinatesArray[2][0] = points.get(2).latitude;
        coordinatesArray[2][1] = points.get(2).longitude;

        coordinatesArray[3][0] = points.get(3).latitude;
        coordinatesArray[3][1] = points.get(3).longitude;

        coordinatesArray[4][0] = points.get(0).latitude;
        coordinatesArray[4][1] = points.get(0).longitude;


        List<double[][]> pointList = new ArrayList<>();

        pointList.add(coordinatesArray);

        Geometry polygonGeometry = new Geometry(pointList);
        GeoJSON data = new GeoJSON(new PolygonProperties(),polygonGeometry);

        sendPolygonPOJO.setGeo_json(data);

        Call<PolygonInfoPOJO> responsePOJOCall = service.sendPolygon(BuildConfig.AgroMonitorAPIKey, sendPolygonPOJO);

        Log.d(DEBUG_TAG,bodyToString(responsePOJOCall.request().body()));

        responsePOJOCall.enqueue(new Callback<PolygonInfoPOJO>() {
            @Override
            public void onResponse(Call<PolygonInfoPOJO> call, final Response<PolygonInfoPOJO> response) {

                if(response.isSuccessful()) {
                    Log.d(DEBUG_TAG, "Response message from polygon =  " + response.toString());
                    final PolygonDao mDao = AppDatabase.getsDbInstance(context).polygonDao();
                    AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            // insert the response to the local database
                            long rowsAffected = mDao.insertPolygon(response.body());
                            mMapCallback.updateOnSuccess();
                            Log.i(DEBUG_TAG,"Rows affected = " + rowsAffected);

                        }
                    });

                }
                else
                {
                    Log.d(DEBUG_TAG,"Respone message = " + response.message() );
                    mMapCallback.updateOnFailure();
                }
            }

            @Override
            public void onFailure(Call<PolygonInfoPOJO> call, Throwable t) {
                Log.e(DEBUG_TAG,"There was an error sending polygon to the server");
                t.printStackTrace();
            }
        });


    }

    private static String bodyToString(final RequestBody request){
        try

        {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            copy.writeTo(buffer);
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "Could not read request body data";
        }
    }


    public interface  onFailure
    {
        void updateOnFailure();
        void updateOnSuccess();
    }

    public interface deliveryCallBack
    {
        void populateList(List<WeatherForecastPOJO> polygonList);
    }


}
