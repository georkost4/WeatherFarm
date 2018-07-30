package com.dsktp.sora.weatherfarm.data.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
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
public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyWeatherViewholder>
{
    private static final String DEBUG_TAG = "#WeatherAdapter";
    private List<WeatherForecastPOJO> mList;
    private List<WeatherForecastPOJO> mDailyList;

    private onClickListener mCallback;

    public WeatherAdapter()
    {
        mList = new ArrayList<>();
        mDailyList = new ArrayList<>();
    }

    public interface onClickListener
    {
        void handleClick();
    }

    public void setList(List<WeatherForecastPOJO> mList)
    {
        this.mList = mList;
        mDailyList = TimeUtils.filterListByDay(mList);
        notifyDataSetChanged();
    }

    public void setCallback(onClickListener mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public MyWeatherViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View inflatedRowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.weather_forecast_row_item,viewGroup,false);

        return new MyWeatherViewholder(inflatedRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWeatherViewholder myWeatherViewholder, int i)
    {
        WeatherForecastPOJO itemToBind = mDailyList.get(i);
        myWeatherViewholder.day.setText(TimeUtils.unixToDay(itemToBind.getDt()));
        String temperatureMinText = TempUtils.kelvingToCelcius(itemToBind.getMain().getTemp_min()) + " °C"; // todo create util method for formatting the sign
        String temperatureMaxText = TempUtils.kelvingToCelcius(itemToBind.getMain().getTemp_max()) + " °C";
        myWeatherViewholder.temperature_min.setText(temperatureMinText);
        myWeatherViewholder.temperature_max.setText(temperatureMaxText);
        myWeatherViewholder.icon.setImageResource(ImageUtils.getIcon(itemToBind.getWeather().get(0).getIcon()));
        myWeatherViewholder.weatherDescription.setText(itemToBind.getWeather().get(0).getDescription());

    }

    @Override
    public int getItemCount() {
        return mDailyList.size();
    }

    public class MyWeatherViewholder extends RecyclerView.ViewHolder {

        public TextView day;
        private TextView temperature_min;
        private TextView temperature_max;
        private ImageView icon;
        private TextView weatherDescription;

        public MyWeatherViewholder(@NonNull final View itemView)
        {
            super(itemView);
            day = itemView.findViewById(R.id.tv_day_label);
            temperature_min = itemView.findViewById(R.id.tv_temperature_min_label);
            temperature_max = itemView.findViewById(R.id.tv_temperature_max_label);
            weatherDescription = itemView.findViewById(R.id.tv_weather_row_description_label);
            icon = itemView.findViewById(R.id.iv_weather_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //todo to be implemented
                    Toast.makeText(itemView.getContext(),"To be implemented",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
