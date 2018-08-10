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
 * This class represents a Main object that is a part of the API response
 * when you request weather forecast data. It also implements the Parcelable
 * interface so we can write the object into Room Database
 */
public class Main implements Parcelable {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double pressure;
    private double sea_level;
    private double grnd_level;
    private double humidity;
    private double temp_kf; //todo there is not such field in current weather


    public Main(double temp, double temp_min, double temp_max, double pressure, double sea_level, double grnd_level, double humidity) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.sea_level = sea_level;
        this.grnd_level = grnd_level;
        this.humidity = humidity;
    }

    public Main() {  }

    public double getTemp() {
        return temp;
    }

    private void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    private void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    private void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getPressure() {
        return pressure;
    }

    private void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSea_level() {
        return sea_level;
    }

    private void setSea_level(double sea_level) {
        this.sea_level = sea_level;
    }

    public double getGrnd_level() {
        return grnd_level;
    }

    private void setGrnd_level(double grnd_level) {
        this.grnd_level = grnd_level;
    }

    public double getHumidity() {
        return humidity;
    }

    private void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        //write the values to Parcel object
        dest.writeDouble(temp);
        dest.writeDouble(temp_min);
        dest.writeDouble(temp_max);
        dest.writeDouble(pressure);
        dest.writeDouble(sea_level);
        dest.writeDouble(grnd_level);
        dest.writeDouble(humidity);
    }

    public static final Parcelable.Creator<Main> CREATOR = new Parcelable.Creator<Main>()
    {
        public Main createFromParcel(Parcel in) {
            return new Main(in);
        }

        public Main[] newArray(int size) {
            return new Main[size];
        }
    };


    private Main(Parcel in)
    {
        //read and set the values from
        //the Parcel object
        setTemp(in.readDouble());
        setTemp_min(in.readDouble());
        setTemp_max(in.readDouble());
        setPressure(in.readDouble());
        setSea_level(in.readDouble());
        setGrnd_level(in.readDouble());
        setHumidity(in.readDouble());
    }
}
