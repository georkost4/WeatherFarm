package com.dsktp.sora.weatherfarm;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.dsktp.sora.weatherfarm.data.model.Forecast.Cloud;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Main;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Rain;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Snow;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Weather;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.model.Forecast.Wind;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.WeatherForecastDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import static com.dsktp.sora.weatherfarm.utils.TimeUtils.unixToDay;
import static com.dsktp.sora.weatherfarm.utils.TimeUtils.unixToDayTime;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 23/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

@RunWith(AndroidJUnit4.class)
public class WeatherForecastDaoTest
{
    private AppDatabase mDb;
    private WeatherForecastDao mWeatherDao;

    @Before
    public void CreateDb()
    {
        Context context = InstrumentationRegistry.getContext();
        mDb = Room.inMemoryDatabaseBuilder(context,AppDatabase.class).build();
        mWeatherDao = mDb.weatherForecastDao();
    }

    @Test
    public void ReadWriteFromTable()
    {
        WeatherForecastPOJO dummyWeatherForeCastPOJO = new WeatherForecastPOJO();

        dummyWeatherForeCastPOJO.setClouds(new Cloud(13));
        dummyWeatherForeCastPOJO.setDt(1434);
        dummyWeatherForeCastPOJO.setMain(new Main(290,260,280,12,23,25,3));
        dummyWeatherForeCastPOJO.setRain(new Rain());
        dummyWeatherForeCastPOJO.setSnow(new Snow());
        dummyWeatherForeCastPOJO.setWind(new Wind());
        ArrayList<Weather> dummyWeatherList = new ArrayList<>();
        dummyWeatherList.add(new Weather(14,"maindummy","descDummy","24"));
        dummyWeatherForeCastPOJO.setWeather(dummyWeatherList);
        ArrayList<WeatherForecastPOJO> dummyWeatherPOJOlist = new ArrayList<>();
        dummyWeatherPOJOlist.add(dummyWeatherForeCastPOJO);

        mWeatherDao.insertWeatherForecastEntry(dummyWeatherPOJOlist);


        LiveData<List<WeatherForecastPOJO>> tableList = mWeatherDao.getWeatherEntries();
        List<WeatherForecastPOJO> list = tableList.getValue();

        assertThat(list.size(),equalTo(1));
        assertThat(list.get(0).getMain().getHumidity(),equalTo(3.0));
    }

    @Test
    public void RemoveItemFromTable()
    {
        ReadWriteFromTable();

        int rowsAffected = mWeatherDao.deleteForecastEntry(1434);
        int expectedRowsAffected = 1;

        assertThat(rowsAffected,equalTo(expectedRowsAffected));

        int rowsAffected1 = mWeatherDao.deleteForecastEntry(14324);
        int expectedRowsAffected1 = 0;

        assertThat(rowsAffected1,equalTo(expectedRowsAffected1));
    }

