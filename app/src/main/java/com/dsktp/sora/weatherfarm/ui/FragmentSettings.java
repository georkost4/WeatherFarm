package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.Constants;
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

/**
 * This fragment class represents the settings screen of the app.
 */
public class FragmentSettings extends Fragment implements PlaceSelectionListener
{
    private static final String DEBUG_TAG = "#FragmentSettings";
    private View mInflatedView;
    private SettingsChangeCallback mCallback;
    private Switch mUnitsSwitch;
    private TextView mUnitsLabel;


    public void setCallback(SettingsChangeCallback mCallback) {  this.mCallback = mCallback;  }

    /**
     * This interface is used to get the callback from the MainActivity to handle the
     * update of the UI with the new values depending on what has changed.
     */
    public interface SettingsChangeCallback
    {
        void onUserPreferredUnitChange();
        void onLocationSettingChange();
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


        //set up the switch logic
        setUpSwitch();


        return mInflatedView;
    }

    /**
     * This method takes care of setting up the switch the label
     * and the on Switch change listener.
     */
    private void setUpSwitch() {
        //get reference to the switch
        mUnitsSwitch = mInflatedView.findViewById(R.id.switch_units);
        mUnitsLabel = mInflatedView.findViewById(R.id.tv_units_label);
        //set the state of the switch from the user preference
        String unitsPrefered = AppUtils.getUnitUserPreference(getContext());
        if(unitsPrefered.equals(Constants.PREFERENCES_UNITS_IMPERIAL_VALUE))
        {
            //user has imperial units
            mUnitsSwitch.setChecked(false); // switch off the switch
            mUnitsLabel.setText(R.string.settings_imperial_units_string); //set the label to correct text

        }
        else
        {
            //user has chosen metric units
            mUnitsSwitch.setChecked(true); //switch on the switch
            mUnitsLabel.setText(R.string.settings_metric_units_string); // set the correct label
        }
        //set on check change listeners
        mUnitsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                oncSwitchCheckChange(buttonView,isChecked);
            }
        });
    }


    /**
     * This method is triggered when the Metric Unit switch has changed
     * state.
     * @param switchView The switch
     * @param isChecked The boolean variable determining the check state
     */
    private void oncSwitchCheckChange(CompoundButton switchView, boolean isChecked)
    {
        //The  switch is checked
        if(isChecked)
        {
            //set the label text to Metric
            ((TextView)mInflatedView.findViewById(R.id.tv_units_label)).setText(R.string.settings_metric_units_string);
            //set the user preferred unit to metric
            AppUtils.saveUnitUserPreference(getContext(), Constants.PREFERENCES_UNITS_METRIC_VALUE);
            mCallback.onUserPreferredUnitChange();
        }
        else
        {
            //The  switch is disabled
            //set the label text to Imperial
            ((TextView)mInflatedView.findViewById(R.id.tv_units_label)).setText(R.string.settings_imperial_units_string);
            //set the user preferred unit to metric
            AppUtils.saveUnitUserPreference(getContext(), Constants.PREFERENCES_UNITS_IMPERIAL_VALUE);
            mCallback.onUserPreferredUnitChange();
        }
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
        mCallback.onLocationSettingChange(); //trigger the callback


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
