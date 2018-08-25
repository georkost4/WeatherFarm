package com.dsktp.sora.weatherfarm;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import static com.dsktp.sora.weatherfarm.utils.TimeUtils.unixToDay;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 30/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
@RunWith(JUnit4.class)
public class WeatherAdapterBindViewholderTest
{
    private List<WeatherForecastPOJO> dummyList;
    private String DEBUG_TAG = "Test class";


    @Before
    public void setup()
    {
        dummyList = populateListWithFakeData();
    }

    @Test
    public void pickTheRightIndex()
    {
        HashMap<String,ArrayList<WeatherForecastPOJO>> filteredByDayMap = new HashMap<>();

        ArrayList<WeatherForecastPOJO> filteredList = new ArrayList<>();

        String today = unixToDay((Calendar.getInstance().getTimeInMillis())/1000);
        System.out.println("Today is " + today);
        String tomorrow = unixToDay((Calendar.getInstance().getTimeInMillis())/1000 + 86400);
        System.out.println("Tomorrow is " + tomorrow);
        String tomorrow1 = unixToDay((System.currentTimeMillis()/1000 + 86400*2));
        String tomorrow2 = unixToDay(System.currentTimeMillis()/1000 + 86400*3);
        String tomorrow3 = unixToDay(System.currentTimeMillis()/1000 + 86400*4);

        //This will filter the response by day
        int viewIndex = 1;
        int listIndex = 0;
        //the first item of the list will represent the same day and the same hour

        switch (viewIndex)
        {
            case 0:
            {
                System.out.println(unixToDay(dummyList.get(listIndex).getDt()));
                while(unixToDay(dummyList.get(listIndex).getDt()).equals(today))
                {
                    System.out.println("Testubg");
                    filteredList.add(dummyList.get(listIndex));
                    //increment the index
                    listIndex++;
                }
                //save the entry to the hash map
                filteredByDayMap.put(today,filteredList);
                //clear the list
                filteredList.clear();
                listIndex = 0;
                break;
            }
            case 1:
            {
                while(unixToDay(dummyList.get(listIndex).getDt()).equals(tomorrow))
                {
                    System.out.println("Adding the day " + unixToDay(dummyList.get(listIndex).getDt()) + " to the list");
                    filteredList.add(dummyList.get(listIndex));
                    //increment the index
                    listIndex++;
                }
                //save the entry to the hash map
                System.out.println("The daily items added to the filtered list are = " + filteredList.size());
                filteredByDayMap.put(tomorrow,filteredList);
                //clear the list
                filteredList.clear();
                break;
            }
        }
//        System.out.println(filteredByDayMap.get(today));
        System.out.println(filteredByDayMap.get(tomorrow).get(0).getDt());
    }

    private ArrayList<WeatherForecastPOJO> populateListWithFakeData()
    {
        Gson gson = new GsonBuilder().create();
        return   gson.fromJson(jsonResponse, new TypeToken<List<WeatherForecastPOJO>>(){}.getType());

    }

