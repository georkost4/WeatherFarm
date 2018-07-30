package com.dsktp.sora.weatherfarm.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.adapters.WeatherAdapter;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.viewmodel.WeatherForecastViewModel;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.ImageUtils;
import com.dsktp.sora.weatherfarm.utils.TempUtils;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 27/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class FragmentWeatherForecast extends Fragment implements WeatherAdapter.onClickListener {
    private static final String DEBUG_TAG = "#FragmentWeatherForecast";
    private View mInflatedView;
    private RecyclerView mRecyclerView;
    private WeatherAdapter mAdapter;
    private WeatherForecastViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_current_forecast,container,false);

        //bind the views
        mRecyclerView = mInflatedView.findViewById(R.id.rv_5_day_forecast);
        final TextView tvCondition = (mInflatedView.findViewById(R.id.tv_weather_condition_label));
        final TextView tvTemperature = (mInflatedView.findViewById(R.id.tv_weather_day_temperature));
        final ImageView ivWeatherImage = mInflatedView.findViewById(R.id.iv_weather_forecast_icon);
        final TextView tvLocation = mInflatedView.findViewById(R.id.tv_weather_location_label);

        //setup RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WeatherAdapter();
        mAdapter.setCallback(this);

        //get the ViewModel and observe the LiveData object for changes
        mViewModel = ViewModelProviders.of(this).get(WeatherForecastViewModel.class);
        mViewModel.getWeatherList().observe(getViewLifecycleOwner(), new Observer<List<WeatherForecastPOJO>>() {
            @Override
            public void onChanged(@Nullable List<WeatherForecastPOJO> weatherForecastPOJOS)
            {
                //the first time the App loads the list will be empty
                if(!weatherForecastPOJOS.isEmpty()) //todo remove if necessary
                {
                    Log.d(DEBUG_TAG,"WeatherForecastPOJOS list size = " + weatherForecastPOJOS.size());
                    // populate the UI
                    tvCondition.setText(weatherForecastPOJOS.get(0).getWeather().get(0).getDescription());
                    tvTemperature.setText(TempUtils.formatToCelsiousSing(TempUtils.kelvinToCelsius(weatherForecastPOJOS.get(0).getMain().getTemp())));
                    ivWeatherImage.setImageResource(ImageUtils.getIcon(weatherForecastPOJOS.get(0).getWeather().get(0).getDescription()));
                    tvLocation.setText(AppUtils.getSelectedPosition(getContext())[0]);
                    //set the new data to the adapter
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setList(weatherForecastPOJOS);
                }
            }});

        return mInflatedView;
    }

    @Override
    public void handleClick(WeatherForecastPOJO weatherForecastPOJO) {
        Toast.makeText(getContext(),"To be implemented",Toast.LENGTH_SHORT).show();
    }
}

