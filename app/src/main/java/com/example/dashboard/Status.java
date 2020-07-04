package com.example.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ImageView;

import com.example.adapter.HistoryViewAdapter;
import com.example.modules.Calendar;
import com.example.modules.History;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Status extends AppCompatActivity {
    ImageView imgStatus;
    List<History> listHistories;
    RecyclerView recyclerView;
    Boolean status=false;
    DatabaseReference databaseReference;
    HistoryViewAdapter adapter;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        init();
        setCalendar();
        databaseReference= FirebaseDatabase.getInstance()
                .getReference(calendar.getYear()+"")
                .child(calendar.getMonth()+"")
                .child(calendar.getDate()+"")
                .child("status");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    status=dataSnapshot.getValue(Boolean.class);
                    changeImageStatus(status);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        getHistoryFromFirebase();

    }
    public void init(){
        imgStatus=findViewById(R.id.image_status);
        //set RecyclerView
        recyclerView=findViewById(R.id.rv_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //end
    }
    public void changeImageStatus(boolean status){
        if(status){
            imgStatus.setImageResource(R.drawable.on);
        }
        else
            imgStatus.setImageResource(R.drawable.off);

    }
    public void getHistoryFromFirebase(){
        databaseReference= FirebaseDatabase.getInstance()
                .getReference(calendar.getYear()+"")
                .child(calendar.getMonth()+"")
                .child(calendar.getDate()+"")
                .child("history");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    listHistories=new ArrayList<History>();
                    int id=0;
                    for(DataSnapshot dataSnap:dataSnapshot.getChildren()){
                        id+=1;
                        String startTime=dataSnap.getKey();
                        String endTime=dataSnap.getValue(String.class);
                        listHistories.add(new History(id,startTime,endTime));
                    }
                    adapter=new HistoryViewAdapter(listHistories);
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
}