    private String jsonResponse = "[\n" +
            "    {\n" +
            "        \"dt\": 1535144400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 296.09,\n" +
            "            \"temp_min\": 296.086,\n" +
            "            \"temp_max\": 296.09,\n" +
            "            \"pressure\": 1010.33,\n" +
            "            \"sea_level\": 1024.43,\n" +
            "            \"grnd_level\": 1010.33,\n" +
            "            \"humidity\": 53,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.76,\n" +
            "            \"deg\": 76.5016\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535155200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 291.92,\n" +
            "            \"temp_min\": 291.916,\n" +
            "            \"temp_max\": 291.92,\n" +
            "            \"pressure\": 1010.14,\n" +
            "            \"sea_level\": 1024.33,\n" +
            "            \"grnd_level\": 1010.14,\n" +
            "            \"humidity\": 79,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.31,\n" +
            "            \"deg\": 2.00278\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535166000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 290.19,\n" +
            "            \"temp_min\": 290.187,\n" +
            "            \"temp_max\": 290.19,\n" +
            "            \"pressure\": 1010.28,\n" +
            "            \"sea_level\": 1024.38,\n" +
            "            \"grnd_level\": 1010.28,\n" +
            "            \"humidity\": 83,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.56,\n" +
            "            \"deg\": 320.501\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535176800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 297.5,\n" +
            "            \"temp_min\": 297.495,\n" +
            "            \"temp_max\": 297.5,\n" +
            "            \"pressure\": 1010.62,\n" +
            "            \"sea_level\": 1024.6,\n" +
            "            \"grnd_level\": 1010.62,\n" +
            "            \"humidity\": 55,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.02,\n" +
            "            \"deg\": 338.008\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535187600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 302.876,\n" +
            "            \"temp_min\": 302.876,\n" +
            "            \"temp_max\": 302.876,\n" +
            "            \"pressure\": 1009.73,\n" +
            "            \"sea_level\": 1023.6,\n" +
            "            \"grnd_level\": 1009.73,\n" +
            "            \"humidity\": 46,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.12,\n" +
            "            \"deg\": 32.0019\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535198400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 305.89,\n" +
            "            \"temp_min\": 305.89,\n" +
            "            \"temp_max\": 305.89,\n" +
            "            \"pressure\": 1007.83,\n" +
            "            \"sea_level\": 1021.73,\n" +
            "            \"grnd_level\": 1007.83,\n" +
            "            \"humidity\": 36,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.21,\n" +
            "            \"deg\": 28.0024\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535209200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 306.067,\n" +
            "            \"temp_min\": 306.067,\n" +
            "            \"temp_max\": 306.067,\n" +
            "            \"pressure\": 1006.4,\n" +
            "            \"sea_level\": 1020.41,\n" +
            "            \"grnd_level\": 1006.4,\n" +
            "            \"humidity\": 32,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.87,\n" +
            "            \"deg\": 31.0019\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535220000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 299.623,\n" +
            "            \"temp_min\": 299.623,\n" +
            "            \"temp_max\": 299.623,\n" +
            "            \"pressure\": 1006.85,\n" +
            "            \"sea_level\": 1020.93,\n" +
            "            \"grnd_level\": 1006.85,\n" +
            "            \"humidity\": 57,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.57,\n" +
            "            \"deg\": 42.0027\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 36\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535230800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 801,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"few clouds\",\n" +
            "                \"icon\": \"02n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 297.329,\n" +
            "            \"temp_min\": 297.329,\n" +
            "            \"temp_max\": 297.329,\n" +
            "            \"pressure\": 1008.29,\n" +
            "            \"sea_level\": 1022.31,\n" +
            "            \"grnd_level\": 1008.29,\n" +
            "            \"humidity\": 58,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.22,\n" +
            "            \"deg\": 135.005\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 24\n" +
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
            "            \"temp\": 292.986,\n" +
            "            \"temp_min\": 292.986,\n" +
            "            \"temp_max\": 292.986,\n" +
            "            \"pressure\": 1008.21,\n" +
            "            \"sea_level\": 1022.32,\n" +
            "            \"grnd_level\": 1008.21,\n" +
            "            \"humidity\": 86,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.17,\n" +
            "            \"deg\": 104.507\n" +
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
            "            \"temp\": 290.951,\n" +
            "            \"temp_min\": 290.951,\n" +
            "            \"temp_max\": 290.951,\n" +
            "            \"pressure\": 1007.82,\n" +
            "            \"sea_level\": 1021.95,\n" +
            "            \"grnd_level\": 1007.82,\n" +
            "            \"humidity\": 90,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.05,\n" +
            "            \"deg\": 86.0076\n" +
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
            "            \"temp\": 297.501,\n" +
            "            \"temp_min\": 297.501,\n" +
            "            \"temp_max\": 297.501,\n" +
            "            \"pressure\": 1008.17,\n" +
            "            \"sea_level\": 1022.19,\n" +
            "            \"grnd_level\": 1008.17,\n" +
            "            \"humidity\": 61,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.88,\n" +
            "            \"deg\": 54.0008\n" +
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
            "            \"temp\": 303.905,\n" +
            "            \"temp_min\": 303.905,\n" +
            "            \"temp_max\": 303.905,\n" +
            "            \"pressure\": 1007.34,\n" +
            "            \"sea_level\": 1021.21,\n" +
            "            \"grnd_level\": 1007.34,\n" +
            "            \"humidity\": 40,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.96,\n" +
            "            \"deg\": 101.503\n" +
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
            "            \"temp\": 306.67,\n" +
            "            \"temp_min\": 306.67,\n" +
            "            \"temp_max\": 306.67,\n" +
            "            \"pressure\": 1005.45,\n" +
            "            \"sea_level\": 1019.3,\n" +
            "            \"grnd_level\": 1005.45,\n" +
            "            \"humidity\": 33,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.06,\n" +
            "            \"deg\": 92.0032\n" +
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
            "            \"temp\": 306.383,\n" +
            "            \"temp_min\": 306.383,\n" +
            "            \"temp_max\": 306.383,\n" +
            "            \"pressure\": 1003.94,\n" +
            "            \"sea_level\": 1017.89,\n" +
            "            \"grnd_level\": 1003.94,\n" +
            "            \"humidity\": 29,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.95,\n" +
            "            \"deg\": 116.004\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535306400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 299.735,\n" +
            "            \"temp_min\": 299.735,\n" +
            "            \"temp_max\": 299.735,\n" +
            "            \"pressure\": 1004.12,\n" +
            "            \"sea_level\": 1018.31,\n" +
            "            \"grnd_level\": 1004.12,\n" +
            "            \"humidity\": 60,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 0.76,\n" +
            "            \"deg\": 156.001\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 48\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535317200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 801,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"few clouds\",\n" +
            "                \"icon\": \"02n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 297.252,\n" +
            "            \"temp_min\": 297.252,\n" +
            "            \"temp_max\": 297.252,\n" +
            "            \"pressure\": 1005.29,\n" +
            "            \"sea_level\": 1019.38,\n" +
            "            \"grnd_level\": 1005.29,\n" +
            "            \"humidity\": 66,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.25,\n" +
            "            \"deg\": 104.001\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 12\n" +
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
            "            \"temp\": 292.984,\n" +
            "            \"temp_min\": 292.984,\n" +
            "            \"temp_max\": 292.984,\n" +
            "            \"pressure\": 1005.35,\n" +
            "            \"sea_level\": 1019.42,\n" +
            "            \"grnd_level\": 1005.35,\n" +
            "            \"humidity\": 75,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.63,\n" +
            "            \"deg\": 92.5009\n" +
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
            "            \"temp\": 290.738,\n" +
            "            \"temp_min\": 290.738,\n" +
            "            \"temp_max\": 290.738,\n" +
            "            \"pressure\": 1005.77,\n" +
            "            \"sea_level\": 1019.84,\n" +
            "            \"grnd_level\": 1005.77,\n" +
            "            \"humidity\": 90,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 1.21,\n" +
            "            \"deg\": 60.0068\n" +
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
            "            \"temp\": 297.084,\n" +
            "            \"temp_min\": 297.084,\n" +
            "            \"temp_max\": 297.084,\n" +
            "            \"pressure\": 1006.82,\n" +
            "            \"sea_level\": 1020.78,\n" +
            "            \"grnd_level\": 1006.82,\n" +
            "            \"humidity\": 61,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 2.21,\n" +
            "            \"deg\": 352.004\n" +
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
            "                \"icon\": \"02d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 303.139,\n" +
            "            \"temp_min\": 303.139,\n" +
            "            \"temp_max\": 303.139,\n" +
            "            \"pressure\": 1007.08,\n" +
            "            \"sea_level\": 1020.95,\n" +
            "            \"grnd_level\": 1007.08,\n" +
            "            \"humidity\": 44,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.03,\n" +
            "            \"deg\": 7.50214\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 8\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535371200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 500,\n" +
            "                \"main\": \"Rain\",\n" +
            "                \"description\": \"light rain\",\n" +
            "                \"icon\": \"10d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 302.509,\n" +
            "            \"temp_min\": 302.509,\n" +
            "            \"temp_max\": 302.509,\n" +
            "            \"pressure\": 1006.67,\n" +
            "            \"sea_level\": 1020.65,\n" +
            "            \"grnd_level\": 1006.67,\n" +
            "            \"humidity\": 52,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.46,\n" +
            "            \"deg\": 22.504\n" +
            "        },\n" +
            "        \"rain\": {\n" +
            "            \"3h\": 0.72\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 24\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535382000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 501,\n" +
            "                \"main\": \"Rain\",\n" +
            "                \"description\": \"moderate rain\",\n" +
            "                \"icon\": \"10d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 293.694,\n" +
            "            \"temp_min\": 293.694,\n" +
            "            \"temp_max\": 293.694,\n" +
            "            \"pressure\": 1008.24,\n" +
            "            \"sea_level\": 1022.18,\n" +
            "            \"grnd_level\": 1008.24,\n" +
            "            \"humidity\": 94,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.16,\n" +
            "            \"deg\": 331.501\n" +
            "        },\n" +
            "        \"rain\": {\n" +
            "            \"3h\": 4.74\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 68\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535392800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 500,\n" +
            "                \"main\": \"Rain\",\n" +
            "                \"description\": \"light rain\",\n" +
            "                \"icon\": \"10n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 294.581,\n" +
            "            \"temp_min\": 294.581,\n" +
            "            \"temp_max\": 294.581,\n" +
            "            \"pressure\": 1008.6,\n" +
            "            \"sea_level\": 1022.79,\n" +
            "            \"grnd_level\": 1008.6,\n" +
            "            \"humidity\": 83,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.16,\n" +
            "            \"deg\": 356.509\n" +
            "        },\n" +
            "        \"rain\": {\n" +
            "            \"3h\": 1.39\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 48\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535403600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 294.202,\n" +
            "            \"temp_min\": 294.202,\n" +
            "            \"temp_max\": 294.202,\n" +
            "            \"pressure\": 1010.01,\n" +
            "            \"sea_level\": 1024.09,\n" +
            "            \"grnd_level\": 1010.01,\n" +
            "            \"humidity\": 77,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.91,\n" +
            "            \"deg\": 350\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 32\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535414400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 801,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"few clouds\",\n" +
            "                \"icon\": \"02n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 293.148,\n" +
            "            \"temp_min\": 293.148,\n" +
            "            \"temp_max\": 293.148,\n" +
            "            \"pressure\": 1010.64,\n" +
            "            \"sea_level\": 1024.65,\n" +
            "            \"grnd_level\": 1010.64,\n" +
            "            \"humidity\": 76,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.71,\n" +
            "            \"deg\": 346.006\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 20\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535425200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 801,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"few clouds\",\n" +
            "                \"icon\": \"02n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 292.285,\n" +
            "            \"temp_min\": 292.285,\n" +
            "            \"temp_max\": 292.285,\n" +
            "            \"pressure\": 1011.36,\n" +
            "            \"sea_level\": 1025.41,\n" +
            "            \"grnd_level\": 1011.36,\n" +
            "            \"humidity\": 76,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.82,\n" +
            "            \"deg\": 346.507\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 24\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535436000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 294.811,\n" +
            "            \"temp_min\": 294.811,\n" +
            "            \"temp_max\": 294.811,\n" +
            "            \"pressure\": 1012.4,\n" +
            "            \"sea_level\": 1026.42,\n" +
            "            \"grnd_level\": 1012.4,\n" +
            "            \"humidity\": 68,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.67,\n" +
            "            \"deg\": 353.001\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 32\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535446800,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 802,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"scattered clouds\",\n" +
            "                \"icon\": \"03d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 298.339,\n" +
            "            \"temp_min\": 298.339,\n" +
            "            \"temp_max\": 298.339,\n" +
            "            \"pressure\": 1012.95,\n" +
            "            \"sea_level\": 1026.82,\n" +
            "            \"grnd_level\": 1012.95,\n" +
            "            \"humidity\": 58,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 7.37,\n" +
            "            \"deg\": 8.00421\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 48\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535457600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 803,\n" +
            "                \"main\": \"Clouds\",\n" +
            "                \"description\": \"broken clouds\",\n" +
            "                \"icon\": \"04d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 300.652,\n" +
            "            \"temp_min\": 300.652,\n" +
            "            \"temp_max\": 300.652,\n" +
            "            \"pressure\": 1012.04,\n" +
            "            \"sea_level\": 1025.97,\n" +
            "            \"grnd_level\": 1012.04,\n" +
            "            \"humidity\": 48,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 7.56,\n" +
            "            \"deg\": 22.5003\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 56\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535468400,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"02d\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 300.118,\n" +
            "            \"temp_min\": 300.118,\n" +
            "            \"temp_max\": 300.118,\n" +
            "            \"pressure\": 1011.89,\n" +
            "            \"sea_level\": 1025.84,\n" +
            "            \"grnd_level\": 1011.89,\n" +
            "            \"humidity\": 49,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.36,\n" +
            "            \"deg\": 20.5027\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 8\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535479200,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 500,\n" +
            "                \"main\": \"Rain\",\n" +
            "                \"description\": \"light rain\",\n" +
            "                \"icon\": \"10n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 296.81,\n" +
            "            \"temp_min\": 296.81,\n" +
            "            \"temp_max\": 296.81,\n" +
            "            \"pressure\": 1012.58,\n" +
            "            \"sea_level\": 1026.67,\n" +
            "            \"grnd_level\": 1012.58,\n" +
            "            \"humidity\": 71,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.32,\n" +
            "            \"deg\": 21.5054\n" +
            "        },\n" +
            "        \"rain\": {\n" +
            "            \"3h\": 1.05\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 12\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535490000,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 501,\n" +
            "                \"main\": \"Rain\",\n" +
            "                \"description\": \"moderate rain\",\n" +
            "                \"icon\": \"10n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 293.105,\n" +
            "            \"temp_min\": 293.105,\n" +
            "            \"temp_max\": 293.105,\n" +
            "            \"pressure\": 1013.58,\n" +
            "            \"sea_level\": 1027.71,\n" +
            "            \"grnd_level\": 1013.58,\n" +
            "            \"humidity\": 86,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 3.78,\n" +
            "            \"deg\": 3.50989\n" +
            "        },\n" +
            "        \"rain\": {\n" +
            "            \"3h\": 3.57\n" +
            "        },\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 32\n" +
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
            "            \"temp\": 292.506,\n" +
            "            \"temp_min\": 292.506,\n" +
            "            \"temp_max\": 292.506,\n" +
            "            \"pressure\": 1013.55,\n" +
            "            \"sea_level\": 1027.71,\n" +
            "            \"grnd_level\": 1013.55,\n" +
            "            \"humidity\": 83,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.07,\n" +
            "            \"deg\": 350.504\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
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
            "            \"temp\": 292.287,\n" +
            "            \"temp_min\": 292.287,\n" +
            "            \"temp_max\": 292.287,\n" +
            "            \"pressure\": 1013.67,\n" +
            "            \"sea_level\": 1027.8,\n" +
            "            \"grnd_level\": 1013.67,\n" +
            "            \"humidity\": 84,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.7,\n" +
            "            \"deg\": 347.504\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
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
            "            \"temp\": 296.205,\n" +
            "            \"temp_min\": 296.205,\n" +
            "            \"temp_max\": 296.205,\n" +
            "            \"pressure\": 1014.2,\n" +
            "            \"sea_level\": 1028.33,\n" +
            "            \"grnd_level\": 1014.2,\n" +
            "            \"humidity\": 71,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 5.66,\n" +
            "            \"deg\": 3.50153\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
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
            "            \"temp\": 301.496,\n" +
            "            \"temp_min\": 301.496,\n" +
            "            \"temp_max\": 301.496,\n" +
            "            \"pressure\": 1014.45,\n" +
            "            \"sea_level\": 1028.45,\n" +
            "            \"grnd_level\": 1014.45,\n" +
            "            \"humidity\": 50,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.66,\n" +
            "            \"deg\": 13.5039\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
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
            "            \"temp\": 303.826,\n" +
            "            \"temp_min\": 303.826,\n" +
            "            \"temp_max\": 303.826,\n" +
            "            \"pressure\": 1013.59,\n" +
            "            \"sea_level\": 1027.53,\n" +
            "            \"grnd_level\": 1013.59,\n" +
            "            \"humidity\": 40,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.61,\n" +
            "            \"deg\": 21.5002\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
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
            "            \"temp\": 303.107,\n" +
            "            \"temp_min\": 303.107,\n" +
            "            \"temp_max\": 303.107,\n" +
            "            \"pressure\": 1013.11,\n" +
            "            \"sea_level\": 1027.19,\n" +
            "            \"grnd_level\": 1013.11,\n" +
            "            \"humidity\": 38,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 6.01,\n" +
            "            \"deg\": 27.5105\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 20\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"dt\": 1535565600,\n" +
            "        \"weather\": [\n" +
            "            {\n" +
            "                \"id\": 800,\n" +
            "                \"main\": \"Clear\",\n" +
            "                \"description\": \"clear sky\",\n" +
            "                \"icon\": \"01n\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"main\": {\n" +
            "            \"temp\": 298.355,\n" +
            "            \"temp_min\": 298.355,\n" +
            "            \"temp_max\": 298.355,\n" +
            "            \"pressure\": 1014.63,\n" +
            "            \"sea_level\": 1028.78,\n" +
            "            \"grnd_level\": 1014.63,\n" +
            "            \"humidity\": 46,\n" +
            "            \"temp_kf\": 0\n" +
            "        },\n" +
            "        \"wind\": {\n" +
            "            \"speed\": 4.21,\n" +
            "            \"deg\": 31.0015\n" +
            "        },\n" +
            "        \"rain\": {},\n" +
            "        \"clouds\": {\n" +
            "            \"all\": 0\n" +
            "        }\n" +
            "    }\n" +
            "]";

}
