package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.Constants;

import static com.dsktp.sora.weatherfarm.utils.Constants.DETAILED_FORECAST_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.MAP_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.POLYGON_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.SETTINGS_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.WEATHER_FORECAST_FRAGMENT_TAG;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 20/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class ActivityMain extends AppCompatActivity implements FragmentSettings.SettingsChangeCallback
{
    private static final String DEBUG_TAG = "#ActivityMain";
    private FragmentMap mMapFragment;
    private FragmentWeatherForecast mWeatherFragment;
    private FragmentMyPolygons mPolygonFragment;
    private FragmentSettings mFragmentSettings;
    private FragmentManager mFragmentManager;
    private FragmentDetailedWeatherInfo mDetailWeatherForecast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
        findViewById(R.id.settings_btn).setVisibility(View.VISIBLE);

        // show the Weather forecast fragment
        if(mFragmentManager.findFragmentByTag(WEATHER_FORECAST_FRAGMENT_TAG) == null) //check to see if it already exists before re-creating
        {
            AppUtils.saveValues(this,System.currentTimeMillis());
            String[] latlngSet =  AppUtils.getSelectedPosition(this);
            RemoteRepository.getsInstance().getForecastLatLon(latlngSet[1],latlngSet[2],getBaseContext()); // todo remove from this place
            Log.i(DEBUG_TAG,"Creating weather fragment");
            mWeatherFragment = new FragmentWeatherForecast();
            mFragmentManager.beginTransaction().add(R.id.fragment_container,mWeatherFragment,WEATHER_FORECAST_FRAGMENT_TAG).commit();
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemID = item.getItemId();
        switch (itemID)
        {
            case android.R.id.home:
            {
                mFragmentManager.popBackStack();
                if(mFragmentManager.getBackStackEntryCount() == 1) // stack count == 1 means that the user is in the FIRST screen so HIDE the up nav button
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFragmentManager.getBackStackEntryCount() == 0)
        {
            findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE); //show the button if the user navigates to first screen
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void onPolygonsClick(View view)
    {
        if(mFragmentManager.findFragmentByTag(Constants.SETTINGS_FRAGMENT_TAG)!=null) // if the click came from settings dont add it to the back stack
        {
            Log.i(DEBUG_TAG,"Creating polygon fragment");
            mPolygonFragment = new FragmentMyPolygons();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mPolygonFragment,POLYGON_FRAGMENT_TAG).
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("").commit();
        }

    }

    public void onMapClick(View view)
    {
        if(mFragmentManager.findFragmentByTag("MapFragment") == null)
        {
            Log.i(DEBUG_TAG,"Creating map fragment");
            mMapFragment = new FragmentMap();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container,mMapFragment,MAP_FRAGMENT_TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack("")
                    .commit();
        }

    }
    public void onSettingsClicked(View view)
    {
        view.setVisibility(View.GONE); // todo maybe remove this
        if(mFragmentManager.findFragmentByTag(SETTINGS_FRAGMENT_TAG) == null) {
            mFragmentSettings = new FragmentSettings();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mFragmentSettings, SETTINGS_FRAGMENT_TAG).addToBackStack("").commit();
            mFragmentSettings.setCallback(this);
        }
    }

    public void onWeatherDetailsClicked(View view)
    {
        if(mFragmentManager.findFragmentByTag(DETAILED_FORECAST_FRAGMENT_TAG) == null)
        {
            Bundle dataToSend = new Bundle();
//            dataToSend.putParcelable("") implement this
            mDetailWeatherForecast = new FragmentDetailedWeatherInfo();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container,mDetailWeatherForecast,DETAILED_FORECAST_FRAGMENT_TAG)
                    .addToBackStack("")
                    .commit();
        }

    }

    @Override
    public void onSettingsChanged() {
        Log.d(DEBUG_TAG,"On settings change");
        // lets assume the position has changed so re fetch data for the new location
        String[] newLocationArray = AppUtils.getSelectedPosition(this);
        RemoteRepository.getsInstance().getForecastLatLon(newLocationArray[1],newLocationArray[2],this);
    }


}
