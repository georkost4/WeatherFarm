package com.dsktp.sora.weatherfarm.data.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.model.Ground.Soil;
import com.dsktp.sora.weatherfarm.data.model.Ground.UVindex;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.utils.Converter;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class represents a Room Database implementation . The member variables are classes that each one hold
 * methods for specific tables in this database.It also implements the Singleton Pattern to make sure that only
 * one instance of thi class is active.
 */
@Database(entities = {Soil.class, UVindex.class, WeatherForecastPOJO.class, PolygonInfoPOJO.class}, version = 1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AppDatabase extends RoomDatabase
{
    private static AppDatabase sDbInstance;
    public abstract UVindexDao uVindexDao();
    public abstract SoilDataDao soilDataDao();
    public abstract PolygonDao polygonDao();
    public abstract WeatherForecastDao weatherForecastDao();

    /**
     * Singleton Constructor
     * @param context The context to create the Database
     * @return AppDatabase object
     */
    public static AppDatabase getsDbInstance(Context context)
    {
        if(sDbInstance == null)
        {
            //create the room database
            sDbInstance = Room.databaseBuilder(context,AppDatabase.class,"ApplicationDatabase").build();
        }
        return sDbInstance;
    }
}
