package com.dsktp.sora.weatherfarm.data.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Room;

import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
@Dao
public interface PolygonDao
{
    @Query("SELECT * FROM polygonTable")
    LiveData<List<PolygonInfoPOJO>> getPolygons();

    @Insert
    long insertPolygon(PolygonInfoPOJO polygonInfoPOJO);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPolygonList(List<PolygonInfoPOJO> polygonInfoPOJOS);

    @Query("DELETE FROM polygonTable WHERE id = :polygonID")
    int deletePolygon(String polygonID);
}
