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

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.utils.AppUtils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 20/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class ActivityMain extends AppCompatActivity implements RemoteRepository.onSucces
{
    private static final String DEBUG_TAG = "#ActivityMain";
    private Fragment mMapFragment , mPolygonFragment,mWeatherFragment;
    private FragmentManager mFragmentManager;
    private RemoteRepository mRepo;
    private Button polygonButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRepo = RemoteRepository.getsInstance();
        mRepo.setmCallback(this);
        mFragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);


        // show the Weather forecast fragment
        if(mFragmentManager.findFragmentByTag("weatherFragment") == null) //check to see if it already exists before re-creating
        {
            RemoteRepository.getsInstance().getForecastLatLon("41","26",getBaseContext()); // todo remove from this place
            AppUtils appUtils = new AppUtils(PreferenceManager.getDefaultSharedPreferences(this));
            appUtils.saveValues(0);
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
                FragmentSettings settings = new FragmentSettings();
                mFragmentManager.beginTransaction().replace(R.id.fragment_container,settings,"settings").addToBackStack("").commit();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(mFragmentManager.getBackStackEntryCount() == 0) findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE); //show the button if the user navigates to first screen
    }

    @Override
    public void updateUI()
    {
        if(mFragmentManager.findFragmentByTag("weatherFragment") == null)
        {
            mWeatherFragment = new FragmentWeatherForecast();

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,mWeatherFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
        findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
    }



    public void onPolygonsClick(View view)
    {
        if(mFragmentManager.findFragmentByTag("PolygonFragment") == null )
        {
            Log.i(DEBUG_TAG,"Creating polygon fragment");
            findViewById(R.id.btn_my_polygons).setVisibility(View.GONE); // hide the polygon button from the toolbar
            mPolygonFragment = new FragmentMyPolygons();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mPolygonFragment,"PolygonFragment").setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("").commit(); //todo move this line out of the if statement
        }

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
}
