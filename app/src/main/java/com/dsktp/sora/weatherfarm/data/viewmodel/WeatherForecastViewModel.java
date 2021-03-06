package com.dsktp.sora.weatherfarm.data.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class defines a ViewModel used for the WeatherForecast objects
 * to be observable from the fragment and update the UI when a change
 * occurs to the data
 */
public class WeatherForecastViewModel extends AndroidViewModel
{
    private static final String DEBUG_TAG = "#WeahterForecastViewModel";
    private LiveData<List<WeatherForecastPOJO>> weatherList;

    public WeatherForecastViewModel(Application application)
    {
        super(application);
        Log.d(DEBUG_TAG,"Querying the database");
        weatherList = AppDatabase.getsDbInstance(application.getBaseContext()).weatherForecastDao().getWeatherEntries();
    }

    public LiveData<List<WeatherForecastPOJO>> getWeatherList() {
        return weatherList;
    }
}
