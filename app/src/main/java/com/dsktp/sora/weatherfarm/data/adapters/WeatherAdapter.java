package com.dsktp.sora.weatherfarm.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.utils.FormatUtils;
import com.dsktp.sora.weatherfarm.utils.ImageUtils;
import com.dsktp.sora.weatherfarm.utils.TempUtils;
import com.dsktp.sora.weatherfarm.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 28/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contains the implementation of the RecyclerView adapter holding weather forecast
 * objects
 */
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyWeatherViewholder>
{
    private static final String DEBUG_TAG = "#WeatherAdapter";
    private List<WeatherForecastPOJO> mList;
    private List<WeatherForecastPOJO> mDailyList;
    private onClickListener mCallback;
    private Context mContext;

    /**
     * Default constructor
     */
    public WeatherAdapter()
    {
        //initialize the arrayLists
        mList = new ArrayList<>();
        mDailyList = new ArrayList<>();
    }

    /**
     * Custom Callback to handle events on the forecast viewHolder
     */
    public interface onClickListener
    {
        void onDayDetailsClick(WeatherForecastPOJO weatherForecastPOJO);
    }

    public void setList(List<WeatherForecastPOJO> mList)
    {
        //set the data
        this.mList = mList;
        mDailyList = TimeUtils.filterListByDay(mList); //get the filtered per day list
        notifyDataSetChanged(); // notify the adapter
    }
    /**
     * Setter method for the mCallback member variable
     * @param mCallback The class/fragment that implements this callback
     */
    public void setCallback(onClickListener mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public MyWeatherViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View inflatedRowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_weather_forecast_row_item,viewGroup,false);
        mContext = viewGroup.getContext();
        return new MyWeatherViewholder(inflatedRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWeatherViewholder myWeatherViewholder, int i)
    {
        WeatherForecastPOJO itemToBind = mDailyList.get(i);
        if(i==0)
        {
            //this is the first row of the list so set the day to today
            myWeatherViewholder.day.setText(R.string.day_text_value);
        }
        else
        {
            myWeatherViewholder.day.setText(TimeUtils.unixToDay(itemToBind.getDt()));
        }
        //populate the list
        myWeatherViewholder.temperature_min.setText(FormatUtils.formatTemperature(itemToBind.getMain().getTemp_min(),mContext));
        myWeatherViewholder.temperature_max.setText(FormatUtils.formatTemperature(itemToBind.getMain().getTemp_max(),mContext));
        myWeatherViewholder.icon.setImageResource(ImageUtils.getIcon(itemToBind.getWeather().get(0).getDescription()));
        myWeatherViewholder.weatherDescription.setText(itemToBind.getWeather().get(0).getDescription());
    }

    @Override
    public int getItemCount() {
        return mDailyList.size();
    }

    /**
     * This class extends the ViewHolder class
     */
    public class MyWeatherViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView day;
        private TextView temperature_min;
        private TextView temperature_max;
        private ImageView icon;
        private TextView weatherDescription;

        MyWeatherViewholder(@NonNull final View itemView)
        {
            super(itemView);
            //bind the views
            day = itemView.findViewById(R.id.tv_day_label);
            temperature_min = itemView.findViewById(R.id.tv_temperature_min_label);
            temperature_max = itemView.findViewById(R.id.tv_temperature_max_label);
            weatherDescription = itemView.findViewById(R.id.tv_weather_row_description_label);
            icon = itemView.findViewById(R.id.iv_weather_icon_row);
            itemView.setOnClickListener(this); //set the click listener on the viewholder
        }

        @Override
        public void onClick(View view) {
            //call the callback method to handle the click to the fragment / class that implements the interface
            mCallback.onDayDetailsClick(mDailyList.get(getAdapterPosition()));
        }
    }
}
