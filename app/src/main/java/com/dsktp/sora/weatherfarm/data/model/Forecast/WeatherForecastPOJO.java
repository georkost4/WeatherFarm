package com.dsktp.sora.weatherfarm.data.model.Forecast;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 22/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */


/**
 * This class represents a WeatherForecastPOJO object from the  API response
 * when you request weather forecast data. It also implements the Parcelable
 * interface so we can write the object into Room Database
 */
@Entity(tableName = "weatherForecastTable")
public class WeatherForecastPOJO implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    private int _id;
    private int dt;
    private ArrayList<Weather> weather;
    private Main main;
    private Wind wind;
    private Rain rain;
    private Cloud clouds;
    private Snow snow;

    public WeatherForecastPOJO(int dt, ArrayList<Weather> weather, Main main, Wind wind, Rain rain, Cloud clouds, Snow snow) {
        this.dt = dt;
        this.weather = weather;
        this.main = main;
        this.wind = wind;
        this.rain = rain;
        this.clouds = clouds;
        this.snow = snow;
    }

    @Ignore
    public WeatherForecastPOJO(){}

    public Snow getSnow() {
        return snow;
    }

    public int getDt() {
        return dt;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public Main getMain() {
        return main;
    }

    public Wind getWind() {
        return wind;
    }

    public Rain getRain() {
        return rain;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public void setWeather(ArrayList<Weather> weather) {
        this.weather = weather;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public void setClouds(Cloud clouds) {
        this.clouds = clouds;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(dt);
        dest.writeList(weather);
        dest.writeParcelable(main,flags);
        dest.writeParcelable(wind,flags);
        dest.writeParcelable(rain,flags);
        dest.writeParcelable(clouds,flags);
        dest.writeParcelable(snow,flags);
    }

    public static final Parcelable.Creator<WeatherForecastPOJO> CREATOR = new Parcelable.Creator<WeatherForecastPOJO>()
    {
        public WeatherForecastPOJO createFromParcel(Parcel in) {
            return new WeatherForecastPOJO(in);
        }

        public WeatherForecastPOJO[] newArray(int size) {
            return new WeatherForecastPOJO[size];
        }
    };


    private WeatherForecastPOJO(Parcel in) {
        setDt(in.readInt());
        setWeather(in.readArrayList(null));
        setMain((Main) in.readParcelable(null));
        setWind((Wind) in.readParcelable(null));
        setRain((Rain) in.readParcelable(null));
        setClouds((Cloud) in.readParcelable(null));
        setSnow((Snow) in.readParcelable(null));

    }
}
