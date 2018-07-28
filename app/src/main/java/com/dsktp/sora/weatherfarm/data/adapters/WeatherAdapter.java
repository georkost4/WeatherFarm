package com.dsktp.sora.weatherfarm.data.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.DELETE;

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
    private onClickListener mCallback;

    public WeatherAdapter()
    {
        mList = new ArrayList<>();
    }

    public interface onClickListener
    {
        void handleClick();
    }

    public void setList(List<WeatherForecastPOJO> mList)
    {
        this.mList = mList;
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
        int j = i;
        long epoch = System.currentTimeMillis()/1000;
        String today = Utils.Time.unixToDay(epoch);
        String tomorrow = Utils.Time.unixToDay(epoch + 86400);
        String tomorrow1 = Utils.Time.unixToDay(epoch + 86400);
        String tomorrow2 = Utils.Time.unixToDay(epoch + 86400);
        String tomorrow3 = Utils.Time.unixToDay(epoch + 86400);
//        String tomorrow4 = Utils.Time.unixToDay(epoch + 86400);

        Log.d("DEBUG","List size = " + mList.size());
        switch (i)
        {
            case 0:
            {
                while(Utils.Time.unixToDay(mList.get(i).getDt()).equals(today))
                {
                    i++;
                    j = i;
                }
                break;
            }
            case 1:
            {
                while(Utils.Time.unixToDay(mList.get(i).getDt()).equals(tomorrow))
                {
                    i++;
                    j = i;
                }
                break;
            }
            case 2:
            {
                while(Utils.Time.unixToDay(mList.get(i).getDt()).equals(tomorrow1))
                {
                    i++;
                    j = i;
                }
                break;
            }
            case 3:
            {
                while(Utils.Time.unixToDay(mList.get(i).getDt()).equals(tomorrow2))
                {
                    i++;
                    j = i;
                }
                break;
            }
            case 4:
            {
                while(Utils.Time.unixToDay(mList.get(i).getDt()).equals(tomorrow3))
                {
                    i++;
                    j = i;
                }
                break;
            }

        }


        WeatherForecastPOJO itemToBind = mList.get(j);
        myWeatherViewholder.day.setText(Utils.Time.unixToDate(itemToBind.getDt()));
        myWeatherViewholder.temperature_min.setText(Utils.Temperature.kelvingToCelcius(itemToBind.getMain().getTemp_min()));
        myWeatherViewholder.temperature_max.setText(Utils.Temperature.kelvingToCelcius(itemToBind.getMain().getTemp_max()));
        myWeatherViewholder.icon.setImageResource(Utils.Image.getIcon(itemToBind.getWeather().get(0).getIcon()));
        Log.d(DEBUG_TAG,"temperature mix = " + myWeatherViewholder.temperature_max.getText());
        Log.d(DEBUG_TAG,"temperature max = " + myWeatherViewholder.temperature_min.getText());



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyWeatherViewholder extends RecyclerView.ViewHolder {

        public TextView day;
        private TextView temperature_min;
        private TextView temperature_max;
        private ImageView icon;

        public MyWeatherViewholder(@NonNull final View itemView)
        {
            super(itemView);
            day = itemView.findViewById(R.id.tv_day_label);
            temperature_min = itemView.findViewById(R.id.tv_temperature_min_label);
            temperature_max = itemView.findViewById(R.id.tv_temperature_max_label);
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
