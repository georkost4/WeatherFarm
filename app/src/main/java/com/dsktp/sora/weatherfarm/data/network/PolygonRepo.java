package com.dsktp.sora.weatherfarm.data.network;

import android.content.Context;
import android.util.Log;

import com.dsktp.sora.weatherfarm.BuildConfig;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.model.Polygons.GeoJSON;
import com.dsktp.sora.weatherfarm.data.model.Polygons.Geometry;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonProperties;
import com.dsktp.sora.weatherfarm.data.model.Polygons.SendPolygonPOJO;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.AppExecutors;
import com.dsktp.sora.weatherfarm.data.repository.PolygonDao;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.dsktp.sora.weatherfarm.utils.Constants.BASE_AGRO_MONITORING_URL;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 25/8/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class PolygonRepo
{
    private static final String DEBUG_TAG = "#PolygonRepo";
    private RemoteRepository mRemoteRepo;

    public PolygonRepo() {
    }

    public PolygonRepo(RemoteRepository mRemoteRepo) {
        this.mRemoteRepo = mRemoteRepo;
    }

    public void removePolygon(final String polygonID, final Context context)
    {
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_AGRO_MONITORING_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PolygonWebService polygonWebService = retrofitBuilder.create(PolygonWebService.class);

        Call<ResponseBody> request = polygonWebService.deletePolygon(polygonID, BuildConfig.AgroMonitorAPIKey);

        request.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    if(response.code() == 204) //todo create valid code responses Constants
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
                    mRemoteRepo.mPolyListCallback.populateList(response.body());
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

        Log.d(DEBUG_TAG,mRemoteRepo.bodyToString(responsePOJOCall.request().body()));

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
                            mRemoteRepo.mMapCallback.updateOnSuccess();
                            Log.i(DEBUG_TAG,"Rows affected = " + rowsAffected);

                        }
                    });

                }
                else
                {
                    Log.d(DEBUG_TAG,"Respone message = " + response.message() );
                    mRemoteRepo.mMapCallback.updateOnFailure();
                }
            }

            @Override
            public void onFailure(Call<PolygonInfoPOJO> call, Throwable t) {
                Log.e(DEBUG_TAG,"There was an error sending polygon to the server");
                t.printStackTrace();
            }
        });


    }
}
