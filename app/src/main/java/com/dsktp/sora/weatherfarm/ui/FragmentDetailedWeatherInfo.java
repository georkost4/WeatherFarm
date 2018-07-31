package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 31/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class FragmentDetailedWeatherInfo extends Fragment
{
    private View mInflatedView;
    private WeatherForecastPOJO mWeatherForecastData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle detailedWeathearBundle = getArguments();
        mWeatherForecastData = detailedWeathearBundle.getParcelable("detailed_weather_list");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mInflatedView = inflater.inflate(R.layout.fragment_detailed_weather_forecast,container,false);

        //todo populate the data from the database




        return mInflatedView;

    }
}
