package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;


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
    private SettingsChangeCallback mCallback;

    public void setCallback(SettingsChangeCallback mCallback) {
        this.mCallback = mCallback;
    }

    public interface SettingsChangeCallback
    {
        void onSettingsChanged();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_settings,container,false);

        SupportPlaceAutocompleteFragment autocompleteFragment =  (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        ((ActivityMain)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActivityMain)getActivity()).getSupportActionBar().setTitle("Settings");


        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(DEBUG_TAG, "Place: " + place.getName());
                Log.i(DEBUG_TAG,"Latitude = " + place.getLatLng().latitude);
                Log.i(DEBUG_TAG,"Longitude = " + place.getLatLng().longitude);

                AppUtils.saveSelectedPosition(place,getContext());
                mCallback.onSettingsChanged();

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
