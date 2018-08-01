package com.dsktp.sora.weatherfarm.ui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.adapters.PolygonAdapter;
import com.dsktp.sora.weatherfarm.data.model.Forecast.WeatherForecastPOJO;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.AppExecutors;
import com.dsktp.sora.weatherfarm.data.repository.PolygonDao;
import com.dsktp.sora.weatherfarm.data.viewmodel.PolygonViewModel;
import com.dsktp.sora.weatherfarm.utils.AppUtils;
import com.dsktp.sora.weatherfarm.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class FragmentMyPolygons  extends Fragment implements PolygonAdapter.PolygonRowCallback,RemoteRepository.deliveryCallBack
{
    private static final String DEBUG_TAG = "#FragmentMyPolygons";
    private View mInflatedView;
    private RecyclerView rvPolygons;
    private PolygonAdapter mAdapter;
    private RemoteRepository mRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_my_polygons,container,false);


        //get the polygon list only if the polygon list hasn't been synced
        //otherwise query the local database
        if(!AppUtils.hasThePolygonListSynced(getContext()))
        {
            mRepo = RemoteRepository.getsInstance();
            mRepo.getListOfPolygons(mInflatedView.getContext());
        }

        ((ActivityMain)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActivityMain)getActivity()).getSupportActionBar().setTitle("My Polygons");

        //inflate the list of the polygons
        rvPolygons = mInflatedView.findViewById(R.id.rv_polygon_list);
        mAdapter = new PolygonAdapter(getContext());
        rvPolygons.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPolygons.setAdapter(mAdapter);

        mAdapter.setmCallback(this);

        ViewModelProviders.of(this).get(PolygonViewModel.class).getPolygonList().observe(getViewLifecycleOwner(), new Observer<List<PolygonInfoPOJO>>() {
            @Override
            public void onChanged(@Nullable List<PolygonInfoPOJO> polygonInfoPOJOS) {
                Log.d(DEBUG_TAG,"Live data to the rescue...");
                mAdapter.setPolygonList(polygonInfoPOJOS);
            }
        });




        return mInflatedView;
    }

    @Override
    public void handleDeleteButtonClick(final String polygonID) {
        RemoteRepository remoteRepository = RemoteRepository.getsInstance();
        remoteRepository.removePolygon(polygonID,getContext());
        final PolygonDao dao = AppDatabase.getsDbInstance(getContext()).polygonDao();
        AppExecutors.getInstance().getRoomIO().execute(new Runnable() {
            @Override
            public void run() {
                dao.deletePolygon(polygonID);
            }
        });
    }

    @Override
    public void handleForecastButtonClick(String polygonID) {
        RemoteRepository.getsInstance().setmPolyListCallback(this);
        RemoteRepository.getsInstance().getForecastPolygon(polygonID,getContext());
    }


    @Override
    public void populateList(List<WeatherForecastPOJO> polygonList)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.DETAILED_FORECAST_ARGUMENT_KEY, polygonList.get(0));
        if(getActivity().getSupportFragmentManager().findFragmentByTag(Constants.DETAILED_FORECAST_FRAGMENT_TAG) == null)
        {
            FragmentDetailedWeatherInfo fragmentDetailedWeatherInfo = new FragmentDetailedWeatherInfo();
            fragmentDetailedWeatherInfo.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragmentDetailedWeatherInfo).addToBackStack("").commit();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
    }
}
