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
import com.dsktp.sora.weatherfarm.data.network.NetworkReceiver;
import com.dsktp.sora.weatherfarm.data.service.MyJobDispatcher;
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

/**
 * This class is the sole activity on the project managing and switching
 * the fragments
 */
public class ActivityMain extends AppCompatActivity implements FragmentSettings.SettingsChangeCallback {
    private static final String DEBUG_TAG = "#ActivityMain";
    private FragmentManager mFragmentManager;
    private FusedLocationProviderClient mFusedLocationClient;
    private BroadcastReceiver mReceiver;



    @Override
    protected void onStart() {
        super.onStart();
        //initialize the broadcast receiver
        mReceiver = new NetworkReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //register the receiver with the action CONNECTIVITY_ACTION
        registerReceiver(mReceiver,intentFilter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getSupportFragmentManager();

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        //schedule periodic update
        MyJobDispatcher.setUpJob(this);
        //show the toolbar buttons
        showToolbarButtons();
        //check for cached data and show the error layout if no cached data is available
        checkForNetworkAndCachedData();
        //get the Location permission from the user
        requestLocationPermissionFromTheUser();

        //if we have cached data show the weather forecast fragment to the user
        if(!AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE)||!AppUtils.getCurrentPosition(this)[0].equals(NO_LATITUDE)) //check for cached data
        {
            //we have cached data so show them to the user
            showWeatherForecastFragment();
        }

    }



