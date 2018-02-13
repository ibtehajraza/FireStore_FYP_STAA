package com.fyp.ibtehaj.firestore;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private static String TAG = "KEY_CHAN";
    private String USER_ID ;
    private android.support.v7.widget.Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        toolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("STAA");


        mAuth = FirebaseAuth.getInstance();
        USER_ID = null;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        getData();
        tempGetDatabase();

    }

    private void getData(){

        final TextView textView , textView1, textView2;
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView2);
        textView2 = findViewById(R.id.textView3);

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
                    textView.setText(salesMan.getName());

                    textView1.setText(
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
                                textView2.append(
                                        "Client Name: " + meeting.getClientName() +
                                        "\nTime Stamp: " + meeting.getTimeStamp() +
                                        "\nGPS: " + meeting.getGps()+
                                                "\n\n"
                                );
                                log(meeting.getClientName());
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
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            log("UserID: "+currentUser.getUid());
            USER_ID = currentUser.getUid();

        }else{
            log("NOT SIGNED-IN");
            Intent intent = new Intent(MainActivity.this , StartPageActivity.class);
            startActivity(intent);
            finish();
        }
        //  updateUI(currentUser);
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
                Toast.makeText(this,"Account Setting",Toast.LENGTH_LONG).show();
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

    private void tempGetDatabase(){

//        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
//                .getReference("Schedule").child("dVJADHQ2rDflxtuNjAmY1hh7wGy2");

        Calendar calendar = Calendar.getInstance();
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        log("******************"+dayLongName.toLowerCase()+"***************");
        final Query databaseReference = FirebaseDatabase.getInstance()
                .getReference("Schedule").child("dVJADHQ2rDflxtuNjAmY1hh7wGy2")
                .orderByChild("day")
                .equalTo("monday");


        /* **/

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.hasChildren()){
                    Schedule schedule = dataSnapshot.getValue(Schedule.class);
                    if (schedule != null) {
                        log(schedule.getDay()+" - "+schedule.getBrick()
                                +" - "+schedule.getDocId()
                                +" - "+schedule.getDocName()
                                +" - "+schedule.getTimeStamp());
                    }
                    for (DataSnapshot postDataSnapshot: dataSnapshot.child("medicine").getChildren()){

                        log("Key: "+postDataSnapshot.getKey()+" Value: "+ postDataSnapshot.getValue().toString());
                    }
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

    /* End of the main class**/
}
