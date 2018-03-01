package com.fyp.ibtehaj.firestore;

import android.*;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static android.content.Context.LOCATION_SERVICE;

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
                  Snackbar.make(view,"Microphone: Coming Soon!",Snackbar.LENGTH_LONG)
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
                checkPermission();
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



    private void checkPermission() {
        Dexter.withActivity((Activity) context)
                .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        // permission is granted
                        Toast.makeText(context,"Got Permission",Toast.LENGTH_SHORT).show();
                        getLoc();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {


                        // check for permanent denial of permission
                        if (response.isPermanentlyDenied()) {
                            showSettingsDialog();
                        }else {
                            showExplanationDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    //Need Permission
    private void showExplanationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature.");
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }


    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }



    /* Code to get location data **/
    private void getLoc(){
        GPSTracker gps= new GPSTracker(context);
        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Log.i("key" , "Your Location is - \nLat: " + latitude + "\nLong: " + longitude);
            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            //For TimeStamp
            String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
//            Toast.makeText(context, currentDateTime, Toast.LENGTH_SHORT).show();
            Log.d("timeStamp" , currentDateTime);

            gps.stopUsingGPS();

        }else{
            // To ask the user to enable GPS.
            showSettingsAlert();
        }
    }


    // Asking user to enable GPS services
    private void showSettingsAlert(){

        LocationManager service = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);



        if(!enabled){

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);

            // Setting Dialog Title
            alertDialog.setTitle("GPS is required");

            // Setting Dialog Message
            alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog,int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                }
            });

            // on pressing cancel button
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            // Showing Alert Message
            alertDialog.show();}
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
