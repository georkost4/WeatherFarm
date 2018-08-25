package com.dsktp.sora.weatherfarm.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.AppExecutors;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.FormatUtils;
import com.dsktp.sora.weatherfarm.utils.ImageUtils;
import com.dsktp.sora.weatherfarm.utils.TempUtils;
import com.dsktp.sora.weatherfarm.utils.TimeUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import static android.appwidget.AppWidgetManager.ACTION_APPWIDGET_UPDATE;
import static android.appwidget.AppWidgetManager.getInstance;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidgetProvider extends AppWidgetProvider
{
    private static String DEBUG_TAG = "#MyWidgetProvider";

    private static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                        final int appWidgetId) {

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
            @Override
            public void run() {
                List<WeatherForecastPOJO> weatherForecastPOJOList = AppDatabase.getsDbInstance(context).weatherForecastDao().getWeatherEntriesList();
                if(!weatherForecastPOJOList.isEmpty())
                {
                    updateValues(views,weatherForecastPOJOList,context);
                    // Instruct the widget manager to update the widget
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                }
            }
        });
    }

    private static void updateValues(RemoteViews views, List<WeatherForecastPOJO> weatherForecastPOJOList,Context context) {
        Log.i(DEBUG_TAG,"Updating the widget values");
        views.setTextViewText(R.id.tv_widget_date, SimpleDateFormat.getDateInstance(SimpleDateFormat.SHORT).format(weatherForecastPOJOList.get(0).getDt()));
        views.setTextViewText(R.id.tv_widget_location_value, AppUtils.getSelectedPosition(context)[0]);
        views.setTextViewText(R.id.tv_widget_temp, FormatUtils.formatTemperature(weatherForecastPOJOList.get(0).getMain().getTemp(),context));
        views.setImageViewResource(R.id.iv_widget_weather_icon, ImageUtils.getIcon(weatherForecastPOJOList.get(0).getWeather().get(0).getDescription()));
        views.setTextViewText(R.id.tv_widget_max_temp_value, FormatUtils.formatTemperature(weatherForecastPOJOList.get(0).getMain().getTemp_max(),context));
        views.setTextViewText(R.id.tv_widget_min_temp_value, FormatUtils.formatTemperature(weatherForecastPOJOList.get(0).getMain().getTemp_min(),context));
        views.setTextViewText(R.id.tv_widget_pressure_value, FormatUtils.formatPressure(weatherForecastPOJOList.get(0).getMain().getPressure()));
        views.setTextViewText(R.id.tv_widget_humidity_value, FormatUtils.formatHumidity(weatherForecastPOJOList.get(0).getMain().getHumidity()));

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //if the action equal to ACTION == UPDATE then update the Widget UI
        if(intent.getAction().equals(ACTION_APPWIDGET_UPDATE))
        {
            int[] ids = (int[]) intent.getExtras().get(AppWidgetManager.EXTRA_APPWIDGET_IDS);
            onUpdate(context,getInstance(context),ids);
        }
    }
}

