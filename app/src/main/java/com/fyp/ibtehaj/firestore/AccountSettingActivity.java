package com.fyp.ibtehaj.firestore;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountSettingActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    TextView nameTextView , emailTextView , scoreTextView;
    EditText nameEditText, emailEditText, phoneEditText, addressEditText;
    ImageView profilePicture;
    Button updateAccount;
    private static final String TAG = "userInfo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);


        nameTextView = findViewById(R.id.nameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        scoreTextView = findViewById(R.id.scoreTextView);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);

        profilePicture = findViewById(R.id.profileImageView);
        updateAccount = findViewById(R.id.updateAccountBtn);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        setFields();

        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void setFields() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();


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



//                    DatabaseReference mMeetingRef = FirebaseDatabase.getInstance().getReference("SalesMan")
//                            .child(mAuth.getCurrentUser().getUid()).child("meeting");

                   /* mMeetingRef.addChildEventListener(new ChildEventListener() {
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
                    */

//

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

    private void log(String log){
        Log.i(TAG,log);
    }


}
