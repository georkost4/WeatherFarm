package com.dsktp.sora.weatherfarm.data.model.Forecast;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class Wind implements Parcelable {
    private double speed;
    private double deg;

    public Wind(double speed, double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public Wind() {
    }

    public double getSpeed() {
        return speed;
    }

    private void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    private void setDeg(double deg) {
        this.deg = deg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(speed);
        dest.writeDouble(deg);
    }

    public static final Parcelable.Creator<Wind> CREATOR = new Parcelable.Creator<Wind>() {
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        public Wind[] newArray(int size)
        {
            return new Wind[size];
        }
    };

    private Wind(Parcel in)
    {
        setSpeed(in.readDouble());
        setDeg(in.readDouble());
    }
}
