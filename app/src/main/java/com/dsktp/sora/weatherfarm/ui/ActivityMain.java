package com.dsktp.sora.weatherfarm.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.data.network.networkReceiver;
import com.dsktp.sora.weatherfarm.data.repository.AppExecutors;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import static com.dsktp.sora.weatherfarm.utils.Constants.FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.FRAGMENT_ERROR_LAYOUT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY;
import static com.dsktp.sora.weatherfarm.utils.Constants.MAP_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.NO_LATITUDE;
import static com.dsktp.sora.weatherfarm.utils.Constants.NO_PLACE;
import static com.dsktp.sora.weatherfarm.utils.Constants.POLYGON_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.SETTINGS_FRAGMENT_TAG;
import static com.dsktp.sora.weatherfarm.utils.Constants.WEATHER_FORECAST_FRAGMENT_TAG;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 20/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class ActivityMain extends AppCompatActivity implements FragmentSettings.SettingsChangeCallback {
    private static final String DEBUG_TAG = "#ActivityMain";
    private FragmentMap mMapFragment;
    private FragmentWeatherForecast mWeatherFragment;
    private FragmentMyPolygons mPolygonFragment;
    private FragmentSettings mFragmentSettings;
    private FragmentManager mFragmentManager;
    private FragmentDetailedWeatherInfo mDetailWeatherForecast;
    private FusedLocationProviderClient mFusedLocationClient;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onStop() {
        super.onStop();
        if(mReceiver!=null) unregisterReceiver(mReceiver);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mReceiver = new networkReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        if(mReceiver!=null) registerReceiver(mReceiver,intentFilter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);


        showToolbarButtons();


        requestLocationPermissionFromTheUser();


        showWeatherForecastFragment();


    }

    private void showToolbarButtons() {
        //show the toolbar buttons
        findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
        findViewById(R.id.settings_btn).setVisibility(View.VISIBLE);
    }

    private void showWeatherForecastFragment() {
        // show the Weather forecast fragment
        if (mFragmentManager.findFragmentByTag(WEATHER_FORECAST_FRAGMENT_TAG) == null) //check to see if it already exists before re-creating
        {
            //if the user has selected a place get info for that place
            if(!AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE))
            {
                //check for internet connection
                if(AppUtils.getNetworkState(this))
                {
                    //we have internet so update
                    AppUtils.saveLastUpdatedValue(this, System.currentTimeMillis());
                    String[] latlngSet = AppUtils.getSelectedPosition(this);

                    RemoteRepository.getsInstance().getForecastLatLon(latlngSet[1], latlngSet[2], getBaseContext());
                }
                else
                {
                    //we dont have internet
                    Toast.makeText(this, R.string.no_connection_text,Toast.LENGTH_LONG);
                    checkForNetworkAndCachedData();
                }

            }
            Log.i(DEBUG_TAG, "Creating weather fragment");
            mWeatherFragment = new FragmentWeatherForecast();
            mFragmentManager.beginTransaction().add(R.id.fragment_container, mWeatherFragment, WEATHER_FORECAST_FRAGMENT_TAG).commit();
        }
    }

    public void requestLocationPermissionFromTheUser()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) //if the platform has android less than an android 6.0 version then dont handle runtime permissions
        {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(DEBUG_TAG, "We have the permissions");

                //We already have the permissions
                if(AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE))
                {
                    Log.d(DEBUG_TAG,"Getting the current position info");
                    getCurrentPosition();
                }

            } else
                {
                // Location permission not granded
                Log.d(DEBUG_TAG, "We dont have hte permissions");
                //provide additional context to why you will need to access the devices location
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(getBaseContext(), "Location permission is needed to show you weather data for your current position", Toast.LENGTH_LONG).show();
                }


                //ask for permissions again
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }
        }
    }

    private boolean checkForNetworkAndCachedData() {
        if(!AppUtils.getNetworkState(this)&&AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE)&&AppUtils.getCurrentPosition(this)[0].equals(NO_LATITUDE))
        {
            // We have no internet , no cached weather info either for the current or the selected location
            // show the appropriate layout
            mFragmentManager.beginTransaction().replace(R.id.fragment_container,new FragmentErrorLayout(),FRAGMENT_ERROR_LAYOUT_TAG).commit();
            //hide the toolbar buttons
            hideToolbarButtons();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case android.R.id.home: {
                mFragmentManager.popBackStack();
                if (mFragmentManager.getBackStackEntryCount() == 1) // stack count == 1 means that the user is in the FIRST screen so HIDE the up nav button
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
        if (mFragmentManager.getBackStackEntryCount() == 0) {
            findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE); //show the button if the user navigates to first screen
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    public void onPolygonsClick(View view) {
        if (mFragmentManager.findFragmentByTag(Constants.POLYGON_FRAGMENT_TAG) == null) // if the click came from settings dont add it to the back stack
        {
            Log.i(DEBUG_TAG, "Creating polygon fragment");
            mPolygonFragment = new FragmentMyPolygons();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mPolygonFragment, POLYGON_FRAGMENT_TAG).
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("").commit();
        }

    }

    public void onMapClick(View view) {
        if (mFragmentManager.findFragmentByTag(Constants.MAP_FRAGMENT_TAG) == null) {
            Log.i(DEBUG_TAG, "Creating map fragment");
            mMapFragment = new FragmentMap();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mMapFragment, MAP_FRAGMENT_TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack("")
                    .commit();
        }

    }

    public void onSettingsClicked(View view) {
        view.setVisibility(View.GONE); // todo maybe remove this
        if (mFragmentManager.findFragmentByTag(SETTINGS_FRAGMENT_TAG) == null) {
            mFragmentSettings = new FragmentSettings();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mFragmentSettings, SETTINGS_FRAGMENT_TAG).addToBackStack("").commit();
            mFragmentSettings.setCallback(this);


        }
    }

    public void getCurrentPosition() {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            AppUtils.saveCurrentPosition(getBaseContext(), location);
                            if(AppUtils.getNetworkState(getBaseContext())) // if we have connectivity
                            {
                                //fetch data for this location
                                RemoteRepository.getsInstance().getForecastLatLon(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), getBaseContext());
                            }
                            Log.d(DEBUG_TAG, "Location = " + location.getLatitude() + "  ,  " + location.getLongitude());

                        } else
                        {
                            Log.e(DEBUG_TAG, "Location == null");
                            //open the settings fragment so the user chooses the location

                        }
                    }
                });
    }

    @Override
    public void onSettingsChanged() {
        Log.d(DEBUG_TAG,"On settings change");
        // lets assume the position has changed so re fetch data for the new location
        String[] newLocationArray = AppUtils.getSelectedPosition(this);
        RemoteRepository.getsInstance().getForecastLatLon(newLocationArray[1],newLocationArray[2],this);

        AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 0)
        {
            //permission result for coarse location
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // fine location permission has been granted
                Log.d(DEBUG_TAG,"Fine location has been granted");
                //we have the permission of the user so get the current position info
                if(AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE)) //if the user has not been selected yet get the current position weather forecast
                {
                    Log.d(DEBUG_TAG,"Getting the position from onRequestPermissionResult");
                    getCurrentPosition();
                }
            }
            else
            {
                Toast.makeText(this,"Permission not granted",Toast.LENGTH_LONG).show();
                //open the settings menu to choose location
                FragmentErrorLayout fragmentErrorLayout = new FragmentErrorLayout();
                Bundle bundle = new Bundle();
                bundle.putString(Constants.FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.select_a_location_from_search_bar_text));
                bundle.putBoolean(FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,true);
                fragmentErrorLayout.setArguments(bundle);
                mFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentErrorLayout).commit();
                hideToolbarButtons();
            }
        }
        else
        {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void hideToolbarButtons() {
        findViewById(R.id.btn_my_polygons).setVisibility(View.GONE);
        findViewById(R.id.settings_btn).setVisibility(View.GONE);
    }
}
