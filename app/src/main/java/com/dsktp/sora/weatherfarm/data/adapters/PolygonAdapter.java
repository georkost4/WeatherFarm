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

/**
 * This class contains the implementation of the RecyclerView adapter holding polygon
 * objects
 */
public class PolygonAdapter extends RecyclerView.Adapter<PolygonAdapter.PolygonViewHolder>
{
    private static final String DEBUG_TAG = "#" + "PolygonAdapter";
    private List<PolygonInfoPOJO> mPolygonList;
    private PolygonRowCallback mCallback;

    /**
     * Custom Callback to handle events on the polygon viewHolder
     */
    public interface PolygonRowCallback
    {
        void handleDeleteButtonClick(String polygonID);
        void handleForecastButtonClick(String polygonID);
    }

    /**
     * Default constructor
     */
    public PolygonAdapter()
    {
        //initialize the arrayList
        mPolygonList = new ArrayList<>();
    }

    /**
     * Setter method for the PolygonRowCallback member variable
     * @param mCallback The class/fragment that implements this callback
     */
    public void setCallback(PolygonRowCallback mCallback)
    {
        this.mCallback = mCallback;
    }

    /**
     * Setter method for the list holding the polygon objects
     * @param mPolygonList The polygon list<PolygonInfoPOJO>
     */
    public void setPolygonList(List<PolygonInfoPOJO> mPolygonList)
    {
        Log.d(DEBUG_TAG,"Setting the polygon list and notifying the adapter");
        this.mPolygonList = mPolygonList;
        notifyDataSetChanged(); // notify the adapter that the data has changed
    }



    @NonNull
        @Override
        public PolygonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
        {
            View inflatedItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_polygon_item_row,viewGroup,false);
            return new PolygonViewHolder(inflatedItemView);
        }

        @Override
        public void onBindViewHolder(@NonNull PolygonViewHolder polygonViewHolder, int i)
        {
            //get the current polygon object
            PolygonInfoPOJO pojoToBind = mPolygonList.get(i);
            //populate the ui
            polygonViewHolder.polygonName.setText(pojoToBind.getName());
        }

        @Override
        public int getItemCount() {
            return mPolygonList.size();
        }

    /**
     * Class implementing the ViewHolder object containing the polygon object along with some
     * buttons
     */
    class PolygonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView polygonName;
            private Button deleteBtn;
            private Button forecastButton;

            PolygonViewHolder(@NonNull View itemView)
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
                //call the proper call back method to handle the event properly
                //if the view id == button id then call the delete button callback
                //if the view id == forecast id then call the forecast button callback
                if(view.getId() == deleteBtn.getId())  mCallback.handleDeleteButtonClick(mPolygonList.get(getAdapterPosition()).getId());
                else if(view.getId() == forecastButton.getId()) mCallback.handleForecastButtonClick(mPolygonList.get(getAdapterPosition()).getId());
            }

        }
}

