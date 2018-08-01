package com.dsktp.sora.weatherfarm.data.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dsktp.sora.weatherfarm.R;
import com.dsktp.sora.weatherfarm.data.model.Polygons.PolygonInfoPOJO;

import java.util.ArrayList;
import java.util.List;

/**
 * This file created by Georgios Kostogloudis
 * and was last modified on 26/7/2018.
 * The name of the project is WeatherFarm and it was created as part of
 * UDACITY ND programm.
 */
public class PolygonAdapter extends RecyclerView.Adapter<PolygonAdapter.PolygonViewHolder>
{
    private static final String DEBUG_TAG = "#" + "PolygonAdapter";
    private List<PolygonInfoPOJO> mPolygonList;
    private PolygonRowCallback mCallback;
    private Context mContext;


    public interface PolygonRowCallback
    {
        void handleDeleteButtonClick(String polygonID);
        void handleForecastButtonClick(String polygonID);
    }

    public PolygonAdapter(Context context)
    {
        mContext = context;
        mPolygonList = new ArrayList<>();
    }

    public void setmCallback(PolygonRowCallback mCallback)
    {
        this.mCallback = mCallback;
    }

    public void setPolygonList(List<PolygonInfoPOJO> mPolygonList)
    {
        Log.d(DEBUG_TAG,"Setting the polygon list and notifying the adapter");
        this.mPolygonList = mPolygonList;
        notifyDataSetChanged();
    }



    @NonNull
        @Override
        public PolygonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View inflatedItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_polygon_item_row,viewGroup,false);
            PolygonViewHolder polygonViewHolder = new PolygonViewHolder(inflatedItemView);
            return polygonViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull PolygonViewHolder polygonViewHolder, int i)
        {
            PolygonInfoPOJO pojoToBind = mPolygonList.get(i);
            polygonViewHolder.polygonName.setText(pojoToBind.getName());
        }

        @Override
        public int getItemCount() {
            return mPolygonList.size();
        }

        class PolygonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView polygonName;
            private Button deleteBtn;
            private Button forecastButton;

            public PolygonViewHolder(@NonNull View itemView)
            {
                super(itemView);
                deleteBtn = itemView.findViewById(R.id.polygon_row_btn);
                polygonName = itemView.findViewById(R.id.polygon_row_name_value);
                forecastButton = itemView.findViewById(R.id.btn_row_forecast);
                deleteBtn.setOnClickListener(this);
                forecastButton.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if(view.getId() == deleteBtn.getId())  mCallback.handleDeleteButtonClick(mPolygonList.get(getAdapterPosition()).getId());
                else if(view.getId() == forecastButton.getId()) mCallback.handleForecastButtonClick(mPolygonList.get(getAdapterPosition()).getId());
            }

        }
}

