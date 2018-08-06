package com.dsktp.sora.weatherfarm;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.dsktp.sora.weatherfarm.data.model.Forecast.Cloud;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Main;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Rain;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Snow;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Weather;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Wind;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.WeatherForecastDao;
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
public class WeatherForecastDaoTest
{
    private AppDatabase mDb;
    private WeatherForecastDao mWeatherDao;

    @Before
    public void CreateDb()
    {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context,AppDatabase.class).build();
        mWeatherDao = mDb.weatherForecastDao();
    }

    @Test
    public void ReadWriteFromTable()
    {
        WeatherForecastPOJO dummyWeatherForeCastPOJO = new WeatherForecastPOJO();

        dummyWeatherForeCastPOJO.setClouds(new Cloud(13));
        dummyWeatherForeCastPOJO.setDt(1434);
        dummyWeatherForeCastPOJO.setMain(new Main(290,260,280,12,23,25,3));
        dummyWeatherForeCastPOJO.setRain(new Rain());
        dummyWeatherForeCastPOJO.setSnow(new Snow());
        dummyWeatherForeCastPOJO.setWind(new Wind());
        ArrayList<Weather> dummyWeatherList = new ArrayList<>();
        dummyWeatherList.add(new Weather(14,"maindummy","descDummy","24"));
        dummyWeatherForeCastPOJO.setWeather(dummyWeatherList);
        ArrayList<WeatherForecastPOJO> dummyWeatherPOJOlist = new ArrayList<>();
        dummyWeatherPOJOlist.add(dummyWeatherForeCastPOJO);

        mWeatherDao.insertWeatherForecastEntry(dummyWeatherPOJOlist);


        LiveData<List<WeatherForecastPOJO>> tableList = mWeatherDao.getWeatherEntries();
        List<WeatherForecastPOJO> list = tableList.getValue();

        assertThat(list.size(),equalTo(1));
        assertThat(list.get(0).getMain().getHumidity(),equalTo(3.0));
    }

    @Test
    public void RemoveItemFromTable()
    {
        ReadWriteFromTable();

        int rowsAffected = mWeatherDao.deleteForecastEntry(1434);
        int expectedRowsAffected = 1;

        assertThat(rowsAffected,equalTo(expectedRowsAffected));

        int rowsAffected1 = mWeatherDao.deleteForecastEntry(14324);
        int expectedRowsAffected1 = 0;

        assertThat(rowsAffected1,equalTo(expectedRowsAffected1));
    }


    @After
    public void CloseDb()
    {
        mDb.close();
    }
}
