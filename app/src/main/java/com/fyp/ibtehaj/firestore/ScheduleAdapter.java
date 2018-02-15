package com.fyp.ibtehaj.firestore;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

/**
 * Created by ibtehaj on 2/14/2018.
 */


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.MyViewHolder> {

    private int[] drawableLine = {R.drawable.container_side_line_red,
            R.drawable.container_side_line_blue,
            R.drawable.container_side_line_green,
            R.drawable.container_side_line_pink};
    private Context context;
    private List<Schedule> scheduleList;


    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recyclerview_cell, parent , false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Schedule schedule = scheduleList.get(position);

        holder.title.setText(schedule.getDocName());
        holder.specialization.setText(schedule.getDocSpecialization());

        holder.area.setText(Html.fromHtml("<b>Area: </b><em>"+schedule.getDocArea()+"</em>"));
        holder.contact.setText(Html.fromHtml("<b>Contact: </b><em>"+schedule.getDocContact()+"</em>"));

        Random r = new Random();
        int n=r.nextInt(4);


        holder.sideBar.setBackgroundResource(drawableLine[n]);


//        holder..setBackgroundResource(R.drawable.snowball_person);
          holder.microphone.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  Snackbar.make(view,"Microphone",Snackbar.LENGTH_LONG)
                          .setAction("Action",null)
                          .show();
              }
          });

        holder.gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"GPS",Snackbar.LENGTH_LONG)
                        .setAction("Action",null)
                        .show();
            }
        });

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//                showPopUp(holder.overflow);
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, specialization, area, contact;
        public ImageView sideBar,
                overflow, gps, microphone;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            specialization = itemView.findViewById(R.id.specialization);
            area = itemView.findViewById(R.id.area);
            contact = itemView.findViewById(R.id.contact);

            sideBar = itemView.findViewById(R.id.side_bar);
            overflow = itemView.findViewById(R.id.overflow);
            gps = itemView.findViewById(R.id.gps);
            microphone = itemView.findViewById(R.id.microphone);
        }
    }
}
