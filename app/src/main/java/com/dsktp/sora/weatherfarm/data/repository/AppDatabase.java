package com.dsktp.sora.weatherfarm.data.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.dsktp.sora.weatherfarm.data.model.Ground.Soil;
import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
@Database(entities = {Soil.class, UVindex.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract UVindexDao uVindexDao();
    public abstract SoilDataDao soilDataDao();
}
