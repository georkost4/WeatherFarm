package com.dsktp.sora.weatherfarm.data.service;

import android.content.Context;
import android.util.Log;

import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import static com.dsktp.sora.weatherfarm.utils.Constants.NO_LATITUDE;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 7/8/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class MyJobDispatcher extends JobService {

    private String DEBUG_TAG = "#MyJobDispatcher";

    @Override
    public boolean onStartJob(JobParameters job)
    {
        Log.i(DEBUG_TAG,"Updating the data");
        if(AppUtils.getCurrentPosition(getBaseContext())[0].equals(NO_LATITUDE)) {
            String[] selectedPlace = AppUtils.getSelectedPosition(getBaseContext());
            RemoteRepository.getsInstance().getForecastLatLon(selectedPlace[1],selectedPlace[2],getBaseContext());
        }
        else
        {
            String[] currentPlace = AppUtils.getCurrentPosition(getBaseContext());
            RemoteRepository.getsInstance().getForecastLatLon(currentPlace[0],currentPlace[1],getBaseContext());
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job)
    {
        Log.i(DEBUG_TAG,"OnStopJob");
        return false;
    }

    public static void setUpJob(Context context)
    {
        // Create a new dispatcher using the Google Play driver.
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));

        com.firebase.jobdispatcher.Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(MyJobDispatcher.class)
                // uniquely identifies the job
                .setTag("my-update-weather-data")
                // periodic job
                .setRecurring(true)
                // persist forever
                .setLifetime(Lifetime.FOREVER)
                // trigger every 3 hours
                .setTrigger(Trigger.executionWindow(60*60*3, (60*60*3)+30))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on any network
                        Constraint.ON_ANY_NETWORK
                )
                .build();

        dispatcher.mustSchedule(myJob);
    }
}
