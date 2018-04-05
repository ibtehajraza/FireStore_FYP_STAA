package com.fyp.ibtehaj.firestore;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    private MediaRecorder mediaRecorder ;
    private String AudioSavePathInDevice = null;
    private Random random ;
    private String RandomAudioFileName = "AB4COP";
    private MediaPlayer mediaPlayer ;
    private View v;
    private  boolean recStatus = false;


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private StorageReference mStorageRef;

    private String docName,memoName;


    public ScheduleAdapter(Context context, List<Schedule> scheduleList) {
        this.context = context;
        this.scheduleList = scheduleList;
        random = new Random();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recyclerview_cell, parent , false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Schedule schedule = scheduleList.get(position);

        holder.title.setText(schedule.getDocName());
        holder.specialization.setText(schedule.getDocSpecialization());

        holder.area.setText(Html.fromHtml("<b>Area: </b><em>"+schedule.getDocArea()+"</em>"));
        holder.contact.setText(Html.fromHtml("<b>Contact: </b><em>"+schedule.getDocContact()+"</em>"));

        Random r = new Random();
        int n=r.nextInt(4);

        docName = schedule.getDocName();
        holder.sideBar.setBackgroundResource(drawableLine[n]);


//        holder..setBackgroundResource(R.drawable.snowball_person);
          holder.microphone.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  if (checkMultiplePermission()){
                      memoName = scheduleList.get(position).getDocName();
                      RECORD_MEMO(holder);
                  }else {
                      Snackbar.make(view,"Need Permissions For Farther Actions",Snackbar.LENGTH_LONG).show();
                  }
              v = view;
              }
          });

        holder.gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docName = scheduleList.get(position).getDocName();
//                Snackbar.make(view,"Position: "+position+" Doc: "+docName,Snackbar.LENGTH_LONG).show();

                if(checkMultiplePermission()){
                    getLoc();
                }else {
                    Snackbar.make(view,"Need Permissions For Farther Actions",Snackbar.LENGTH_LONG).show();
                }
                v = view;
            }
        });

        holder.statusText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(holder.statusText.getText().toString().toLowerCase(), " pending")) {
                    holder.statusText.setText(" Done");
                    holder.statusText.setTextColor(ContextCompat.getColor(context, R.color.colorSuccessfullText));

                    holder.statusText.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.success, 0, 0, 0);
                    holder.statusText.setPadding(5, 0, 5, 0);

                    /* Making a dramatic effect... I Know Right... **/
                    holder.gps.setClickable(false);
                    holder.gps.setAlpha((float) 0.5);

                    holder.microphone.setClickable(false);
                    holder.microphone.setAlpha((float) 0.5);

                    holder.title.setAlpha((float) 0.5);
                    holder.specialization.setAlpha((float) 0.5);
                    holder.area.setAlpha((float) 0.5);
                    holder.contact.setAlpha((float) 0.5);
                }
            }
        });

//        holder.overflow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
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


    private void RECORD_MEMO(MyViewHolder holder) {

        if(!recStatus)
        {
            recStatus = true;
            holder.microphone.setImageResource(R.mipmap.stop);
            Log.i("RecordingStuff","onStartClick");
            AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CreateRandomAudioFileName(5) + "AudioRecording.mp3";
            MediaRecorderReady();

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
//                holder.microphone.setBackgroundResource(R.drawable.microphone);
            holder.microphone.setImageResource(R.mipmap.microphone);
            recStatus=false;
            Log.i("RecordingStuff","onMicrophoneClick");
            mediaRecorder.stop();

            Log.i("RecordingStuff","Path is "+AudioSavePathInDevice);
            uploadFile(AudioSavePathInDevice);
        }

    }


    /* Checking for multiple permissions **/
    private boolean checkMultiplePermission() {
        final boolean[] status = {false};
        Dexter.withActivity((Activity) context)
                .withPermissions(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.RECORD_AUDIO,
                        android.Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE

                )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            Toast.makeText(context,"Got Permission",Toast.LENGTH_SHORT).show();
//                            getLoc();
                            status[0] = true;
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            // check for permanent denial of permission
                            showSettingsDialog();

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(context, "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
        return status[0];
    }


    private void MediaRecorderReady(){

        mediaRecorder=new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

//        Log.i("TAG",AudioSavePathInDevice);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);

    }


    @NonNull
    private String CreateRandomAudioFileName(int string){

        StringBuilder stringBuilder = new StringBuilder( string );

        int i = 0 ;
        while(i < string ) {

            stringBuilder.append(RandomAudioFileName.charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();

    }


    /* Trile code for audio file upload */
    private void uploadFile(String path){

        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();
        String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
        memoName += "-"+mAuth.getCurrentUser().getDisplayName()+"-"+ currentDateTime;
        Log.i("RecordingMemo","MEMO-NAME: "+memoName);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        Uri file = Uri.fromFile(new File(path));
        StorageReference riversRef = mStorageRef.child("VoiceMemo/"+userId+"/"+memoName+".mp3");

        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Log.i("RecordingStuff","DownloadURL: "+downloadUrl);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context,
                            "Memo Upload un-successful Try Again with stable internet connection",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
    }


    /* Code to get location data **/
    private void getLoc(){
        GPSTracker gps= new GPSTracker(context);
        // check if GPS enabled
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Log.i("key" , "Your Location is - \nLat: " + latitude + "\nLong: " + longitude);
//            Toast.makeText(context, "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            //For TimeStamp
            String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());
            Log.d("timeStamp" , currentDateTime);

            if(latitude != 0 && longitude != 0) {
                mAuth = FirebaseAuth.getInstance();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                String UserId = mAuth.getCurrentUser().getUid();

                Meeting newMeeting = new Meeting(docName, currentDateTime, latitude + ", " + longitude);
                mDatabase.child("SalesMan").child(UserId).child("meeting").push().setValue(newMeeting)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Snackbar.make(v,"Successfully Recorded",Snackbar.LENGTH_LONG)
                                        .show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(v,"Un-Successful! TRY AGAIN",Snackbar.LENGTH_LONG)
                                .show();
                    }
                });

                gps.stopUsingGPS();
            }
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
        public TextView title, specialization, area, contact, statusText;
        public ImageView sideBar,
                overflow, gps, microphone;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            specialization = itemView.findViewById(R.id.specialization);
            area = itemView.findViewById(R.id.area);
            contact = itemView.findViewById(R.id.contact);
            statusText = itemView.findViewById(R.id.statusText);

            sideBar = itemView.findViewById(R.id.side_bar);
            overflow = itemView.findViewById(R.id.overflow);
            gps = itemView.findViewById(R.id.gps);
            microphone = itemView.findViewById(R.id.microphone);
        }
    }
}
