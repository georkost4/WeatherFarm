package com.dsktp.sora.weatherfarm.data.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;

import java.util.List;


/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
@Dao
public interface UVindexDao
{
    @Query("SELECT * FROM CurrentUltraVioletDataTable")
    List<UVindex> getAllEntries();

    @Insert
    void insertUViEntry(UVindex entrie);

    @Delete
    void delete(UVindex entrie);
}
