package com.fyp.ibtehaj.firestore;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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

import java.util.ArrayList;
import java.util.List;

public class ReorderGuyMainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private List<MedicalStore> medicalStoresList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MedicalStoreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reorder_guy_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Setting The ToolBar
        Toolbar toolbar = findViewById(R.id.main_page_toolbar);
        toolbar.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        TextView mTitle = toolbar.findViewById(R.id.toolBarTitle);
        mTitle.setText("Medical Stores");
        mTitle.setAllCaps(true);
        mTitle.setPadding(1,1,1,1);


        // Getting Authentication Status From Firebase
        mAuth = FirebaseAuth.getInstance();
//        USER_ID = null;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        recyclerView =  findViewById(R.id.recycler_view);

        medicalStoresList = new ArrayList<>();
        adapter = new MedicalStoreAdapter(this, medicalStoresList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        Context context = recyclerView.getContext();
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_silde_from_right);
        recyclerView.setLayoutAnimation(controller);


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MedicalStore medicalStore = medicalStoresList.get(position);
                Intent i = new Intent(ReorderGuyMainActivity.this, CreateInvoiceActivity.class);
                String m[] = new String[3];
                m[0] = medicalStore.getName();
                m[1] = medicalStore.getContact();
                m[2] = medicalStore.getAddress();
                i.putExtra("medical",m);
                startActivity(i);
//                Toast.makeText(getApplicationContext(), movie.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));



//        prepareData();
        getData();


    }

    private void getData() {
        FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("MedicalStores");
        Log.i("recy","in getData");



        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                if(dataSnapshot.hasChildren()){
                    Log.i("recy",dataSnapshot.getChildrenCount()+" ");
                    MedicalStore medicalStore = dataSnapshot.getValue(MedicalStore.class);

                    if(medicalStore != null){
                        medicalStoresList.add(medicalStore);
                    }

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

    }

    private void prepareData() {

        MedicalStore medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);

        medicalStore = new MedicalStore("F.B. AREA","03062698050","Ali");
        medicalStoresList.add(medicalStore);



        adapter.notifyDataSetChanged();

    }


    private void logout(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut();
//            log("currentUser: "+currentUser);
            Intent intent = new Intent(ReorderGuyMainActivity.this , StartPageActivity.class);
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
                Intent intent = new Intent(ReorderGuyMainActivity.this, AccountSettingActivity.class);
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
}
