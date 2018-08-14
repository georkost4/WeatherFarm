package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.Constants;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 31/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This fragment is used to inform the user about some errors such as no cached weather data to show
 * , no current or selected place is configured to get the weather forecast data or there is no internet
 * connectivity to select a place to get weather data.
 */
public class FragmentErrorLayout extends Fragment
{
    private static final String DEBUG_TAG = "#FragmentErrorLayout";
    private View mInflatedView;
    private String mInformationalText;
    private boolean mShowSearchLayoutFlag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            Log.d(DEBUG_TAG,"Getting the values from the bundle");
            //Get the values from the bundle
            mInformationalText = getArguments().getString(Constants.FRAGMENT_ERROR_LAYOUT_TEXT_BUNDLE_KEY); // this value determines the text the user will see
            mShowSearchLayoutFlag = getArguments().getBoolean(Constants.FRAGMENT_ERROR_LAYOUT_BUNDLE_KEY,false); // this value determines if the search bar is visible
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.error_no_cached_data_layout,container,false);

        Log.i(DEBUG_TAG,"Text = " + mInformationalText);
        Log.i(DEBUG_TAG,"Flag = " + mShowSearchLayoutFlag);


        if(mShowSearchLayoutFlag)   //show the search bar if the flag is true
        {
            mInflatedView.findViewById(R.id.add_new_location_layout).setVisibility(View.VISIBLE);
        }
        //set the text to inform the user about his situation
        ((TextView)mInflatedView.findViewById(R.id.tv_error_text_value)).setText(mInformationalText);

        ((ActivityMain)getActivity()).getSupportActionBar().setTitle("");

        //if the place is selected handle it
        handlePlaceSelected();

        getActivity().findViewById(R.id.toolbar_refresh_button).setVisibility(View.VISIBLE); //make the toolbar icon visible
        getActivity().findViewById(R.id.toolbar_refresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                handleRefreshButtonClicked(view);
            }
        });
        return mInflatedView;
    }

    /**
     * This method handles the click on the toolbar
     * button Refresh. It checks for network connectivity
     * and if the device has no internet connectivity it
     * informs the user using a text message
     * @param view The View object that was clicked
     */
    private void handleRefreshButtonClicked(View view)
    {
        if(AppUtils.getNetworkState(getContext())) {
            // we have internet
            mInflatedView.findViewById(R.id.add_new_location_layout).setVisibility(View.VISIBLE);
//            //set the text to inform the user about his situation
//            ((TextView)mInflatedView.findViewById(R.id.tv_error_text_value)).setText(R.string.error_internet_but_not_selected_place_error); todo maybe remove this line
        }
        else
        {
            //we dont have internet connection show the user a proper text
            Toast.makeText(getContext(),getString(R.string.no_connection_text),Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method handles the Place selection from the search bar. It gets the Place
     * object and it saves the Name , Latitude , Longitude properties to the shared
     * preferences. It also requests weather data for that lat,lon coordinates.
     */
    private void handlePlaceSelected()
    {
        // launch the place autocomplete fragment to choose a location
        SupportPlaceAutocompleteFragment autocompleteFragment =  (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.error_layout_choose_place);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(DEBUG_TAG, "Place: " + place.getName());
                Log.i(DEBUG_TAG,"Latitude = " + place.getLatLng().latitude);
                Log.i(DEBUG_TAG,"Longitude = " + place.getLatLng().longitude);

                AppUtils.saveSelectedPosition(place,getContext()); // save the current pos to preferences

                //get forecast for the selected place
                RemoteRepository.getsInstance().getForecastLatLon(String.valueOf(place.getLatLng().latitude),String.valueOf(place.getLatLng().longitude),getContext());
                //show weather forecast fragment and make the toolbar buttons visible
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new FragmentWeatherForecast()).commit();
                getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.settings_btn).setVisibility(View.VISIBLE);

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(DEBUG_TAG, "An error occurred: " + status);
            }
        });
    }


}
