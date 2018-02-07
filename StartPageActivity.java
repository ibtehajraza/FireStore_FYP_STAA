package com.fyp.ibtehaj.firestore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartPageActivity extends AppCompatActivity {

    private Button mRegBtn;
    private Button mLogInBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        mRegBtn  = (Button) findViewById(R.id.start_reg_btn);
        mLogInBtn  = (Button) findViewById(R.id.start_login_btn);

        /*Register Button onClickListener **/
        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reg_intent = new Intent(StartPageActivity.this , RegisterActivity.class);
                startActivity(reg_intent);
            }
        });



        mLogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login_intent = new Intent(StartPageActivity.this , LoginActivity.class);
                startActivity(login_intent);
            }
        });

    }
}