    @Test
    public void manipulateDateObject()
    {
        //get a list from the jsonr string
        ArrayList<WeatherForecastPOJO> dummyList = getFakeDataList();

        mWeatherDao.insertWeatherForecastEntry(dummyList);
        //get the list from the database
        ArrayList<WeatherForecastPOJO> listFromDb = (ArrayList<WeatherForecastPOJO>) mWeatherDao.getWeatherEntriesList();
        //assert that the lists are equal
        assertThat(dummyList.get(0).getDt(),equalTo(listFromDb.get(0).getDt()));

        String today = unixToDay((System.currentTimeMillis())/1000);
        ArrayList<WeatherForecastPOJO> todayList = new ArrayList<>();
        String tomorrow = unixToDay((Calendar.getInstance().getTimeInMillis())/1000 + 86400);
        ArrayList<WeatherForecastPOJO> tomorrowList = new ArrayList<>();
        String tomorrow1 = unixToDay((System.currentTimeMillis()/1000 + 86400*2));
        ArrayList<WeatherForecastPOJO> tomorrowList1 = new ArrayList<>();
        String tomorrow2 = unixToDay(System.currentTimeMillis()/1000 + 86400*3);
        ArrayList<WeatherForecastPOJO> tomorrowList2 = new ArrayList<>();
        String tomorrow3 = unixToDay(System.currentTimeMillis()/1000 + 86400*4);
        ArrayList<WeatherForecastPOJO> tomorrowList3 = new ArrayList<>();

        int testCounter = 0;
        // filter out the days from the 5 day list
        // by checking if it meets the if condition
        // the first one will be list(0)
        int i = 0;
        if(unixToDay(dummyList.get(i).getDt()).equals(today))
        {

            while (unixToDay(dummyList.get(i).getDt()).equals(today))
            {
                testCounter++;
                todayList.add(dummyList.get(i));
                System.out.println("Added the object " + unixToDayTime(dummyList.get(i).getDt()) + " to todayList");
                i++;
            }

        }
        if(unixToDay(dummyList.get(i).getDt()).equals(tomorrow))
        {
            while (unixToDay(dummyList.get(i).getDt()).equals(tomorrow))
            {
                testCounter++;
                tomorrowList.add(dummyList.get(i));
                System.out.println("Added the object " + unixToDayTime(dummyList.get(i).getDt()) + " to tomorrowList");
                i++;
            }

        }
        if(unixToDay(dummyList.get(i).getDt()).equals(tomorrow1))
        {
            while (unixToDay(dummyList.get(i).getDt()).equals(tomorrow1))
            {
                testCounter++;
                tomorrowList1.add(dummyList.get(i));
                System.out.println("Added the object " + unixToDayTime(dummyList.get(i).getDt()) + " to tomorrow1List");
                i++;
            }
        }
        if(unixToDay(dummyList.get(i).getDt()).equals(tomorrow2))
        {
            while (unixToDay(dummyList.get(i).getDt()).equals(tomorrow2))
            {
                testCounter++;
                tomorrowList2.add(dummyList.get(i));
                System.out.println("Added the object " + unixToDayTime(dummyList.get(i).getDt()) + " to tomorrow2List");
                i++;
            }
        }
        if(unixToDay(dummyList.get(i).getDt()).equals(tomorrow3))
        {
            while (unixToDay(dummyList.get(i).getDt()).equals(tomorrow3))
            {
                testCounter++;
                tomorrowList3.add(dummyList.get(i));
                System.out.println("Added the object " + unixToDayTime(dummyList.get(i).getDt()) + " to tomorrowList3");
                i++;
            }
        }

        System.out.println("This line is executed " + testCounter + " times" );
    }


    @After
    public void CloseDb()
    {
        mDb.close();
    }

    private ArrayList<WeatherForecastPOJO> getFakeDataList()
    {
        Gson gson = new GsonBuilder().create();
        return   gson.fromJson(jsonResponse, new TypeToken<List<WeatherForecastPOJO>>(){}.getType());

    }

