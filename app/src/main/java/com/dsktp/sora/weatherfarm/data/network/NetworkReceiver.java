package com.dsktp.sora.weatherfarm.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.dsktp.sora.weatherfarm.utils.AppUtils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 2/8/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class is a broadcast receiver for the action of CONNECTIVITY_CHANGE.
 * It checks for internet connectivity and handles the broadcast by saving the
 * value in the shared preferences.
 */
public class NetworkReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(checkInternet(context))
        {
            //we have internet
            //save the net state into preferences
            AppUtils.saveNetworkState(context,true);
        }
        else
        {
            //we dont have internet
            //save the net state into preferences
            AppUtils.saveNetworkState(context,false);
        }

    }

    /**
     * Helper method to check if the device has internet connectivity
     * @param context The context to access system service
     * @return True if the device has internet access , False for not having internet connection
     */
    private boolean checkInternet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
