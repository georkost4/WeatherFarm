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
public class Snow implements Parcelable {
    @SerializedName("3h")
    private double threeHourSnowVolume;

    public Snow(double threeHourSnowVolume) {
        this.threeHourSnowVolume = threeHourSnowVolume;
    }

    public Snow() {
    }

    public double getThreeHourSnowVolume() {
        return threeHourSnowVolume;
    }

    private void setThreeHourSnowVolume(double threeHourSnowVolume) {
        this.threeHourSnowVolume = threeHourSnowVolume;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
            parcel.writeDouble(threeHourSnowVolume);
    }

    public static final Parcelable.Creator<Snow> CREATOR = new Parcelable.Creator<Snow>()
    {
        public Snow createFromParcel(Parcel in) {
            return new Snow(in);
        }

        public Snow[] newArray(int size) {
            return new Snow[size];
        }
    };


    private Snow(Parcel in) {
       setThreeHourSnowVolume(in.readDouble());
    }
}