    private String jsonResponse = "[\n" +
            "    {\n" +
            "        \"dt\": 1535230800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 293.14,\n" +
            "            \"temp_min\": 293.14,\n" +
            "            \"temp_max\": 296.476,\n" +
            "            \"pressure\": 1008.8,\n" +
            "            \"sea_level\": 1022.83,\n" +
            "            \"grnd_level\": 1008.8,\n" +
            "            \"humidity\": 56,\n" +
            "            \"temp_kf\": -3.33\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.76,\n" +
            "            \"deg\": 115.503\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535241600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 289.83,\n" +
            "            \"temp_min\": 289.83,\n" +
            "            \"temp_max\": 292.333,\n" +
            "            \"pressure\": 1008.65,\n" +
            "            \"sea_level\": 1022.73,\n" +
            "            \"grnd_level\": 1008.65,\n" +
            "            \"humidity\": 82,\n" +
            "            \"temp_kf\": -2.5\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 0.97,\n" +
            "            \"deg\": 120.001\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535252400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 288.75,\n" +
            "            \"temp_min\": 288.75,\n" +
            "            \"temp_max\": 290.416,\n" +
            "            \"pressure\": 1008.35,\n" +
            "            \"sea_level\": 1022.48,\n" +
            "            \"grnd_level\": 1008.35,\n" +
            "            \"humidity\": 87,\n" +
            "            \"temp_kf\": -1.67\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 0.52,\n" +
            "            \"deg\": 89.5\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535263200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 296.61,\n" +
            "            \"temp_min\": 296.61,\n" +
            "            \"temp_max\": 297.443,\n" +
            "            \"pressure\": 1008.63,\n" +
            "            \"sea_level\": 1022.66,\n" +
            "            \"grnd_level\": 1008.63,\n" +
            "            \"humidity\": 57,\n" +
            "            \"temp_kf\": -0.83\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.96,\n" +
            "            \"deg\": 40.504\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535274000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 303.232,\n" +
            "            \"temp_min\": 303.232,\n" +
            "            \"temp_max\": 303.232,\n" +
            "            \"pressure\": 1007.88,\n" +
            "            \"sea_level\": 1021.77,\n" +
            "            \"grnd_level\": 1007.88,\n" +
            "            \"humidity\": 43,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.86,\n" +
            "            \"deg\": 102.5\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535284800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 306.249,\n" +
            "            \"temp_min\": 306.249,\n" +
            "            \"temp_max\": 306.249,\n" +
            "            \"pressure\": 1005.79,\n" +
            "            \"sea_level\": 1019.74,\n" +
            "            \"grnd_level\": 1005.79,\n" +
            "            \"humidity\": 31,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.06,\n" +
            "            \"deg\": 69.5004\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535295600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 306.135,\n" +
            "            \"temp_min\": 306.135,\n" +
            "            \"temp_max\": 306.135,\n" +
            "            \"pressure\": 1004.48,\n" +
            "            \"sea_level\": 1018.33,\n" +
            "            \"grnd_level\": 1004.48,\n" +
            "            \"humidity\": 27,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.86,\n" +
            "            \"deg\": 97.5046\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535306400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 298.568,\n" +
            "            \"temp_min\": 298.568,\n" +
            "            \"temp_max\": 298.568,\n" +
            "            \"pressure\": 1004.66,\n" +
            "            \"sea_level\": 1018.74,\n" +
            "            \"grnd_level\": 1004.66,\n" +
            "            \"humidity\": 53,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.02,\n" +
            "            \"deg\": 107.008\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535317200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 296.595,\n" +
            "            \"temp_min\": 296.595,\n" +
            "            \"temp_max\": 296.595,\n" +
            "            \"pressure\": 1005.75,\n" +
            "            \"sea_level\": 1019.8,\n" +
            "            \"grnd_level\": 1005.75,\n" +
            "            \"humidity\": 61,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.45,\n" +
            "            \"deg\": 133.004\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535328000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 292.787,\n" +
            "            \"temp_min\": 292.787,\n" +
            "            \"temp_max\": 292.787,\n" +
            "            \"pressure\": 1005.99,\n" +
            "            \"sea_level\": 1020.06,\n" +
            "            \"grnd_level\": 1005.99,\n" +
            "            \"humidity\": 84,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.21,\n" +
            "            \"deg\": 85.0023\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535338800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 291.23,\n" +
            "            \"temp_min\": 291.23,\n" +
            "            \"temp_max\": 291.23,\n" +
            "            \"pressure\": 1006.51,\n" +
            "            \"sea_level\": 1020.6,\n" +
            "            \"grnd_level\": 1006.51,\n" +
            "            \"humidity\": 87,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.11,\n" +
            "            \"deg\": 327.5\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535349600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 297.017,\n" +
            "            \"temp_min\": 297.017,\n" +
            "            \"temp_max\": 297.017,\n" +
            "            \"pressure\": 1008.04,\n" +
            "            \"sea_level\": 1022.1,\n" +
            "            \"grnd_level\": 1008.04,\n" +
            "            \"humidity\": 59,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.33,\n" +
            "            \"deg\": 344.502\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535360400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 302.666,\n" +
            "            \"temp_min\": 302.666,\n" +
            "            \"temp_max\": 302.666,\n" +
            "            \"pressure\": 1008.16,\n" +
            "            \"sea_level\": 1022.06,\n" +
            "            \"grnd_level\": 1008.16,\n" +
            "            \"humidity\": 46,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.31,\n" +
            "            \"deg\": 11.0036\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535371200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 305.595,\n" +
            "            \"temp_min\": 305.595,\n" +
            "            \"temp_max\": 305.595,\n" +
            "            \"pressure\": 1007.22,\n" +
            "            \"sea_level\": 1021.12,\n" +
            "            \"grnd_level\": 1007.22,\n" +
            "            \"humidity\": 35,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.71,\n" +
            "            \"deg\": 28.5026\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535382000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 304.604,\n" +
            "            \"temp_min\": 304.604,\n" +
            "            \"temp_max\": 304.604,\n" +
            "            \"pressure\": 1007.22,\n" +
            "            \"sea_level\": 1021.4,\n" +
            "            \"grnd_level\": 1007.22,\n" +
            "            \"humidity\": 33,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.12,\n" +
            "            \"deg\": 31.0001\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 48\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535392800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 803,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"broken clouds\",\n" +
            "                \"icon\": \"04n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 300.848,\n" +
            "            \"temp_min\": 300.848,\n" +
            "            \"temp_max\": 300.848,\n" +
            "            \"pressure\": 1009.28,\n" +
            "            \"sea_level\": 1023.41,\n" +
            "            \"grnd_level\": 1009.28,\n" +
            "            \"humidity\": 43,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.01,\n" +
            "            \"deg\": 24.0037\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 68\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535403600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 803,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"broken clouds\",\n" +
            "                \"icon\": \"04n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 297.553,\n" +
            "            \"temp_min\": 297.553,\n" +
            "            \"temp_max\": 297.553,\n" +
            "            \"pressure\": 1011.08,\n" +
            "            \"sea_level\": 1025.15,\n" +
            "            \"grnd_level\": 1011.08,\n" +
            "            \"humidity\": 50,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.27,\n" +
            "            \"deg\": 359.008\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 64\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535414400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 803,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"broken clouds\",\n" +
            "                \"icon\": \"04n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 295.538,\n" +
            "            \"temp_min\": 295.538,\n" +
            "            \"temp_max\": 295.538,\n" +
            "            \"pressure\": 1011.67,\n" +
            "            \"sea_level\": 1025.78,\n" +
            "            \"grnd_level\": 1011.67,\n" +
            "            \"humidity\": 58,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.46,\n" +
            "            \"deg\": 358.502\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535425200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 803,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"broken clouds\",\n" +
            "                \"icon\": \"04n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 294.748,\n" +
            "            \"temp_min\": 294.748,\n" +
            "            \"temp_max\": 294.748,\n" +
            "            \"pressure\": 1011.79,\n" +
            "            \"sea_level\": 1025.84,\n" +
            "            \"grnd_level\": 1011.79,\n" +
            "            \"humidity\": 56,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 7.15,\n" +
            "            \"deg\": 356.501\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 80\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535436000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 803,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"broken clouds\",\n" +
            "                \"icon\": \"04d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 296.713,\n" +
            "            \"temp_min\": 296.713,\n" +
            "            \"temp_max\": 296.713,\n" +
            "            \"pressure\": 1012.76,\n" +
            "            \"sea_level\": 1026.9,\n" +
            "            \"grnd_level\": 1012.76,\n" +
            "            \"humidity\": 51,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 7.41,\n" +
            "            \"deg\": 5.00143\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 56\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535446800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 801,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"few clouds\",\n" +
            "                \"icon\": \"02d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 300.18,\n" +
            "            \"temp_min\": 300.18,\n" +
            "            \"temp_max\": 300.18,\n" +
            "            \"pressure\": 1012.98,\n" +
            "            \"sea_level\": 1027.11,\n" +
            "            \"grnd_level\": 1012.98,\n" +
            "            \"humidity\": 43,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 7.41,\n" +
            "            \"deg\": 16.002\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 24\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535457600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 302.488,\n" +
            "            \"temp_min\": 302.488,\n" +
            "            \"temp_max\": 302.488,\n" +
            "            \"pressure\": 1012.11,\n" +
            "            \"sea_level\": 1026.29,\n" +
            "            \"grnd_level\": 1012.11,\n" +
            "            \"humidity\": 39,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.61,\n" +
            "            \"deg\": 27.5009\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 36\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535468400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 301.739,\n" +
            "            \"temp_min\": 301.739,\n" +
            "            \"temp_max\": 301.739,\n" +
            "            \"pressure\": 1011.89,\n" +
            "            \"sea_level\": 1025.98,\n" +
            "            \"grnd_level\": 1011.89,\n" +
            "            \"humidity\": 38,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.81,\n" +
            "            \"deg\": 36.0059\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 44\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535479200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"02n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 299.002,\n" +
            "            \"temp_min\": 299.002,\n" +
            "            \"temp_max\": 299.002,\n" +
            "            \"pressure\": 1013.09,\n" +
            "            \"sea_level\": 1027.19,\n" +
            "            \"grnd_level\": 1013.09,\n" +
            "            \"humidity\": 46,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.66,\n" +
            "            \"deg\": 28\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 8\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535490000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 296.769,\n" +
            "            \"temp_min\": 296.769,\n" +
            "            \"temp_max\": 296.769,\n" +
            "            \"pressure\": 1014.17,\n" +
            "            \"sea_level\": 1028.42,\n" +
            "            \"grnd_level\": 1014.17,\n" +
            "            \"humidity\": 54,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.88,\n" +
            "            \"deg\": 21.5034\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535500800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 294.705,\n" +
            "            \"temp_min\": 294.705,\n" +
            "            \"temp_max\": 294.705,\n" +
            "            \"pressure\": 1014.76,\n" +
            "            \"sea_level\": 1028.87,\n" +
            "            \"grnd_level\": 1014.76,\n" +
            "            \"humidity\": 59,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.98,\n" +
            "            \"deg\": 0.00350952\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535511600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 293.42,\n" +
            "            \"temp_min\": 293.42,\n" +
            "            \"temp_max\": 293.42,\n" +
            "            \"pressure\": 1015.23,\n" +
            "            \"sea_level\": 1029.5,\n" +
            "            \"grnd_level\": 1015.23,\n" +
            "            \"humidity\": 63,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.36,\n" +
            "            \"deg\": 355.503\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535522400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 297.072,\n" +
            "            \"temp_min\": 297.072,\n" +
            "            \"temp_max\": 297.072,\n" +
            "            \"pressure\": 1016.23,\n" +
            "            \"sea_level\": 1030.33,\n" +
            "            \"grnd_level\": 1016.23,\n" +
            "            \"humidity\": 51,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.07,\n" +
            "            \"deg\": 12.5\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535533200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 301.166,\n" +
            "            \"temp_min\": 301.166,\n" +
            "            \"temp_max\": 301.166,\n" +
            "            \"pressure\": 1016.3,\n" +
            "            \"sea_level\": 1030.32,\n" +
            "            \"grnd_level\": 1016.3,\n" +
            "            \"humidity\": 42,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.79,\n" +
            "            \"deg\": 19.0035\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535544000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 303.396,\n" +
            "            \"temp_min\": 303.396,\n" +
            "            \"temp_max\": 303.396,\n" +
            "            \"pressure\": 1015.09,\n" +
            "            \"sea_level\": 1029.21,\n" +
            "            \"grnd_level\": 1015.09,\n" +
            "            \"humidity\": 38,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.02,\n" +
            "            \"deg\": 23.5022\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535554800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 801,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"few clouds\",\n" +
            "                \"icon\": \"02d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 302.627,\n" +
            "            \"temp_min\": 302.627,\n" +
            "            \"temp_max\": 302.627,\n" +
            "            \"pressure\": 1014.36,\n" +
            "            \"sea_level\": 1028.42,\n" +
            "            \"grnd_level\": 1014.36,\n" +
            "            \"humidity\": 37,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.31,\n" +
            "            \"deg\": 27.002\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 20\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535565600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 299.059,\n" +
            "            \"temp_min\": 299.059,\n" +
            "            \"temp_max\": 299.059,\n" +
            "            \"pressure\": 1015.41,\n" +
            "            \"sea_level\": 1029.62,\n" +
            "            \"grnd_level\": 1015.41,\n" +
            "            \"humidity\": 42,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.61,\n" +
            "            \"deg\": 32.0014\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 44\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535576400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 295.537,\n" +
            "            \"temp_min\": 295.537,\n" +
            "            \"temp_max\": 295.537,\n" +
            "            \"pressure\": 1016.5,\n" +
            "            \"sea_level\": 1030.8,\n" +
            "            \"grnd_level\": 1016.5,\n" +
            "            \"humidity\": 51,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.76,\n" +
            "            \"deg\": 67.0009\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535587200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 290.622,\n" +
            "            \"temp_min\": 290.622,\n" +
            "            \"temp_max\": 290.622,\n" +
            "            \"pressure\": 1016.86,\n" +
            "            \"sea_level\": 1031.02,\n" +
            "            \"grnd_level\": 1016.86,\n" +
            "            \"humidity\": 80,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.21,\n" +
            "            \"deg\": 4.0029\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535598000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 288.841,\n" +
            "            \"temp_min\": 288.841,\n" +
            "            \"temp_max\": 288.841,\n" +
            "            \"pressure\": 1016.76,\n" +
            "            \"sea_level\": 1031.05,\n" +
            "            \"grnd_level\": 1016.76,\n" +
            "            \"humidity\": 75,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.09,\n" +
            "            \"deg\": 354.5\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535608800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 295.948,\n" +
            "            \"temp_min\": 295.948,\n" +
            "            \"temp_max\": 295.948,\n" +
            "            \"pressure\": 1017.08,\n" +
            "            \"sea_level\": 1031.23,\n" +
            "            \"grnd_level\": 1017.08,\n" +
            "            \"humidity\": 40,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.64,\n" +
            "            \"deg\": 5.50305\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535619600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 301.536,\n" +
            "            \"temp_min\": 301.536,\n" +
            "            \"temp_max\": 301.536,\n" +
            "            \"pressure\": 1016.77,\n" +
            "            \"sea_level\": 1030.83,\n" +
            "            \"grnd_level\": 1016.77,\n" +
            "            \"humidity\": 34,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.83,\n" +
            "            \"deg\": 44.5028\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535630400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 303.408,\n" +
            "            \"temp_min\": 303.408,\n" +
            "            \"temp_max\": 303.408,\n" +
            "            \"pressure\": 1015.53,\n" +
            "            \"sea_level\": 1029.7,\n" +
            "            \"grnd_level\": 1015.53,\n" +
            "            \"humidity\": 30,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.01,\n" +
            "            \"deg\": 36.0007\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535641200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 302.789,\n" +
            "            \"temp_min\": 302.789,\n" +
            "            \"temp_max\": 302.789,\n" +
            "            \"pressure\": 1015.04,\n" +
            "            \"sea_level\": 1029.13,\n" +
            "            \"grnd_level\": 1015.04,\n" +
            "            \"humidity\": 28,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.91,\n" +
            "            \"deg\": 36.0092\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535652000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 296.939,\n" +
            "            \"temp_min\": 296.939,\n" +
            "            \"temp_max\": 296.939,\n" +
            "            \"pressure\": 1016.35,\n" +
            "            \"sea_level\": 1030.59,\n" +
            "            \"grnd_level\": 1016.35,\n" +
            "            \"humidity\": 34,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.46,\n" +
            "            \"deg\": 54.0027\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    }\n" +
            "]";

}
