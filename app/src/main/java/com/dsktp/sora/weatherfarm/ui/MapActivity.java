package com.dsktp.sora.weatherfarm.ui;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.utils.Utils;
import com.google.android.gms.common.util.MapUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;
import com.google.maps.android.data.Geometry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.google.maps.android.SphericalUtil.computeArea;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private  String DEBUG_TAG ="#" + getClass().getSimpleName() ;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Rizia, Greece.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final int i = 0;
        final int[] mapType = new int[5];
        mapType[0] = GoogleMap.MAP_TYPE_NONE;
        mapType[1] = GoogleMap.MAP_TYPE_NORMAL;
        mapType[2] = GoogleMap.MAP_TYPE_SATELLITE;
        mapType[3] = GoogleMap.MAP_TYPE_TERRAIN;
        mapType[4] = GoogleMap.MAP_TYPE_HYBRID;


        final List<Marker> markerList = new ArrayList<>();
        final List<LatLng> pointList = new ArrayList<>();

        // Add a marker in Rizia and move the camera
        LatLng riziaPosition = new LatLng(41.621459, 26.424926);
        Marker starterMark = mMap.addMarker(new MarkerOptions().position(riziaPosition));
        starterMark.setVisible(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(riziaPosition,15.0f));

        findViewById(R.id.toogle_map_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(mapType[2]);
                for(Marker point : markerList) point.remove();
                markerList.clear();
                pointList.clear();
            }
        });


        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onMapLongClick(LatLng latLng) {
                if(markerList.size()<4)
                {
                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
                    markerList.add(marker);
                    pointList.add(latLng);
                    if(markerList.size() == 4) // the polygon will be shaped from 4 points
                    {
                        double polygonArea = Utils.Area.squareMetersToHectares(SphericalUtil.computeArea(pointList));
                        Toast.makeText(getBaseContext(),"Total area = " + String.format("%.2f",polygonArea) + " ha",Toast.LENGTH_SHORT).show();
                        if(polygonArea<2000) // todo replace this with the actual remaining area of the user
                        {
                            PolygonOptions polygonOptions = new PolygonOptions();
                            //draw the polygon on the map
                            for(Marker point:markerList)
                            {
                                polygonOptions.add(point.getPosition());
                                FloatingActionButton btn = findViewById(R.id.sendBtn);
                                btn.setVisibility(View.VISIBLE);

                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        RemoteRepository remoteRepository = new RemoteRepository();
                                        remoteRepository.sendPolygon(pointList,"Test24");
                                    }
                                });
                            }
                            mMap.addPolygon(polygonOptions);
                            Log.d(DEBUG_TAG,"Number of points = " + polygonOptions.getPoints().size() );
                        }
                        else
                        {
                            // show error area too big
                        }
                    }
                }
            }
        });


    }
}
