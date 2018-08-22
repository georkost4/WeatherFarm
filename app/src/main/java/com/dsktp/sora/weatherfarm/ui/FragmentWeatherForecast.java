package com.dsktp.sora.weatherfarm.ui;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.adapters.WeatherAdapter;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.data.viewmodel.WeatherForecastViewModel;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.Constants;
import com.dsktp.sora.weatherfarm.utils.FormatUtils;
import com.dsktp.sora.weatherfarm.utils.ImageUtils;
import com.dsktp.sora.weatherfarm.utils.TempUtils;
import com.dsktp.sora.weatherfarm.utils.TimeUtils;

import java.util.List;

import static com.dsktp.sora.weatherfarm.utils.Constants.DETAILED_FORECAST_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.NO_PLACE;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 27/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class represents the Fragment Weather Forecast screen that shows the user
 * the current weather information along with the 5-day forecast list.
 */
public class FragmentWeatherForecast extends Fragment implements WeatherAdapter.onClickListener,FragmentSettings.SettingsChangeCallback {
    private static final String DEBUG_TAG = "#FragmentWeatherForecast";
    private View mInflatedView;
    private RecyclerView mRecyclerView;
    private WeatherAdapter mAdapter;
    private WeatherForecastPOJO dataToSend;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_current_forecast,container,false);
        Log.i(DEBUG_TAG,"WeatherFragment inflated...");
        //region UI configurations
        //show the loading indicator
        setLoadingIndicatorVisibility(View.VISIBLE);


        //set the title of  the toolbar
        ((ActivityMain)getActivity()).getSupportActionBar().setTitle(R.string.weather_forecast_toolbar_title);
        showToolbarButtons(); //show the toolbar buttons
        ImageButton refreshButton = getActivity().findViewById(R.id.toolbar_refresh_button);
        Button mDetailsButton = mInflatedView.findViewById(R.id.btn_weather_details); //setup detail button listener
        //show the refresh button
        refreshButton.setVisibility(View.VISIBLE);

        //bind the views
        mRecyclerView = mInflatedView.findViewById(R.id.rv_5_day_forecast);
        final TextView tvCondition = (mInflatedView.findViewById(R.id.tv_weather_condition_value));
        final TextView tvTemperature = (mInflatedView.findViewById(R.id.tv_weather_day_temperature));
        final ImageView ivWeatherImage = mInflatedView.findViewById(R.id.iv_weather_forecast_icon);
        final TextView tvLocation = mInflatedView.findViewById(R.id.tv_weather_location_value);

        //setup RecyclerView
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new WeatherAdapter();
        mAdapter.setCallback(this);

        //endregion

        //region UI button Listeners
        mDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {      onWeatherDetailsClicked(view);   }    });

        //set up refresh button listener
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRefreshButtonClick();
            }
        });
        //endregion

        //region Live Data observer and populate UI

        //get the ViewModel and observe the LiveData object for changes
        //and update the UI views when a change occurs
        WeatherForecastViewModel mViewModel = ViewModelProviders.of(this).get(WeatherForecastViewModel.class);
        mViewModel.getWeatherList().observe(getActivity(), new Observer<List<WeatherForecastPOJO>>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onChanged(@Nullable List<WeatherForecastPOJO> weatherForecastPOJOS)
            {
                //the first time the App loads the list will be empty
                if(!weatherForecastPOJOS.isEmpty())
                {
                    Log.d(DEBUG_TAG,"WeatherForecastPOJOS list size = " + weatherForecastPOJOS.size());
                    dataToSend = weatherForecastPOJOS.get(0); //save the first object to send for detailed view
                    // populate the UI
                    tvCondition.setText(weatherForecastPOJOS.get(0).getWeather().get(0).getDescription());
                    tvTemperature.setText(FormatUtils.formatTemperature(weatherForecastPOJOS.get(0).getMain().getTemp(),getContext()));
                    ivWeatherImage.setImageResource(ImageUtils.getIcon(weatherForecastPOJOS.get(0).getWeather().get(0).getDescription()));
                    tvLocation.setText(AppUtils.getSelectedPosition(getContext())[0]);
                    //set the new data to the adapter
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.setList(weatherForecastPOJOS);

                    TextView tvLastUpdated = mInflatedView.findViewById(R.id.tv_last_updated_value);
                    //get the date from the utils method
                    String date = TimeUtils.unixToDateTime(AppUtils.getLastUpdated(getContext())/1000);
                    tvLastUpdated.setText(date);

                    //hide the loading indicator
                    setLoadingIndicatorVisibility(View.GONE);
                }
            }});
        //endregion

        return mInflatedView;
    }



    /**
     * This method handles the toolbar button click "Refresh". It checks for internet
     * connectivity and requests up-to date weather forecast data , otherwise is shows
     * a toast to the user to inform him about the connectivity issue.
     */
    private void onRefreshButtonClick()
    {
        if(AppUtils.getNetworkState(getContext()))
        {
            // we have internet
            Toast.makeText(getContext(), R.string.updating_data_string, Toast.LENGTH_SHORT).show();
            if(AppUtils.getSelectedPosition(getContext())[0].equals(NO_PLACE))
            {
                //we have not selected a place so use the current position
                String[] selectedPosition = AppUtils.getCurrentPosition(getContext());
                RemoteRepository.getsInstance().getForecastLatLon(selectedPosition[0],selectedPosition[1],getContext());
            }
            else
            {
                //use the selected position to fetch data
                String[] selectedPosition = AppUtils.getSelectedPosition(getContext());
                RemoteRepository.getsInstance().getForecastLatLon(selectedPosition[1],selectedPosition[2],getContext());
            }
        }
        else
        {
            Toast.makeText(getContext(),R.string.no_internet_connection,Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * This is a callback method that is triggered when the location option is changed
     * in the Settings Fragment . It reloads the UI properly according to the change
     * in settings the user made.
     */
    @Override
    public void onLocationSettingChange() {
        //todo add a Settings ID changed to have more cases to handle
        Log.d(DEBUG_TAG,"On settings change");
        //The position has changed so re fetch data for the new location
        String[] newLocationArray = AppUtils.getSelectedPosition(getContext());
        RemoteRepository.getsInstance().getForecastLatLon(newLocationArray[1],newLocationArray[2],getContext());
//        showWeatherForecastFragment();
    }

    /**
     * This is a callback method that is triggered when the unit settings option is changed
     * in the Settings Fragment . It reloads the UI properly according to the change
     * in settings the user made.
     */
    @Override
    public void onUserPreferredUnitChange() {
        //todo fix the repaint method
        //repaint the fragment
//        mFragmentManager.beginTransaction().replace(R.id.fragment_container, new FragmentWeatherForecast()).commit();
        mAdapter.notifyDataSetChanged();

    }

    /**
     * This method is triggered when the user clicks on a day in the 5-day
     * forecast weather data list. It shows more detailed forecast data about
     * that day
     * @param weatherForecastPOJO The WeatherForecastPOJO object that contains the weather data
     */
    @Override
    public void onDayDetailsClick(WeatherForecastPOJO weatherForecastPOJO) {
        //show the weather info for this day clicked
        Toast.makeText(getContext(),"To be implemented",Toast.LENGTH_SHORT).show(); //todo to be implemented
    }

    /**
     * This method is triggered when the user clicks on the "Details" button. It shows more detailed
     * forecast data about the current day.
     */
    private void onWeatherDetailsClicked(View view)
    {
        //handle the detailed button click
        if(getActivity().getSupportFragmentManager().findFragmentByTag(DETAILED_FORECAST_FRAGMENT_TAG) == null)
        {
            //show the weather detail info for today
            FragmentDetailedWeatherInfo mDetailWeatherForecast = new FragmentDetailedWeatherInfo();
            // create and save the first item of the forecast into a bundle to send it to the fragment
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.DETAILED_FORECAST_ARGUMENT_KEY,dataToSend);
            mDetailWeatherForecast.setArguments(bundle);
            //replace the current fragment with  the detailed weather forecast Fragment
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mDetailWeatherForecast,DETAILED_FORECAST_FRAGMENT_TAG)
                    .addToBackStack("")
                    .commit();
        }
    }

    /**
     * This method makes the toolbar buttons visible
     */
    private void showToolbarButtons()
    {
        getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.settings_btn).setVisibility(View.VISIBLE);
    }

    /**
     * Ui method for changing the loading indicator visibility.
     * @param visibility The int parameter depending the visibility parameter
     */
    private  void setLoadingIndicatorVisibility(int visibility) {
        //show/Hide the loading indicator
        mInflatedView.findViewById(R.id.loading_indicator).setVisibility(visibility);
    }

    @Override
    public void onStop() {
        super.onStop();
        //hide the refresh toolbar button
        getActivity().findViewById(R.id.toolbar_refresh_button).setVisibility(View.GONE);
    }

}

