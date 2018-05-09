package com.fyp.ibtehaj.firestore;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ibtehaj on 5/4/2018.
 */

public class AddCartAdapter extends RecyclerView.Adapter<AddCartAdapter.MyViewHolder>{

    private Context context;
    private List<Medicine> medicineList;
    private List<Medicine> cartMedicine;
    private int totalAmount;

    public AddCartAdapter(Context context, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
        cartMedicine = new ArrayList<>() ;
        totalAmount = 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_recycler_cell, parent , false);


        return new AddCartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final AddCartAdapter.MyViewHolder holder, int position) {

        final Medicine medicine = medicineList.get(position);


        holder.name.setText(medicine.getMedicineName());
        holder.price.setText("Rs. "+medicine.getPrice());


        holder.cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cartMedicine.add(medicine);
//                Snackbar.make(view, cartMedicine.size()+"",Snackbar.LENGTH_LONG).show();
                holder.cartButton.setBackgroundColor(Color.parseColor("#FFDA201D"));
                holder.cartButton.setEnabled(false);
                totalAmount += (Integer.parseInt( holder.quantity.getText().toString()) * Integer.parseInt(medicine.getPrice())) ;

            }
        });


        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(holder.quantity.getText().toString()) + 1 ;
//
//Log.i("invoice", ++temp+"");
                holder.quantity.setText(temp+"");


            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int temp = Integer.parseInt(holder.quantity.getText().toString()) - 1 ;
//
//Log.i("invoice", ++temp+"");
                holder.quantity.setText(temp+"");


            }
        });



    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public List<Medicine> getCartData(){

//        FirebaseAuth mAuth;
//        DatabaseReference mDatabase;
//        // Getting Authentication Status From Firebase
//        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance().getReference("Order");
//
//        HashMap<String, String> temp = new HashMap<>();
////        temp.put("medicalStoreName",);
//
//        mDatabase.child(mAuth.getUid()).setValue("jhvjh");

        return cartMedicine;
    }

    public int totalAmount(){
        return totalAmount;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        Button cartButton;
        EditText quantity;
        TextView name;
        TextView price;
        TextView plus;
        TextView minus;



        public MyViewHolder(View itemView) {
            super(itemView);

            cartButton = itemView.findViewById(R.id.add_cart);
            name = itemView.findViewById(R.id.medicine_name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.editText);

            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);

        }
    }


}