    /**
     * This method makes the fragment transaction and shows the user
     * the Weather Forecast fragment
     */
    private void showWeatherForecastFragment() {
        // show the Weather forecast fragment
        if (mFragmentManager.findFragmentByTag(WEATHER_FORECAST_FRAGMENT_TAG) == null) //check to see if it already exists before re-creating
        {
            Log.i(DEBUG_TAG, "Creating weather fragment");
            FragmentWeatherForecast mWeatherFragment = new FragmentWeatherForecast();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mWeatherFragment, WEATHER_FORECAST_FRAGMENT_TAG).commit();
        }

    }

    /**
     * This method check to see if the device is running android os >= 6.0 or API 23. If it does
     * then it check's to see if the Location permission is granted and if it is it fetches weather
     * forecast data for that location. If the permission is not granted it asks the user to grant it
     * using a pop up dialog. In case the user denies the location permission for the current location
     * of the device , then the app shows an error layout informing the user about the current state of
     * the app.
     */
    private void requestLocationPermissionFromTheUser()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) //if the platform has android less than an android 6.0 version then dont handle runtime permissions
        {
            //check to see if we have the permissions
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(DEBUG_TAG, "We have the permissions");

                //We already have the permissions
                if(AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE))
                {
                    Log.d(DEBUG_TAG,"Getting the current position info");
                    getCurrentPosition();
                }

            }
            else
            {
                // Location permission not granded
                Log.d(DEBUG_TAG, "We dont have hte permissions");
                //provide additional context to why you will need to access the devices location
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(getBaseContext(), R.string.permission_needed_string, Toast.LENGTH_LONG).show();
                }
                //this is the first time we open the app so decide which error text you will show to the user
                FragmentErrorLayout fragmentErrorLayout = new FragmentErrorLayout();
                Bundle bundle = new Bundle();
                //here is the user case he have
                //We have no permissions to get the current position data info
                //We also have no previously selected place to get info for that either
                //if the user has internet show him a layout to select a place
                //if the user has no internet show him a layout indicating that he should get online
                if(AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE)) // if the selected position is == NO_PLACE means we dont have a previously selected place
                {
                    if(AppUtils.getNetworkState(this)) // if we have internet
                    {
                        //show him the layout to select the place
                        bundle.putString(FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.select_place_from_search_bar));
                        bundle.putBoolean(FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,true);// show the search bar

                    }
                    else
                    {
                        //show him error indicating that he is offline and he should get online first
                        bundle.putBoolean(FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,false); //dont show the search bar cause we dont have internet connection
                        bundle.putString(FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.connect_first_and_try));
                    }
                }
                fragmentErrorLayout.setArguments(bundle);
                mFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentErrorLayout).commit(); //show the error layout
                hideToolbarButtons();

                //ask for permissions again
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }
        }
    }

    /**
     * This method makes a check to see if the app is run for the first time meaning that
     * the selected weather position , current position is null . It also checks for internet connection
     * and shows the error layout fragment with the appropriate text message to inform the user about
     * the current app state
     */
    private void checkForNetworkAndCachedData() {
        //we dont have internet
        //we dont have current position
        //we dont have selected position
        if(!AppUtils.getNetworkState(this)&&AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE)&&AppUtils.getCurrentPosition(this)[0].equals(NO_LATITUDE))
        {
            Log.i(DEBUG_TAG,"Choosing the first layout");
            //hide the toolbar buttons
            hideToolbarButtons();
            // We have no internet , no cached weather info either for the current or the selected location
            // show the appropriate layout
            Bundle bundle = new Bundle();
            bundle.putString(FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.no_cache_no_internet_please_select_location));
            bundle.putBoolean(FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,false);
            FragmentErrorLayout fragmentErrorLayout = new FragmentErrorLayout();
            fragmentErrorLayout.setArguments(bundle);
            mFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentErrorLayout,FRAGMENT_ERROR_LAYOUT_TAG).commit();

        }
        //we have internet
        //we dont have selected position
        //we dont have current position
        else if(AppUtils.getNetworkState(this)&&AppUtils.getSelectedPosition(this)[0].equals(NO_PLACE) && AppUtils.getCurrentPosition(this)[0].equals(NO_LATITUDE))
        {
            Log.i(DEBUG_TAG,"Choosing the second layout");
            //hide the toolbar buttons
            hideToolbarButtons();
            // We have  internet , no cached weather info either for the current or the selected location
            // show the appropriate layout
            Bundle bundle = new Bundle();
            bundle.putString(FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.select_a_location_from_search_bar_text));
            bundle.putBoolean(FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,true);
            FragmentErrorLayout fragmentErrorLayout = new FragmentErrorLayout();
            fragmentErrorLayout.setArguments(bundle);
            mFragmentManager.beginTransaction().replace(R.id.fragment_container,fragmentErrorLayout,FRAGMENT_ERROR_LAYOUT_TAG).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch (itemID) {
            case android.R.id.home: {
                mFragmentManager.popBackStack();
                if (mFragmentManager.getBackStackEntryCount() == 1) // stack count == 1 means that the user is in the FIRST screen so HIDE the up nav button
                {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false); //hide the back button on the toolbar
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mFragmentManager.getBackStackEntryCount() == 0)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false); //hide the back button from toolbar
        }
    }

    /**
     * This method handles the click on the toolbar button "POLYGONS".It takes the user
     * to the Polygon Fragment screen.
     * @param view The View(Polygons Button) object that was clicked
     */
    public void onPolygonsClick(View view) {
        if (mFragmentManager.findFragmentByTag(Constants.POLYGON_FRAGMENT_TAG) == null) // if the click came from settings dont add it to the back stack
        {
            Log.i(DEBUG_TAG, "Creating polygon fragment");
            FragmentMyPolygons mPolygonFragment = new FragmentMyPolygons();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mPolygonFragment, POLYGON_FRAGMENT_TAG).
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("").commit();
        }

    }

    /**
     * This method handles the click on the  floating action button " + ".It takes the user
     * to the Map Fragment screen.
     * @param view The View(Map Floating Action Button) object that was clicked
     */
    public void onMapClick(View view) {
        if(AppUtils.getNetworkState(this)) {
            if (mFragmentManager.findFragmentByTag(Constants.MAP_FRAGMENT_TAG) == null) {
                Log.i(DEBUG_TAG, "Creating map fragment");
                FragmentMap mMapFragment = new FragmentMap();
                mFragmentManager.beginTransaction().replace(R.id.fragment_container, mMapFragment, MAP_FRAGMENT_TAG)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack("")
                        .commit();
            }
        }
        else
        {
            Toast.makeText(this, R.string.no_internet_connection,Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method handles the click on the toolbar button "SETTINGS".It takes the user
     * to the Settings Fragment screen.
     * @param view The View(Settings Button) object that was clicked
     */
    public void onSettingsClicked(View view) {
        if (mFragmentManager.findFragmentByTag(SETTINGS_FRAGMENT_TAG) == null)
        {
            FragmentSettings mFragmentSettings = new FragmentSettings();
            mFragmentManager.beginTransaction().replace(R.id.fragment_container, mFragmentSettings, SETTINGS_FRAGMENT_TAG).addToBackStack("").commit();
            mFragmentSettings.setCallback(this); // set the callback to this activity/fragment so we can handle the onSettingsChanged method
        }
    }

    /**
     * This method uses a Fused Location client to get the current position of the device.
     * If successful it requests weather data for that position , otherwise it shows the
     * error layout to the user with the right text to inform him about the state of the app.
     */
    private void getCurrentPosition() {
        //get the current positions
        //and request weather data for that location
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
                                //show the weather forecast fragment
                                showWeatherForecastFragment();
                            }
                            Log.d(DEBUG_TAG, "Location = " + location.getLatitude() + "  ,  " + location.getLongitude());

                        } else
                        {
                            Log.e(DEBUG_TAG, "Location == null");
                            //open the settings fragment so the user chooses the location
                            FragmentErrorLayout errorLayout = new FragmentErrorLayout();
                            Bundle dataToSend = new Bundle();
                            if(AppUtils.getNetworkState(getBaseContext())) {
                                //we have internet
                                dataToSend.putBoolean(Constants.FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY, true); // show the search bar
                                dataToSend.putString(Constants.FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.select_a_location_from_search_bar_text));
                            }
                            else
                            {
                                //we have no internet
                                dataToSend.putString(Constants.FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.no_cache_no_internet_please_select_location));
                                dataToSend.putBoolean(Constants.FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,false); //hide the toolbar
                            }
                            errorLayout.setArguments(dataToSend);
                            mFragmentManager.beginTransaction().replace(R.id.fragment_container,errorLayout).commit();
                            hideToolbarButtons();
                        }
                    }
                });
    }

    /**
     * This is a callback method that is triggered when a settings option is changed
     * in the Settings Fragment . It reloads the UI properly according to the change
     * in settings the user made.
     */
    @Override
    public void onSettingsChanged() {
        //todo add a Settings ID changed to have more cases to handle
        Log.d(DEBUG_TAG,"On settings change");
        //The position has changed so re fetch data for the new location
        String[] newLocationArray = AppUtils.getSelectedPosition(this);
        RemoteRepository.getsInstance().getForecastLatLon(newLocationArray[1],newLocationArray[2],this);
        showWeatherForecastFragment();
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

                if(AppUtils.getNetworkState(this))
                {
                    //we have  internet so hide the search bar until reconnect
                    bundle.putString(Constants.FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.select_a_location_from_search_bar_text));
                    bundle.putBoolean(FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,true);
                }
                else
                {
                    //we have no internet so hide the search bar until reconnect
                    bundle.putString(Constants.FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY,getString(R.string.no_cache_no_internet_please_select_location));
                    bundle.putBoolean(FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,false);
                }
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

    /**
     * This method hides the toolbar button from the user
     */
    private void hideToolbarButtons() {
        findViewById(R.id.btn_my_polygons).setVisibility(View.GONE);
        findViewById(R.id.settings_btn).setVisibility(View.GONE);
    }

    /**
     * This method makes the toolbar buttons visible to the user
     */
    private void showToolbarButtons() {
        //show the toolbar buttons
        findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
        findViewById(R.id.settings_btn).setVisibility(View.VISIBLE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        //unregister the receiver
        if(mReceiver!=null) unregisterReceiver(mReceiver);
    }


}
