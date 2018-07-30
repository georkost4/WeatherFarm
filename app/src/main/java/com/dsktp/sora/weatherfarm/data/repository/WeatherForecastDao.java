package com.dsktp.sora.weatherfarm.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
@Dao
public interface WeatherForecastDao
{
    @Query("SELECT * FROM weatherForecastTable")
    LiveData<List<WeatherForecastPOJO>> getWeatherEntries();

    @Insert
    void insertWeatherForecastEntry(List<WeatherForecastPOJO> entrie);

    @Query("DELETE FROM weatherForecastTable WHERE dt = :entryID")
    int deleteForecastEntry(int  entryID);

    @Query("DELETE FROM weatherForecastTable")
    int deleteOldData();


}
