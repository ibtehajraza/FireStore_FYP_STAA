package com.fyp.ibtehaj.firestore;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CreateInvoiceActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private List<Medicine> medicineList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AddCartAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invoice);


        Bundle intent = getIntent().getExtras();
        if(intent != null) {
            String status[] = intent.getStringArray("medical");
            Log.i("invoice",status[0]);
        }


        // Setting The ToolBar
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
//        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Cart");
//        TextView mTitle = toolbar.findViewById(R.id.toolBarTitle);
//        mTitle.setText("cart");
//        mTitle.setAllCaps(true);
//        mTitle.setPadding(1,1,1,1);


        // Getting Authentication Status From Fire base
        mAuth = FirebaseAuth.getInstance();
//        USER_ID = null;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerView =  findViewById(R.id.recycler_view);


        medicineList = new ArrayList<>();
        adapter = new AddCartAdapter(this, medicineList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_silde_from_right);
        recyclerView.setLayoutAnimation(controller);

                prepareData();

        fetchData();

        Button check_out_btn = findViewById(R.id.check_out_btn);
        check_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("invoice",adapter.getItemCount()+"");
            }
        });



    }

    private void fetchData() {




    }

    private void prepareData() {

        Medicine medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);

        medicine = new Medicine("Arinic", "aaa", 66);
        medicineList.add(medicine);


        adapter.notifyDataSetChanged();
    }
}
