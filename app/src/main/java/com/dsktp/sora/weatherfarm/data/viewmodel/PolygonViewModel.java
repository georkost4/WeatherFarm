package com.dsktp.sora.weatherfarm.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class PolygonViewModel extends AndroidViewModel
{
    private static final String DEBUG_TAG = "#PolygonViewModel";
    private LiveData<List<PolygonInfoPOJO>> polygonList;

    public PolygonViewModel(@NonNull Application application) {
        super(application);
        Log.d(DEBUG_TAG,"Querying the polygon table");
        polygonList = AppDatabase.getsDbInstance(application.getBaseContext()).polygonDao().getPolygons();
    }

    public LiveData<List<PolygonInfoPOJO>> getPolygonList() {
        return polygonList;
    }
}
