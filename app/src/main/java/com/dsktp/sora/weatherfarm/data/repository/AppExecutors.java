package com.dsktp.sora.weatherfarm.data.repository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class AppExecutors
{
    private static AppExecutors sInstance;
    private final Executor roomIO;

    private AppExecutors(Executor roomIO) {
        this.roomIO = roomIO;
    }

    public static AppExecutors getInstance()
    {
        if(sInstance == null)
        {
            sInstance = new AppExecutors(Executors.newSingleThreadExecutor());
        }
        return sInstance;
    }

    public Executor getRoomIO() { return roomIO; }
}
