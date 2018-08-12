package com.dsktp.sora.weatherfarm.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

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

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */

/**
 * This class contains methods that create and interact with the polygons.
 */
public class FragmentMyPolygons  extends Fragment implements PolygonAdapter.PolygonRowCallback,RemoteRepository.deliveryCallBack
{
    private static final String DEBUG_TAG = "#FragmentMyPolygons";
    private View mInflatedView;
    private PolygonAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_my_polygons,container,false);

        //get the polygon list only if the polygon list hasn't been synced
        //and we have internet
        //otherwise query the local database
        if (!AppUtils.hasThePolygonListSynced(getContext())) {
            if (AppUtils.getNetworkState(getContext()))
            {
                //we have internet
                //start the loading from the server
                //show the loading indicator
                mInflatedView.findViewById(R.id.polygon_loading_indicator).setVisibility(View.VISIBLE);
                RemoteRepository.getsInstance().getListOfPolygons(mInflatedView.getContext());
            } else
            {
                //we dont have internet to fetch the data
                //show the refresh button until the user connect to internet
                ImageButton refresh = getActivity().findViewById(R.id.toolbar_refresh_button);
                refresh.setVisibility(View.VISIBLE); //show the refresh button
                //set the refresh button listener
                refresh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtils.getNetworkState(getContext())) {
                            //update the polygon list
                            Toast.makeText(getContext(), R.string.updating_list_string, Toast.LENGTH_SHORT).show();
                            RemoteRepository.getsInstance().getListOfPolygons(mInflatedView.getContext());
                        } else {
                            //show error message
                            Toast.makeText(getContext(), R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }


        //set up toolbar
        ((ActivityMain)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((ActivityMain)getActivity()).getSupportActionBar().setTitle(R.string.my_polygons_toolbar_title);

        //set up the recyclerView
        setUpRecyclerView();


        ViewModelProviders.of(this).get(PolygonViewModel.class).getPolygonList().observe(getActivity(), new Observer<List<PolygonInfoPOJO>>() {
            @Override
            public void onChanged(@Nullable List<PolygonInfoPOJO> polygonInfoPOJOS) {
                Log.d(DEBUG_TAG,"Live data to the rescue...");
                if(polygonInfoPOJOS.size() == 0) Toast.makeText(getContext(), R.string.add_new_polygon_string,Toast.LENGTH_LONG).show();
                mAdapter.setPolygonList(polygonInfoPOJOS);
                mInflatedView.findViewById(R.id.polygon_loading_indicator).setVisibility(View.GONE);
            }
        });




        return mInflatedView;
    }

    /**
     * This method set's up the recyclerView by
     * getting a reference to the view , creating a
     * new adapter object , setting it's layout manager
     * and data adapter.
     */
    private void setUpRecyclerView() {
        //inflate the list of the polygons
        RecyclerView rvPolygons = mInflatedView.findViewById(R.id.rv_polygon_list);
        mAdapter = new PolygonAdapter();
        rvPolygons.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPolygons.setAdapter(mAdapter);

        mAdapter.setCallback(this);
    }

    /**
     * This method is triggered when the user click on
     * the "Delete" button on the polygon list. It sends
     * a request to delete the polygon from the local and
     * remote server
     * @param polygonID The polygonID to delete
     */
    @Override
    public void handleDeleteButtonClick(final String polygonID)
    {
        //delete the clicked polygon from the db and the remote server
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


    /**
     * This method is triggered when the user click on
     * the "forecast" button on the polygon list. It sends
     * a request to fetch the weather forecast data for that
     * specific polygon
     * @param polygonID The polygonID to fetch the data
     */
    @Override
    public void handleForecastButtonClick(String polygonID)
    {
        //get forecast data for the clicked polygon
        if(AppUtils.getNetworkState(getContext())) {
            RemoteRepository.getsInstance().setmPolyListCallback(this);
            RemoteRepository.getsInstance().getForecastPolygon(polygonID, getContext());
        }
        else
        {
            //inform the user about the internet connection
            Toast.makeText(getContext(), R.string.no_internet_connection,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is triggered when the Remote Repository has a successful
     * response from the server containing the polygon List.
     * @param polygonList List<WeatherForecastPOJO> containing the user-defined polygons
     */
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
        //hide the toolbar button "Polygons"
        getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        //show the toolbar button "Polygons"
        getActivity().findViewById(R.id.btn_my_polygons).setVisibility(View.VISIBLE);
    }

    @Override
    public void onStop() {
        super.onStop();
        //hide the the "Refresh" icon from the toolbar
        getActivity().findViewById(R.id.toolbar_refresh_button).setVisibility(View.GONE);
    }
}
