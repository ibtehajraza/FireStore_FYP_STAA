package com.fyp.ibtehaj.firestore;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ibtehaj on 5/3/2018.
 */

public class MedicalStoreAdapter extends RecyclerView.Adapter<MedicalStoreAdapter.MyViewHolder> {

    private Context context;
    private List<MedicalStore> medicalList;
    int count=1;
    int[] colours ;


    public MedicalStoreAdapter(Context context, List<MedicalStore> medicalList) {
        this.context = context;
        this.medicalList = medicalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reorder_guy_recyclerview_cell, parent , false);


        colours = new int[4];
        colours[1] = ContextCompat.getColor(context, R.color.colorReorderGuyBlue);
        colours[2] = ContextCompat.getColor(context, R.color.colorReorderGuyGreen);
        colours[3] = ContextCompat.getColor(context, R.color.colorReorderGuyYellow);

        return new MedicalStoreAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MedicalStoreAdapter.MyViewHolder holder, int position) {

        MedicalStore medicalStore = medicalList.get(position);

        holder.name.setText(medicalStore.getName());
//        holder.address.setText(Html.fromHtml("<b>Address: </b><em>"+medicalStore.getAddress()+"</em>"));
//        holder.contact.setText(Html.fromHtml("<b>Contact: </b><em>"+medicalStore.getContact()+"</em>"));

        if(count > 3 ){
            count =1;
        }

        holder.relativeLayout.setBackgroundColor(colours[count++]);

    }

    @Override
    public int getItemCount() {
        return medicalList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView contact;
        public TextView address;
        public RelativeLayout relativeLayout;


        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            contact = itemView.findViewById(R.id.contact);
            relativeLayout = itemView.findViewById(R.id.reorder_guy_cell);
        }
    }
}
