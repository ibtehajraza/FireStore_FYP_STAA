package com.fyp.ibtehaj.firestore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private static String TAG = "mainActivity";
    private String USER_ID ;
    private android.support.v7.widget.Toolbar toolbar;

    private RecyclerView recyclerView;
    private ScheduleAdapter adapter;
    private List<Schedule> scheduleList;

    private ShimmerFrameLayout mShimmerViewContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Setting The ToolBar
        toolbar = findViewById(R.id.main_page_toolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        TextView mTitle = toolbar.findViewById(R.id.toolBarTitle);
        mTitle.setText("SCHEDULE");
        mTitle.setAllCaps(true);
        mTitle.setPadding(1,1,1,1);

        // Getting Authentication Status From Firebase
        mAuth = FirebaseAuth.getInstance();
        USER_ID = null;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Setting up Some UI Improvements
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        recyclerView =  findViewById(R.id.recycler_view);

        scheduleList = new ArrayList<>();
        adapter = new ScheduleAdapter(this, scheduleList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_silde_from_right);
        recyclerView.setLayoutAnimation(controller);


        /* *
        * Trying the day bar on top of the application
        *
        *
        * */

//        LinearLayout linearLayout = findViewById(R.id.day_view_linear_layout);


        // Getting Data From FireBase
        //getData();
        /*
        * This method was initially created for test purposes but now i have to re-think it
        * */
        tempGetDatabase("monday");

    }

    private void getData(){

//        final TextView textView , textView1, textView2;
//        textView = findViewById(R.id.textView);
//        textView1 = findViewById(R.id.textView2);
//        textView2 = findViewById(R.id.textView3);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();

//        final String[] str = new String[1];

        if (currentUser != null) {

            DatabaseReference mRef = database.getReference("SalesMan")
                    .child(currentUser.getUid());

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Get Post object and use the values to update the UI
                    SalesMan salesMan = dataSnapshot.getValue(SalesMan.class);
                    assert salesMan != null;
                    Log.i(TAG,"Name: "+salesMan.getName()
                        +"\nEmail: "+salesMan.getEmail()
                        +"\nArea: "+salesMan.getArea()
                        +"\nScore: "+salesMan.getScore()
                        +"\nPhone: "+salesMan.getContact());
//                    log( dataSnapshot.getValue().toString());
//                    log( "Name: "+salesMan.getName()+"\nEmail: "+salesMan.getEmail());
                    log(salesMan.getName());

                    log(
                            "\nEmail: " + salesMan.getEmail() +
                            "\nArea: " + salesMan.getArea() +
                            "\nScore: " + salesMan.getScore() +
                            "\nPhone: " + salesMan.getContact()
                    );


                    DatabaseReference mMeetingRef = FirebaseDatabase.getInstance().getReference("SalesMan")
                            .child(mAuth.getCurrentUser().getUid()).child("meeting");

                    mMeetingRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            if (dataSnapshot.hasChildren()) {
                                Meeting meeting ;
                                meeting = dataSnapshot.getValue(Meeting.class);
//                                String value = dataSnapshot.getValue().toString();
                                assert meeting != null;
                                log(
                                        "Client Name: " + meeting.getClientName() +
                                        "\nTime Stamp: " + meeting.getTimeStamp() +
                                        "\nGPS: " + meeting.getGps()+
                                                "\n\n"
                                );
//                                log(meeting.getClientName());
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

//                    str[0] =dataSnapshot.getValue().toString();
                    /*try {
                        JSONObject jsonObject = new JSONObject(str[0]);
                        log(jsonObject.get("name").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            };
            mRef.addListenerForSingleValueEvent(postListener);
//            mRef.addValueEventListener(postListener);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences sharedPref = getSharedPreferences("status_string",Context.MODE_PRIVATE);
        String status = sharedPref.getString(getString(R.string.status_tag), "");


        Log.i("status"," ==> "+status);

        Log.i("status","in on create ==> ");

        mShimmerViewContainer.startShimmerAnimation();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            log("UserID: "+currentUser.getUid());
            USER_ID = currentUser.getUid();

            if(status.equals("ReOrderGuy")){
                Intent intent = new Intent(MainActivity.this , ReorderGuyMainActivity.class);
                startActivity(intent);
                finish();
            }

        }else{
            log("NOT SIGNED-IN");
            Intent intent = new Intent(MainActivity.this , StartPageActivity.class);
            startActivity(intent);
            finish();
        }
        //  updateUI(currentUser);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mShimmerViewContainer.stopShimmerAnimation();
    }

    private void logout(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut();
            log("currentUser: "+currentUser);
            Intent intent = new Intent(MainActivity.this , StartPageActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {

            case R.id.main_menu_logout_btn:
                logout();
                break;

            case R.id.main_menu_account_setting:
                Intent intent = new Intent(MainActivity.this, AccountSettingActivity.class);
                startActivity(intent);

                break;

            default:
                break;
        }


        /*if(item.getItemId() == R.id.main_menu_logout_btn){
         logout();
//            Toast.makeText(this,"Logout",Toast.LENGTH_LONG).show();
        }

        if(item.getItemId() == R.id.main_menu_account_setting){
            Toast.makeText(this,"Account Setting",Toast.LENGTH_LONG).show();
        }*/



        return true;
    }

    private void tempGetDatabase(String selectedDay) {
//        log("IN: " + scheduleList.size());
//        scheduleList = new ArrayList<>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        final int[] count = {0};
//        log(currentUser.getUid() + "Day "+ selectedDay);
//        Calendar calendar = Calendar.getInstance();
//        //dVJADHQ2rDflxtuNjAmY1hh7wGy2
//        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
//        log("******************"+dayLongName.toLowerCase()+"*************** ");

        final Query databaseReference = FirebaseDatabase.getInstance()
                .getReference("Schedule").child(currentUser != null ? currentUser.getUid() : "")
                .orderByChild("day")
                .equalTo(selectedDay);


        /* **/

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.hasChildren()){

                    Schedule schedule = dataSnapshot.getValue(Schedule.class);
                    if (schedule != null) {
//                        log(schedule.getDay()+" - "+schedule.getBrick()
//                                +" - "+schedule.getDocId()
//                                +" - "+schedule.getDocName()
//                                +" - "+schedule.getTimeStamp());
//                        log(schedule.getDay());
                        scheduleList.add(schedule);
                    }
//                    for (DataSnapshot postDataSnapshot: dataSnapshot.child("medicine").getChildren()){
//
//                        log("Key: "+postDataSnapshot.getKey()+" Value: "+ postDataSnapshot.getValue().toString());
//                    }
//                    count[0] ++;

//                    log("Count = "+count[0]+"  TotalChildren: "+ dataSnapshot.getChildrenCount());

//                    if(count[0] >= dataSnapshot.getChildrenCount()){
//                        log("Count: "+ count[0] + " TotalChildren: "+ dataSnapshot.getChildrenCount());
//                    }

                    log(scheduleList.size()+"  **SIZE");
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    recyclerView.scheduleLayoutAnimation();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /* **/
        /*
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                   log(dataSnapshot.getKey()+ dataSnapshot.getValue().toString());
//                HashMap hashMap = dataSnapshot.getValue(HashMap.class);
//                log(hashMap != null ? hashMap.values().toString() : "null");
                Schedule schedule = dataSnapshot.getValue(Schedule.class);
                if (schedule != null) {
                    log(schedule.getDay());
                }

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    log("Key: "+postSnapshot.getKey()+" Value: "+ postSnapshot.getValue().toString());
                    for(DataSnapshot medicineDataSnapShot : postSnapshot.child("medicine").getChildren()){
                        log("Key: "+medicineDataSnapShot.getKey()+" Value: "+ medicineDataSnapShot.getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }

    private void log(String log){
        Log.i(TAG,log);
    }

    public void getDay(View view) {
        clearStylesFromDayGrid();
        TextView counter = (TextView) view;
        counter.setTypeface(null, Typeface.BOLD_ITALIC);
        counter.setTextSize(18);
        String tappedDay = counter.getTag().toString();
//        Snackbar.make(view, counter.getTag().toString(), Snackbar.LENGTH_LONG)
//                .show();
//        scheduleList = new ArrayList<>();
        scheduleList.clear();
        adapter.notifyDataSetChanged();
//        log( scheduleList.size() + "" );
//        Log.i( "JJkk",adapter.getItemCount()+"");

        tempGetDatabase(tappedDay);
    }

    /* Clearing any styles from the text view */
    private void clearStylesFromDayGrid() {

        TextView mon = findViewById(R.id.monTextView);
        TextView tue = findViewById(R.id.tueTextView);
        TextView wed = findViewById(R.id.wedTextView);
        TextView thu = findViewById(R.id.thuTextView);
        TextView fri = findViewById(R.id.friTextView);
        TextView sat = findViewById(R.id.satTextView);


        mon.setTypeface(null, Typeface.NORMAL);
        tue.setTypeface(null, Typeface.NORMAL);
        wed.setTypeface(null, Typeface.NORMAL);
        thu.setTypeface(null, Typeface.NORMAL);
        fri.setTypeface(null, Typeface.NORMAL);
        sat.setTypeface(null, Typeface.NORMAL);

        mon.setTextSize(15);
        tue.setTextSize(15);
        wed.setTextSize(15);
        thu.setTextSize(15);
        fri.setTextSize(15);
        sat.setTextSize(15);

    }

    /* End of the main class**/
}
