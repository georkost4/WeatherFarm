package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 27/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class FragmentSettings extends Fragment
{
    private static final String DEBUG_TAG = "#FragmentSettings";
    private View mInflatedView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_settings,container,false);

        SupportPlaceAutocompleteFragment autocompleteFragment =  (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(DEBUG_TAG, "Place: " + place.getName());
                Log.i(DEBUG_TAG,"Latitude = " + place.getLatLng().latitude);
                Log.i(DEBUG_TAG,"Longitude = " + place.getLatLng().longitude);

                LatLng latLng = place.getLatLng();
                RemoteRepository.getsInstance().getCurrentForecast(String.valueOf(latLng.latitude),String.valueOf(latLng.longitude),getContext());

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(DEBUG_TAG, "An error occurred: " + status);
            }
        });

        return mInflatedView;
    }
}
