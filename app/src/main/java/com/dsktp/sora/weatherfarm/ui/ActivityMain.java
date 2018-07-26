package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 20/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class ActivityMain extends AppCompatActivity implements RemoteRepository.onSucces
{
    private Fragment mapFragment , PolygonFragment;
    private FragmentManager mFragmentManager;
    private RemoteRepository mRepo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRepo = RemoteRepository.getsInstance();
        mRepo.setmCallback(this);
        mFragmentManager = getSupportFragmentManager();


    }

    @Override
    public void updateUI() {
        getSupportFragmentManager().beginTransaction().remove(mapFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit();
    }



    public void onPolygonsClick(View view)
    {
        PolygonFragment = new FragmentMyPolygons();
        mFragmentManager.beginTransaction().add(R.id.fragment_container,PolygonFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack("").commit();
    }

    public void onMapClick(View view)
    {
        mapFragment = new FragmentMap();
        mFragmentManager.beginTransaction().replace(R.id.fragment_container,mapFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack("").commit();
    }
}
