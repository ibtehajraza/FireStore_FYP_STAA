package com.fyp.ibtehaj.firestore;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by ibtehaj on 5/6/2018.
 */

public class CustomDialogBuilder extends Dialog implements android.view.View.OnClickListener{


    public Activity c;
    public Dialog d;
    public Button confirm, cancle;
    public TextView totalPrice;
    public TextView totalQuantity;

    public CustomDialogBuilder(Activity a) {
        super(a);
        this.c = a;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);

//
        cancle = findViewById(R.id.cancel_btn);
         confirm = findViewById(R.id.confirm_btn);

        totalPrice = findViewById(R.id.total_price);
        totalQuantity = findViewById(R.id.total_quantity);

//        totalPrice.setText("frrfw");

        confirm.setOnClickListener(this);
        cancle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm_btn:
                c.finish();
                break;
            case R.id.cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }


    public void setContent(String price, String quantity){

        Log.i("invoice",price+"");
        Log.i("invoice",quantity+"");

        totalPrice.setText("Rs. "+price);
        totalQuantity.setText(quantity);

    }

}
