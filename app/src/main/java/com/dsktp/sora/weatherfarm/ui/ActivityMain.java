package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.adapters.WeatherAdapter;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.utils.AppUtils;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);



        // show the Weather forecast fragment
        if(mFragmentManager.findFragmentByTag("weatherFragment") == null) //check to see if it already exists before re-creating
        {
            AppUtils.saveValues(this,System.currentTimeMillis());
            String[] latlngSet =  AppUtils.getSelectedPosition(this);
            RemoteRepository.getsInstance().getForecastLatLon(latlngSet[1],latlngSet[2],getBaseContext()); // todo remove from this place
            Log.i(DEBUG_TAG,"Creating weather fragment");
            mWeatherFragment = new FragmentWeatherForecast();
            mFragmentManager.beginTransaction().add(R.id.fragment_container,mWeatherFragment,"weatherFragment").commit();
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemID = item.getItemId();
        switch (itemID)
        {
            case R.id.settings_btn:
            {
                mFragmentSettings = new FragmentSettings();
                mFragmentManager.beginTransaction().replace(R.id.fragment_container,mFragmentSettings,"settings").addToBackStack("").commit();
                mFragmentSettings.setCallback(this);
                break;
            }
            case android.R.id.home:
            {
                mFragmentManager.popBackStack();
                if(mFragmentManager.getBackStackEntryCount() == 1)
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
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
        if(mFragmentManager.findFragmentByTag("PolygonFragment") == null )
        {
            Log.i(DEBUG_TAG,"Creating polygon fragment");
            mPolygonFragment = new FragmentMyPolygons();
        }
        findViewById(R.id.btn_my_polygons).setVisibility(View.GONE); // hide the polygon button from the toolbar

        mFragmentManager.beginTransaction().replace(R.id.fragment_container, mPolygonFragment,"PolygonFragment").
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("")
                .commit(); //todo move this line out of the if statement

    }

    public void onMapClick(View view)
    {
        if(mFragmentManager.findFragmentByTag("MapFragment") == null)
        {
            Log.i(DEBUG_TAG,"Creating map fragment");
            mMapFragment = new FragmentMap();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container,mMapFragment,"MapFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("").commit();
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
