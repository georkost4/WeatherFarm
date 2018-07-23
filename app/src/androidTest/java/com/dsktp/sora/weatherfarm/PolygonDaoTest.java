package com.dsktp.sora.weatherfarm;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dsktp.sora.weatherfarm.data.model.Polygons.GeoJSON;
import com.dsktp.sora.weatherfarm.data.model.Polygons.Geometry;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonProperties;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.PolygonDao;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
@RunWith(AndroidJUnit4.class)
public class PolygonDaoTest
{
    private AppDatabase mDb;
    private PolygonDao mPolygonDao;


    @Before
    public void createDB()
    {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context,AppDatabase.class).build();
        mPolygonDao = mDb.polygonDao();
    }

    @Test
    public void ReadWriteToTable()
    {
        PolygonInfoPOJO dummyObject = new PolygonInfoPOJO();
        dummyObject.setArea(190.4);
        dummyObject.setCenter(new double[]{143.5,34.5});
        dummyObject.setId("sdsddddddd2423");
        dummyObject.setName("dummyObj");
        dummyObject.setUser_id("dummyUser");

        List<double[][]> coordinates = new ArrayList<>();
        double[][] dummyPoints = new double[5][2];
        dummyPoints[0][0] = 46.23;
        dummyPoints[0][1] = 26.43;

        dummyPoints[1][0] = 48.63;
        dummyPoints[1][1] = 20.43;

        dummyPoints[2][0] = 54.23;
        dummyPoints[2][1] = 22.43;

        dummyPoints[3][0] = 77.23;
        dummyPoints[3][1] = 25.43;

        dummyPoints[4][0] = 46.23;
        dummyPoints[4][1] = 26.43;

        coordinates.add(dummyPoints);

        Geometry dummyGeometry = new Geometry(coordinates);
        GeoJSON dummyGeoJSON = new GeoJSON(new PolygonProperties(),dummyGeometry);

        dummyObject.setGeo_json(dummyGeoJSON);

        long rowsAffected = mPolygonDao.insertPolygon(dummyObject);
        long expectedRowsAffected = 1;
        assertThat(rowsAffected,equalTo(expectedRowsAffected)); // assert that one row was affected from the insertion

    }

    @Test
    public void removeEntryFromTable()
    {
        ReadWriteToTable();

        int rowsAffected = mPolygonDao.deletePolygon("sdsddddddd2423");
        int expectedRowsAffected = 1;
        assertThat(rowsAffected,equalTo(expectedRowsAffected));
    }

    @After
    public void closeDB()
    {
        mDb.close();
    }
}
