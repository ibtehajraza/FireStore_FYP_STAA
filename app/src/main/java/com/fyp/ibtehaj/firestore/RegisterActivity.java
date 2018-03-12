package com.fyp.ibtehaj.firestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private String TAG = "KEY_CHAN";
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private FirebaseAuth mAuth;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        progress = new ProgressDialog(this);
        progress.setTitle("Processing");
        progress.setMessage("Creating Profile");
        progress.setCancelable(false);

        mDisplayName = findViewById(R.id.reg_display_name);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        Button mSignUpBtn = findViewById(R.id.reg_sign_up_btn);
        TextView reg_signIn_txt = findViewById(R.id.reg_signin_str);

        /* FireBase Auth**/
        mAuth = FirebaseAuth.getInstance();
        reg_signIn_txt.setText(Html.fromHtml("Already have an account? <b>Sign In</b>"));


        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            progress.show();

                Log.i(TAG , String.valueOf(validateCheck()));
                registerUser();
            }
        });
    }



    /* Check If User is already logged in*/
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent login_intent = new Intent(RegisterActivity.this , MainActivity.class);
            startActivity(login_intent);
            finish();
        }
    }


    private void registerUser(){
        if(validateCheck()){
            Log.i(TAG,"USER GETS VALIDATED");

            String email = mEmail.getEditText().getText().toString();
            String password = mPassword.getEditText().getText().toString();
            String displayName = mDisplayName.getEditText().getText().toString();

            logIn(email,password,displayName);
        }else{
            progress.dismiss();
            Log.i(TAG , "USER IS NOT VALIDATED");
        }
    }


    /* LogIn logic is implemented here **/
    private void logIn(final String  email, String password, final String displayName ){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(displayName).build();

                            if (user != null) {
                                user.updateProfile(profileChangeRequest)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    /* Updating Sales man entries**/
                                                    updateDataBase(email , displayName);

                                                    progress.dismiss();
                                                    Intent login_successful_intent = new Intent (RegisterActivity.this, MainActivity.class);
                                                    login_successful_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(login_successful_intent);
                                                    finish();

                                                    Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });
                            }

                            // updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                        }

                        // ...
                    }
                });
    }


    /* To Create Basic Structure for sales rap database **/
    private void updateDataBase(String email, String displayName) {
        String userId = mAuth.getCurrentUser().getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase;
//        mDatabase = database.getReference("sales man");
        mDatabase = FirebaseDatabase.getInstance().getReference();

        SalesMan salesMan = new SalesMan();
        salesMan.setName(displayName);
        salesMan.setEmail(email);
        salesMan.setArea("TEMP_Gulshan Iqbal, Karachi");
        salesMan.setContact("090078601");
        salesMan.setScore("00");

        HashMap<String , String> meetingHash = new HashMap<>();
        meetingHash.put("timestamp", Calendar.getInstance().getTime().toString());
        meetingHash.put("client name", "First Entry");
        meetingHash.put("gps", "00.0000, 00.0000");


//        mRef.push().setValue(salesMan);
        mDatabase.child("SalesMan").child(userId).setValue(salesMan);
        mDatabase.child("SalesMan").child(userId).child("meeting").push().setValue(meetingHash)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this , "You have successfully Logged in",Toast.LENGTH_SHORT)
                            .show();
                }else{
                    Toast.makeText(getApplicationContext() , "You have successfully Logged in",Toast.LENGTH_SHORT)
                            .show();
                    Log.i(TAG , "Task is not successful");
                }
            }
        });

//        mRef = database.getReference("sales man//"+userId+"//meeting//");
//        mRef.push().setValue("Temp").addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(RegisterActivity.this , "You have successfully Logged in",Toast.LENGTH_SHORT)
//                            .show();
//                }else{
//                    Log.i(TAG , "Task is not successful");
//                }
//            }
//        });


    }



    private boolean validateCheck(){

        boolean valid = true;
        String email = mEmail.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();
        String displayName = mDisplayName.getEditText().getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("enter a valid email address");
            valid = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPassword.setError("between 4 and 10 alphanumeric characters");
//            mPassword.setHint("Provide Password");
            valid = false;
        } else {
            mPassword.setError(null);
        }


        if (displayName.isEmpty() || displayName.length() < 2 || displayName.length() > 20) {
            mDisplayName.setError("name must be greater then 2 characters");
//            mPassword.setHint("Provide Password");

            valid = false;
        } else {
            mDisplayName.setError(null);
        }

        Log.i(TAG , "Display Name: "+displayName+"\nEmail: "+email+"\nPassword: "+password);
        Toast.makeText(RegisterActivity.this,"Display Name: "+displayName+"\nEmail: "+email+"\nPassword: "+password,Toast.LENGTH_LONG).show();
        return valid;
    }


    public void SignInBtn_Click(View view) {
        Log.i(TAG,"SignUp_ONCLICK");

        Intent login_intent = new Intent(RegisterActivity.this , LoginActivity.class);
        startActivity(login_intent);
        finish();
    }
}
