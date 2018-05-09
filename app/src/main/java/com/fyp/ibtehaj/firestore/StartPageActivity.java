package com.fyp.ibtehaj.firestore;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import spencerstudios.com.bungeelib.Bungee;

public class StartPageActivity extends AppCompatActivity {

    private Button mRegBtn;
    private Button mLogInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mRegBtn  = findViewById(R.id.start_reg_btn);
        mLogInBtn  = findViewById(R.id.start_login_btn);

        /*Register Button onClickListener **/
//        mRegBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent sale_man_intent = new Intent(StartPageActivity.this , RegisterActivity.class);
//                sale_man_intent.putExtra("status", "SalesMan");
//                startActivity(sale_man_intent);
//            }
//        });
//
//
//
//        mLogInBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent login_intent = new Intent(StartPageActivity.this , LoginActivity.class);
//                startActivity(login_intent);
//            }
//        });

    }

    public void SignInBtn_Click(View view) {
        String temp ;

        if(view == findViewById( R.id.start_reg_btn)){
            temp = "SalesMan";
        }else{
            temp = "ReOrderGuy";
        }

//        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("server", "serverName");


        SharedPreferences preferences = getSharedPreferences("status_string",MODE_PRIVATE);
        if(preferences.getString(getString(R.string.status_tag), "") == ""){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(getString(R.string.status_tag), temp);
            editor.apply();
            //    Log.i("status","Added Preference ");
        }

//        Log.i("status","In Register=> "+ preferences.getString(getString(R.string.status_tag), "defaultValue"));

//        Log.i("status",temp+"");

        Intent intent = new Intent(StartPageActivity.this , LoginActivity.class);
        intent.putExtra("status", temp);
        startActivity(intent);
        Bungee.zoom(StartPageActivity.this);
        finish();

    }
}
