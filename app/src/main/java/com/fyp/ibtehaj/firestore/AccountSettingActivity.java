package com.fyp.ibtehaj.firestore;

import android.support.design.widget.Snackbar;
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

        updateAccount.setEnabled(false);
        updateAccount.setAlpha((float) 0.5);
        setFields();


        updateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                assert currentUser != null;
                DatabaseReference mRef = database.getReference("SalesMan")
                        .child(currentUser.getUid());

                SalesMan updatedSalesman = new SalesMan();
                updatedSalesman.setName(nameEditText.getText().toString());
                updatedSalesman.setArea(addressEditText.getText().toString());
                updatedSalesman.setEmail(emailEditText.getText().toString());
                updatedSalesman.setContact(phoneEditText.getText().toString());
                mRef.setValue(updatedSalesman);
                Snackbar.make(view, "Action Successful", Snackbar.LENGTH_LONG).show();

//                updateAccount.setEnabled(false);
//                updateAccount.setAlpha((float) 0.5);
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
                    Log.i(TAG, "Name1: " + salesMan.getName()
                            + "\nEmail1: " + salesMan.getEmail()
                            + "\nArea1: " + salesMan.getArea()
                            + "\nScore1: " + salesMan.getScore()
                            + "\nPhone1: " + salesMan.getContact());

                    log(salesMan.getName());
/* Setting the header for user profile**/
                    nameTextView.setText(salesMan.getName());
                    emailTextView.setText(salesMan.getEmail());
                    scoreTextView.setText(salesMan.getScore());

                    /* Setting the Text Fields **/
                    nameEditText.setText(salesMan.getName());
                    emailEditText.setText(salesMan.getEmail());
                    phoneEditText.setText(salesMan.getContact());
                    addressEditText.setText(salesMan.getArea());


//                    log(
//                            "\nEmail: " + salesMan.getEmail() +
//                                    "\nArea: " + salesMan.getArea() +
//                                    "\nScore: " + salesMan.getScore() +
//                                    "\nPhone: " + salesMan.getContact()
//                    );


                    updateAccount.setEnabled(true);
                    updateAccount.setAlpha(1);


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
