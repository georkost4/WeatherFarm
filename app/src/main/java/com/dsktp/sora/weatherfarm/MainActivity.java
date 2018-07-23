package com.dsktp.sora.weatherfarm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 20/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        RemoteRepository remoteRepository = new RemoteRepository();

//        remoteRepository.getCurrentForecast();
//
//        remoteRepository.getCurrentSoilData();
//
//        remoteRepository.getCurrentUVIndex();
//
//        remoteRepository.getForecastLatLon();
//
//        remoteRepository.getForecastPolygon();

//        remoteRepository.sendPolygon();

//        remoteRepository.getPolygonInfo();
//          remoteRepository.getListOfPolygons();
//          remoteRepository.removePolygon();
//          remoteRepository.getListOfPolygons();
    }
}
