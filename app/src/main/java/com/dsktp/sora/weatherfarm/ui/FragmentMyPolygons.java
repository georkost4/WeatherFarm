package com.dsktp.sora.weatherfarm.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.adapters.PolygonAdapter;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;
import com.dsktp.sora.weatherfarm.data.network.RemoteRepository;
import com.dsktp.sora.weatherfarm.data.repository.AppDatabase;
import com.dsktp.sora.weatherfarm.data.repository.AppExecutors;
import com.dsktp.sora.weatherfarm.data.repository.PolygonDao;

import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class FragmentMyPolygons  extends Fragment implements PolygonAdapter.PolygonRowCallback,RemoteRepository.deliveryCallBack
{
    private View mInflatedView;
    private RecyclerView rvPolygons;
    private PolygonAdapter mAdapter;
    private RemoteRepository mRepo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mInflatedView = inflater.inflate(R.layout.fragment_my_polygons,container,false);

        //get the polygon list
        mRepo = RemoteRepository.getsInstance();
        mRepo.setmPolyListCallback(this);
        mRepo.getListOfPolygons();


        //inflate the list of the polygons
        rvPolygons = mInflatedView.findViewById(R.id.rv_polygon_list);
        mAdapter = new PolygonAdapter(getContext());
        rvPolygons.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPolygons.setAdapter(mAdapter);

        mAdapter.setmCallback(this);
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
    public void populateList(List<PolygonInfoPOJO> polygonList) {
        mAdapter.setPolygonList(polygonList);
    }

    @Override
    public void updateUI() {
        mAdapter.notifyDataSetChanged();
    }


}
