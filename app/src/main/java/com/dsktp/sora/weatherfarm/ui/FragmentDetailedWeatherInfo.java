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
import com.dsktp.sora.weatherfarm.utils.FormatUtils;
import com.dsktp.sora.weatherfarm.utils.ImageUtils;
import com.dsktp.sora.weatherfarm.utils.TimeUtils;

import static com.dsktp.sora.weatherfarm.utils.TempUtils.kelvinToCelsius;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 31/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */


/**
 * This class represents the fragment that shows more details about
 * the current forecast weather data
 */
public class FragmentDetailedWeatherInfo extends Fragment
{
    private View mInflatedView;
    private WeatherForecastPOJO mWeatherForecastData;
    private TextView mTvLocation;
    private TextView mTvTemp;
    private TextView mTvDay;
    private ImageView mIvWeatherIcon;
    private TextView mTvMinTemp;
    private TextView mTvMaxTemp;
    private TextView mTvHumidity;
    private TextView mTvPressure;
    private TextView mTvWindSpeed;
    private TextView mTvWindDegrees;
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

        //show loading indicator
        mInflatedView.findViewById(R.id.detailed_fragment_loading_indicator).setVisibility(View.VISIBLE);
        // bind the views
        bindViews();
        //set up the toolbar
        ((ActivityMain)getActivity()).getSupportActionBar().setTitle(R.string.detailed_forecast_toolbar_title);
        ((ActivityMain)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //populate the views with data
//        mTvDay.setText(TimeUtils.unixToDate(mWeatherForecastData.getDt())); //todo fix this
        mTvLocation.setText(AppUtils.getSelectedPosition(getContext())[0]);
        mTvTemp.setText(FormatUtils.formatTemperature(mWeatherForecastData.getMain().getTemp(),getContext()));
        mIvWeatherIcon.setImageResource(ImageUtils.getIcon(mWeatherForecastData.getWeather().get(0).getDescription()));
        //Main Data
        mTvMinTemp.setText(FormatUtils.formatTemperature(mWeatherForecastData.getMain().getTemp_min(),getContext()));
        mTvMaxTemp.setText(FormatUtils.formatTemperature(mWeatherForecastData.getMain().getTemp_max(),getContext()));
        mTvPressure.setText(FormatUtils.formatPressure(mWeatherForecastData.getMain().getPressure()));
        mTvHumidity.setText(FormatUtils.formatHumidity(mWeatherForecastData.getMain().getHumidity()));
        //Wind
        mTvWindSpeed.setText(FormatUtils.formatWindSpeed(mWeatherForecastData.getWind().getSpeed(),getContext()));
        mTvWindDegrees.setText(FormatUtils.formatWindDegrees(mWeatherForecastData.getWind().getDeg()));
        //Clouds
        mTvCloudiness.setText(FormatUtils.formatCloudiness(mWeatherForecastData.getClouds().getAll()));
        //Rain
        if(mWeatherForecastData.getRain()!=null)
        {
            //make the rain section layout visible
            mInflatedView.findViewById(R.id.detail_rain_section).setVisibility(View.VISIBLE);
            //show rain data
            mTvRainVolume.setText(String.valueOf(mWeatherForecastData.getRain().getThreeHourRainVolume())); // todo format text properly

        }
        //        Snow
        if(mWeatherForecastData.getSnow()!=null)
        {
            //make the snow section layout visible
            mInflatedView.findViewById(R.id.detail_snow_section).setVisibility(View.VISIBLE);
            //show snow data
            mTvSnowVolume.setText(String.valueOf(mWeatherForecastData.getSnow().getThreeHourSnowVolume())); // todo format proeprly

        }

        //hide the loading indicator
        mInflatedView.findViewById(R.id.detailed_fragment_loading_indicator).setVisibility(View.GONE);

        return mInflatedView;

    }

    /**
     * This method binds the variables to each view using the method
     * findViewByIId of the inflated view
     */
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
