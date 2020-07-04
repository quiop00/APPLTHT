package com.example.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.adapter.RecyclerAdapter;
import com.example.dashboard.R;
import com.example.modules.Calendar;
import com.example.modules.Products;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Month extends Fragment {
    Calendar calendar;
    TextView tvPickDate;
    TextView tvTotal;
    private int total,replace;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<Products> productsList;
    DatePickerDialog.OnDateSetListener setListener;
    public Month() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_month, container, false);
        tvPickDate=view.findViewById(R.id.tv_pick_date);
        tvTotal=view.findViewById(R.id.tv_total);
        //get date
        setCalendar();
        tvPickDate.setText(calendar.getMonth()+"/"+calendar.getYear());
        //end get date
        tvPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
                        AlertDialog.THEME_HOLO_DARK,setListener,calendar.getYear(),calendar.getMonth()-1,calendar.getDate());
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                if(datePickerDialog.findViewById(Resources
                        .getSystem()
                        .getIdentifier("android:id/year", null, null))!=null){
                    datePickerDialog.findViewById(Resources
                            .getSystem()
                            .getIdentifier("android:id/year", null, null)).setVisibility(View.INVISIBLE);

                }
            }
        });
        setListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.setDate(dayOfMonth);
                calendar.setMonth(month+1);
                calendar.setYear(year);
                tvPickDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                getDataFromFirebase();
            }
        };
        //recycler view start
        recyclerView=view.findViewById(R.id.rv_lists);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recycler view end


        Log.i("","loi");
        getDataFromFirebase();
        return view;
    }
    private void getDataFromFirebase(){

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance()
                .getReference(""+calendar.getYear())
                .child(calendar.getMonth()+"");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    productsList=new ArrayList<>();
                    total=0;
                    int id=0;
                    int replace=0;
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){

                            if(snapshot1.getKey().equals("products")) {
                                for (DataSnapshot snapshot2:snapshot1.getChildren()){
                                    String name = snapshot2.getKey();
                                    int count = snapshot2.getValue(Integer.class);
                                    if(checkSimilar(name)){
                                        productsList.get(replace).setCount(productsList.get(replace).getCount()+count);
                                    }else{
                                        id = id + 1;
                                        Products products = new Products(id, name, count);
                                        productsList.add(products);
                                    }
                                    total += count;
                                }
                                continue;
                            }
                        }

                    }
                    tvTotal.setText(total+"");
                    adapter=new RecyclerAdapter(productsList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setCalendar(){
        java.util.Date date=new java.util.Date();
        java.util.Calendar calendar=new GregorianCalendar();
        calendar.setTime(date);
        this.calendar=new Calendar(calendar.get(java.util.Calendar.YEAR),calendar.get(java.util.Calendar.MONTH)+1,calendar.get(java.util.Calendar.DAY_OF_MONTH));
    }
    public boolean checkSimilar(String productName){
        for(Products product:productsList){
            if(productName.equalsIgnoreCase(product.getName())){
                replace=product.getId();
                return true;
            }
        }
        return false;
    }


}
