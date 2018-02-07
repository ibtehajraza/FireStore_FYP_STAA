package com.fyp.ibtehaj.firestore;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    private String TAG = "KEY_CHAN";
    private TextInputLayout mPassword;
    private TextInputLayout mEmail;
    private Button mLoginBtn;

    private FirebaseAuth mAuth;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progress = new ProgressDialog(this);
        progress.setTitle("Processing");
        progress.setMessage("Logging You In...");
        progress.setCancelable(false);


        mEmail = (TextInputLayout) findViewById(R.id.login_email);
        mPassword = (TextInputLayout) findViewById(R.id.login_password);
        mLoginBtn = (Button) findViewById(R.id.login_sign_in_btn);
        mAuth = FirebaseAuth.getInstance();

        TextView login_signUp_txt = findViewById(R.id.login_signUp_txt);
        login_signUp_txt.setText(Html.fromHtml("Not a member yet? <b>Sign Up</b>"));


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();

                if(validate()){
                 login(email , password);
                }


                Log.i(TAG ,"Email: "+email+"\nPassword: "+password);
                Toast.makeText(LoginActivity.this,"Email: "+email+"\nPassword: "+password,Toast.LENGTH_LONG).show();
            }
        });

    }

    private void login(String email , String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent main_intent = new Intent(LoginActivity.this , MainActivity.class);
                            main_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(main_intent);
                            finish();
//                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }

                        // ...
                    }
                });
    }


    private boolean validate(){
        boolean valid = true;
        String email = mEmail.getEditText().getText().toString();
        String password = mPassword.getEditText().getText().toString();

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

        return valid;
    }

    /* Check */
    /*@Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            Intent login_intent = new Intent(LoginActivity.this , MainActivity.class);
            startActivity(login_intent);
            finish();
        }
    }*/


    public void SignUp_OnClick(View view) {
        Log.i(TAG,"SignUp_ONCLICK");
        Intent reg_intent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(reg_intent);
        finish();
    }

}
