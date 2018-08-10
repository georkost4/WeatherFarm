package com.dsktp.sora.weatherfarm.data.model.Forecast;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 21/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class represents a Cloud object that is a part of the API response
 * when you request weather forecast data. It also implements the Parcelable
 * interface so we can write the object into Room Database
 */
public class Cloud implements Parcelable {
    private int all;

    public Cloud() {
    }

    public Cloud(int all) {
        this.all = all;
    }

    public int getAll() {
        return all;
    }

    private void setAll(int all) {
        this.all = all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
       dest.writeInt(all);
    }

    public static final Parcelable.Creator<Cloud> CREATOR = new Parcelable.Creator<Cloud>()
    {
        public Cloud createFromParcel(Parcel in)
        {
            return new Cloud(in);
        }

        public Cloud[] newArray(int size)
        {
            return new Cloud[size];
        }
    };


    private Cloud(Parcel in)
    {
        setAll(in.readInt());
    }
}
