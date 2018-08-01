package com.dsktp.sora.weatherfarm.data.model.Forecast;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class Rain implements Parcelable {
    @SerializedName("3h")
    private double threeHourRainVolume;

    public Rain(double threeHourRainVolume) {
        this.threeHourRainVolume = threeHourRainVolume;
    }

    public Rain() {
    }

    public double getThreeHourRainVolume() {
        return threeHourRainVolume;
    }

    public void setThreeHourRainVolume(double threeHourRainVolume) {
        this.threeHourRainVolume = threeHourRainVolume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
       dest.writeDouble(threeHourRainVolume);
    }

    public static final Parcelable.Creator<Rain> CREATOR = new Parcelable.Creator<Rain>()
    {
        public Rain createFromParcel(Parcel in) {
            return new Rain(in);
        }

        public Rain[] newArray(int size) {
            return new Rain[size];
        }
    };


    private Rain(Parcel in) {
        setThreeHourRainVolume(in.readDouble());
    }
}
