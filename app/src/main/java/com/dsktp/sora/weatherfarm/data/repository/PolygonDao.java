package com.dsktp.sora.weatherfarm.data.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
    List<PolygonInfoPOJO> getPolygons();

    @Insert
    long insertPolygon(PolygonInfoPOJO polygonInfoPOJO);

    @Query("DELETE FROM polygonTable WHERE id = :polygonID")
    int deletePolygon(String polygonID);
}