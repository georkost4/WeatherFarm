package com.dsktp.sora.weatherfarm.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.adapters.WeatherAdapter;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 27/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class FragmentWeatherForecast extends Fragment
{
    private static final String DEBUG_TAG = "#FragmentWeatherForecast";
    private View mInflatedView;
    private RecyclerView mRecyclerView;
    private LiveData<List<WeatherForecastPOJO>> mWeatherData;
    private WeatherAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_current_forecast,container,false);

        mAdapter = new WeatherAdapter();
        mRecyclerView = mInflatedView.findViewById(R.id.rv_5_day_forecast);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));




        mWeatherData = AppDatabase.getsDbInstance(getContext()).weatherForecastDao().getWeatherEntries(); //load the forecast from the db

        mWeatherData.observe(getViewLifecycleOwner(), new Observer<List<WeatherForecastPOJO>>() {
            @Override
            public void onChanged(@Nullable List<WeatherForecastPOJO> weatherForecastPOJOS)
            {
                Log.d(DEBUG_TAG,"On Changed called");
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setList(weatherForecastPOJOS);
            }
        });






        return mInflatedView;
    }
}
