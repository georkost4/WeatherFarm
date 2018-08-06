package com.dsktp.sora.weatherfarm;

import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.dsktp.sora.weatherfarm.utils.TimeUtils.getCurrentDay;
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
        populateListWithFakeData();
    }

    @Test
    public void pickTheRightIndex()
    {
        String today = getCurrentDay();
        String tomorrow = unixToDay((Calendar.getInstance().getTimeInMillis())/1000 + 86400);
        System.out.println("Tomorrow = " + tomorrow);
        String tomorrow1 = unixToDay((System.currentTimeMillis()/1000 + 86400*2));
        System.out.println("Tomorrow1 = " + tomorrow1);
        String tomorrow2 = unixToDay(System.currentTimeMillis()/1000 + 86400*3);
        System.out.println("List size = "+ dummyList.size());
        System.out.println("DAY = " + unixToDay(dummyList.get(0).getDt()));
        System.out.println("DAY = " + unixToDay(dummyList.get(4).getDt()));
        System.out.println("DAY = " + unixToDay(dummyList.get(12).getDt()));
        System.out.println("DAY = " + unixToDay(dummyList.get(20).getDt()));
//        System.out.println("DAY = " + unixToDay(dummyList.get(28).getDt()));

        int i_based = 3;
        int index = 0;
        switch (i_based)
        {
            case 0:
            {
                index = 0;
                break;
            }
            case 1:
            {

                while (!tomorrow.equals(unixToDay(dummyList.get(index).getDt())))
                {
                    System.out.println("DayToTest = " + unixToDay(dummyList.get(index).getDt()));

                    index++;
                }
                break;
            }
            case 2:
            {
                while (!tomorrow1.equals(unixToDay(dummyList.get(index).getDt())))
                {
                    System.out.println("Day to test = " +  unixToDay(dummyList.get(index).getDt()));
                    index++;
                }
                break;
            }
            case 3:
            {
                while (!tomorrow2.equals(unixToDay(dummyList.get(index).getDt())))
                {
                    System.out.println("Day to test = " +  unixToDay(dummyList.get(index).getDt()));
                    index++;
                }
                break;
            }
        }

        System.out.print("The value of index = " + index
        );
    }

    private void populateListWithFakeData()
    {
        dummyList = new ArrayList<>();
        WeatherForecastPOJO dummyObject = new WeatherForecastPOJO();
        dummyObject.setDt(1532941200);
        dummyList.add(dummyObject);

        WeatherForecastPOJO dummyObject1 = new WeatherForecastPOJO();
        dummyObject1.setDt(1532952000);
        dummyList.add(dummyObject1);

        WeatherForecastPOJO dummyObject2 = new WeatherForecastPOJO();
        dummyObject2.setDt(1532962800);
        dummyList.add(dummyObject2);

        WeatherForecastPOJO dummyObject3 = new WeatherForecastPOJO();
        dummyObject3.setDt(1532973600);
        dummyList.add(dummyObject3);

        WeatherForecastPOJO dummyObject4 = new WeatherForecastPOJO();
        dummyObject4.setDt(1532984400);
        dummyList.add(dummyObject4);

        WeatherForecastPOJO dummyObject5 = new WeatherForecastPOJO();
        dummyObject5.setDt(1532995200);
        dummyList.add(dummyObject5);

        WeatherForecastPOJO dummyObject6 = new WeatherForecastPOJO();
        dummyObject6.setDt(1533006000);
        dummyList.add(dummyObject6);

        WeatherForecastPOJO dummyObject7 = new WeatherForecastPOJO();
        dummyObject7.setDt(1533016800);
        dummyList.add(dummyObject7);

        WeatherForecastPOJO dummyObject8 = new WeatherForecastPOJO();
        dummyObject8.setDt(1533027600);
        dummyList.add(dummyObject8);

        WeatherForecastPOJO dummyObject9 = new WeatherForecastPOJO();
        dummyObject9.setDt(1533038400);
        dummyList.add(dummyObject9);

        WeatherForecastPOJO dummyObject10 = new WeatherForecastPOJO();
        dummyObject10.setDt(1533049200);
        dummyList.add(dummyObject10);

        WeatherForecastPOJO dummyObject11 = new WeatherForecastPOJO();
        dummyObject11.setDt(1533060000);
        dummyList.add(dummyObject11);

        WeatherForecastPOJO dummyObject12 = new WeatherForecastPOJO();
        dummyObject12.setDt(1533070800);
        dummyList.add(dummyObject12);

        WeatherForecastPOJO dummyObject13 = new WeatherForecastPOJO();
        dummyObject13.setDt(1533081600);
        dummyList.add(dummyObject13);

        WeatherForecastPOJO dummyObject14 = new WeatherForecastPOJO();
        dummyObject14.setDt(1533092400);
        dummyList.add(dummyObject14);

        WeatherForecastPOJO dummyObject15 = new WeatherForecastPOJO();
        dummyObject15.setDt(1533103200);
        dummyList.add(dummyObject15);

        WeatherForecastPOJO dummyObject16 = new WeatherForecastPOJO();
        dummyObject16.setDt(1533114000);
        dummyList.add(dummyObject16);

        WeatherForecastPOJO dummyObject17 = new WeatherForecastPOJO();
        dummyObject17.setDt(1533124800);
        dummyList.add(dummyObject17);

        WeatherForecastPOJO dummyObject18 = new WeatherForecastPOJO();
        dummyObject18.setDt(1533135600);
        dummyList.add(dummyObject18);


        WeatherForecastPOJO dummyObject19 = new WeatherForecastPOJO();
        dummyObject19.setDt(1533146400);
        dummyList.add(dummyObject19);

        WeatherForecastPOJO dummyObject20 = new WeatherForecastPOJO();
        dummyObject20.setDt(1533157200);
        dummyList.add(dummyObject20);

//        WeatherForecastPOJO dummyObject21 = new WeatherForecastPOJO();
//        dummyObject21.setDt(1533016800);
//        dummyList.add(dummyObject7);
//
//        WeatherForecastPOJO dummyObject8 = new WeatherForecastPOJO();
//        dummyObject8.setDt(1533027600);
//        dummyList.add(dummyObject8);
//
//        WeatherForecastPOJO dummyObject9 = new WeatherForecastPOJO();
//        dummyObject9.setDt(1533038400);
//        dummyList.add(dummyObject9);
//
//        WeatherForecastPOJO dummyObject10 = new WeatherForecastPOJO();
//        dummyObject10.setDt(1533049200);
//        dummyList.add(dummyObject10);
//
//        WeatherForecastPOJO dummyObject11 = new WeatherForecastPOJO();
//        dummyObject11.setDt(1533060000);
//        dummyList.add(dummyObject11);
//
//        WeatherForecastPOJO dummyObject12 = new WeatherForecastPOJO();
//        dummyObject12.setDt(1533070800);
//        dummyList.add(dummyObject12);
//
//        WeatherForecastPOJO dummyObject13 = new WeatherForecastPOJO();
//        dummyObject13.setDt(1533081600);
//        dummyList.add(dummyObject13);
//
//        WeatherForecastPOJO dummyObject14 = new WeatherForecastPOJO();
//        dummyObject14.setDt(1533092400);
//        dummyList.add(dummyObject14);
//
//        WeatherForecastPOJO dummyObject15 = new WeatherForecastPOJO();
//        dummyObject15.setDt(1533103200);
//        dummyList.add(dummyObject15);
//
//        WeatherForecastPOJO dummyObject16 = new WeatherForecastPOJO();
//        dummyObject16.setDt(1533114000);
//        dummyList.add(dummyObject16);
//
//        WeatherForecastPOJO dummyObject17 = new WeatherForecastPOJO();
//        dummyObject17.setDt(1533124800);
//        dummyList.add(dummyObject17);
//
//        WeatherForecastPOJO dummyObject18 = new WeatherForecastPOJO();
//        dummyObject18.setDt(1533135600);
//        dummyList.add(dummyObject18);
//
//
//        WeatherForecastPOJO dummyObject19 = new WeatherForecastPOJO();
//        dummyObject19.setDt(1533146400);
//        dummyList.add(dummyObject19);
//
//        WeatherForecastPOJO dummyObject20 = new WeatherForecastPOJO();
//        dummyObject20.setDt(1533157200);
//        dummyList.add(dummyObject20);


    }

}
