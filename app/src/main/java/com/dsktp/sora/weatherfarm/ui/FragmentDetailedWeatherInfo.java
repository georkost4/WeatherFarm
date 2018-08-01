package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.Constants;
import com.dsktp.sora.weatherfarm.utils.ImageUtils;
import com.dsktp.sora.weatherfarm.utils.TempUtils;
import com.dsktp.sora.weatherfarm.utils.TimeUtils;

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
    private TextView mTvLocation;
    private TextView mTvTemp;
    private TextView mTvDay;
    private ImageView mIvWeatherIcon;
    private TextView mTvMinTemp,mTvMaxTemp;
    private TextView mTvHumidity,mTvPressure;
    private TextView mTvWindSpeed,mTvWindDegrees;
    private TextView mTvCloudiness;
    private TextView mTvRainVolume;
    private TextView mTvSnowVolume;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle detailedWeathearBundle;
        if((detailedWeathearBundle = getArguments()) != null)
        {
            mWeatherForecastData = detailedWeathearBundle.getParcelable(Constants.DETAILED_FORECAST_ARGUMENT_KEY);
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mInflatedView = inflater.inflate(R.layout.fragment_detailed_weather_forecast,container,false);

        bindViews();

        ((ActivityMain)getActivity()).getSupportActionBar().setTitle("Detailed");
        ((ActivityMain)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //todo populate the data from the database
        mTvDay.setText(TimeUtils.unixToDate(mWeatherForecastData.getDt())); // todo implement method to convert dt to day
        mTvLocation.setText(AppUtils.getSelectedPosition(getContext())[0]);
        mTvTemp.setText(TempUtils.kelvinToCelsius(mWeatherForecastData.getMain().getTemp()));
        mIvWeatherIcon.setImageResource(ImageUtils.getIcon(mWeatherForecastData.getWeather().get(0).getDescription()));
        //Main Data
        mTvMinTemp.setText(TempUtils.kelvinToCelsius(mWeatherForecastData.getMain().getTemp_min()));
        mTvMaxTemp.setText(TempUtils.kelvinToCelsius(mWeatherForecastData.getMain().getTemp_max()));
        mTvPressure.setText(String.valueOf(mWeatherForecastData.getMain().getPressure())); // todo implement method in utils to format pressure field
        mTvHumidity.setText(String.valueOf(mWeatherForecastData.getMain().getHumidity())); // todo implement method in utils to format humidity field
        //Wind
        mTvWindSpeed.setText(String.valueOf(mWeatherForecastData.getWind().getSpeed())); //todo implement method in utils to format wind speed properly
        mTvWindDegrees.setText(String.valueOf(mWeatherForecastData.getWind().getDeg())); //todo implement method in utils to format wind degrees properly
        //Clouds
        mTvCloudiness.setText(mWeatherForecastData.getClouds().getAll()+ " %"); // todo implement method to format cloudiness properly
        //Rain
        if(mWeatherForecastData.getRain()!=null)
        {
            //show rain data
//            mTvRainVolume.setText(String.valueOf(mWeatherForecastData.getRain().getThreeHourRainVolume())); // todo format text properly

        }
        //        Snow
        if(mWeatherForecastData.getSnow()!=null)
        {
            //show snow data
//            mTvSnowVolume.setText(String.valueOf(mWeatherForecastData.getSnow().getThreeHourSnowVolume())); // todo format proeprly

        }




        return mInflatedView;

    }

    private void bindViews()
    {
        mTvLocation = mInflatedView.findViewById(R.id.tv_detail_current_location_value);
        mTvTemp = mInflatedView.findViewById(R.id.tv_detail_temp_value);
        mTvDay = mInflatedView.findViewById(R.id.tv_detail_day_value);
        mIvWeatherIcon = mInflatedView.findViewById(R.id.iv_detail_weather_condition_value);
        mTvMinTemp = mInflatedView.findViewById(R.id.tv_detail_min_temp_value);
        mTvMaxTemp = mInflatedView.findViewById(R.id.tv_detail_max_temp_value);
        mTvHumidity = mInflatedView.findViewById(R.id.tv_detail_humidity_value);
        mTvPressure = mInflatedView.findViewById(R.id.tv_detail_pressure_value);
        mTvWindSpeed = mInflatedView.findViewById(R.id.tv_detail_wind_speed_value);
        mTvWindDegrees = mInflatedView.findViewById(R.id.tv_detail_wind_degrees_value);
        mTvCloudiness = mInflatedView.findViewById(R.id.tv_detail_cloudiness_value);
        mTvRainVolume = mInflatedView.findViewById(R.id.tv_detail_rain_volume_value);
        mTvSnowVolume = mInflatedView.findViewById(R.id.tv_detail_snow_volume_value);


    }


}