package com.dsktp.sora.weatherfarm;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

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
        // Add a marker in Sydney and move the camera
        LatLng riziaPosition = new LatLng(41.621459, 26.424926);
        mMap.addMarker(new MarkerOptions().position(riziaPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(riziaPosition,15.0f));

        findViewById(R.id.toogle_map_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.setMapType(mapType[2]);
            }
        });


    }
}
