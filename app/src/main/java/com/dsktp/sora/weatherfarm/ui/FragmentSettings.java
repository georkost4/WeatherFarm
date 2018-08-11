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
public class FragmentSettings extends Fragment implements PlaceSelectionListener
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

        //set the toolbar title and enable the up button
        ((ActivityMain)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActivityMain)getActivity()).getSupportActionBar().setTitle(R.string.settings_toolbar_title);

        //setup google search place bar
        SupportPlaceAutocompleteFragment autocompleteFragment =  (SupportPlaceAutocompleteFragment) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        return mInflatedView;
    }


    /**
     * This method is called when a user selects a place from
     * the google search bar.
     * @param place The Place object of the place the user chose
     */
    @Override
    public void onPlaceSelected(Place place)
    {
        Log.i(DEBUG_TAG, "Place: " + place.getName());
        Log.i(DEBUG_TAG,"Latitude = " + place.getLatLng().latitude);
        Log.i(DEBUG_TAG,"Longitude = " + place.getLatLng().longitude);

        AppUtils.saveSelectedPosition(place,getContext()); //save the selected position to preferences
        mCallback.onSettingsChanged(); //trigger the callback


    }

    /**
     * This method is called when a user selects a place from
     * the google search bar and an error is occurred
     * @param status the Status object containing info about the error that occurred
     */
    @Override
    public void onError(Status status)
    {
        // TODO: Handle the error.
        Log.i(DEBUG_TAG, "An error occurred: " + status);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        //hide the toolbar buttons
        getActivity().findViewById(R.id.settings_btn).setVisibility(View.GONE);
        getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.GONE);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        //show the toolbar buttons
        getActivity().findViewById(R.id.settings_btn).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
    }


}
