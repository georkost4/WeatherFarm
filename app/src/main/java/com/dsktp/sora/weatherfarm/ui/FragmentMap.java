package com.dsktp.sora.weatherfarm.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.utils.AreaUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class FragmentMap extends Fragment implements OnMapReadyCallback,RemoteRepository.onFailure{

    private  String DEBUG_TAG ="#" + getClass().getSimpleName() ;
    private GoogleMap mMap;
    private static  ProgressBar sProgressBar;
    private View mInflatedView;
    private final List<Marker> markerList = new ArrayList<>();
    private final List<LatLng> pointList = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         mInflatedView = inflater.inflate(R.layout.fragment_map,container,false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.map, fragment);
        transaction.commit();

        fragment.getMapAsync(this);

        sProgressBar = mInflatedView.findViewById(R.id.pb_loading_map_activity);
        RemoteRepository.getsInstance().setmMapCallback(this);
        return mInflatedView;

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
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);



        // Add a marker in Rizia and move the camera
        LatLng riziaPosition = new LatLng(41.621459, 26.424926); //todo replace this lat/long with current position
        Marker starterMark = mMap.addMarker(new MarkerOptions().position(riziaPosition));
        starterMark.setVisible(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(riziaPosition,15.0f));

        mInflatedView.findViewById(R.id.toogle_map_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)  mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                else mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
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
                        double polygonArea = AreaUtils.squareMetersToHectares(SphericalUtil.computeArea(pointList));
                        Toast.makeText(mInflatedView.getContext(),"Total area = " + String.format("%.2f",polygonArea) + " ha",Toast.LENGTH_SHORT).show();
                        if(polygonArea<2000) // todo replace this with the actual remaining area of the user
                        {
                            PolygonOptions polygonOptions = new PolygonOptions();
                            //draw the polygon on the map
                            for(Marker point:markerList)
                            {
                                polygonOptions.add(point.getPosition());
                                Button btn = mInflatedView.findViewById(R.id.sendBtn);
                                btn.setVisibility(View.VISIBLE);

                                btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        RemoteRepository remoteRepository = RemoteRepository.getsInstance();
                                        remoteRepository.sendPolygon(pointList,"Test24",mInflatedView.getContext());
                                        sProgressBar.setVisibility(View.VISIBLE);
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


    public static void hideLoadingIndicator()
    {
        sProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void updateMapUI() {
        Toast.makeText(mInflatedView.getContext(),"Try making a rectangular",Toast.LENGTH_LONG).show();
        markerList.clear();
        mMap.clear();
        pointList.clear();
        sProgressBar.setVisibility(View.GONE);
    }
}
