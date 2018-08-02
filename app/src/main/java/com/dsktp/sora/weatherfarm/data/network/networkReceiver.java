package com.dsktp.sora.weatherfarm.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.utils.AppUtils;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 2/8/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class networkReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent)
    {
        if(checkInternet(context))
        {
            AppUtils.saveNetworkState(context,true);
        }
        else
        {
            AppUtils.saveNetworkState(context,false);
        }

    }

    boolean checkInternet(Context context) {
        {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }
}
