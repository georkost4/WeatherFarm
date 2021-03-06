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
 * This class represents a Weather object that is a part of the API response
 * when you request weather forecast data. It also implements the Parcelable
 * interface so we can write the object into Room Database
 */
public class Weather implements Parcelable
{
    private int id;
    private String main;
    private String description;
    private String icon;

    public Weather(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public Weather() {
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    private void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    private void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(description);
        parcel.writeString(icon);
        parcel.writeInt(id);
        parcel.writeString(main);

    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>()
    {
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };


    private Weather(Parcel in) {
        setDescription(in.readString());
        setIcon(in.readString());
        setId(in.readInt());
        setMain(in.readString());
    }
}
