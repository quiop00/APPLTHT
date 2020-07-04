package com.example.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.adapter.RecyclerAdapter;
import com.example.dashboard.R;
import com.example.modules.Calendar;
import com.example.modules.Products;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Date extends Fragment {
    LineChart lineChart;
    LineDataSet lineDataSet;
    Calendar calendar;
    TextView tvPickDate;
    TextView tvTotal;
    private int total;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<Products> productsList;
    DatePickerDialog.OnDateSetListener setListener;
    public Date() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_date, container, false);
        //lineChart=(LineChart)view.findViewById(R.id.line_chart_date);
        //lineDataSet=new LineDataSet(dataValues1(),"Product");
        //ArrayList<ILineDataSet> dataSets=new ArrayList<>();
        //dataSets.add(lineDataSet);
        //configLineDataSet();
        //LineData data=new LineData(dataSets);
        //configLineChart();
        //lineChart.setData(data);
        //lineChart.invalidate();
        tvPickDate=view.findViewById(R.id.tv_pick_date);
        tvTotal=view.findViewById(R.id.tv_total);
        //get date
        setCalendar();
        tvPickDate.setText(calendar.getDate()+"/"+calendar.getMonth()+"/"+calendar.getYear());
        //end get date
        tvPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getContext(),
                        AlertDialog.THEME_HOLO_DARK,setListener,calendar.getYear(),calendar.getMonth()-1,calendar.getDate());
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
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



        getDataFromFirebase();
        return view;
    }
    private void getDataFromFirebase(){

        final DatabaseReference databaseReference= FirebaseDatabase.getInstance()
                .getReference(""+calendar.getYear())
                .child(calendar.getMonth()+"").child(""+calendar.getDate()).child("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    productsList=new ArrayList<>();
                    total=0;
                    int id=0;
                    for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                        String name=snapshot.getKey();
                        id=id+1;
                        int count=snapshot.getValue(Integer.class);
                        Products products=new Products(id,name,count);
                        total+=count;
                        productsList.add(products);
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
    // Get data from firebase
    private ArrayList<Entry> dataValues1(){
        ArrayList<Entry> dataVals=new ArrayList<Entry>();
        dataVals.add(new Entry(0,1));
        dataVals.add(new Entry(1,5));
        dataVals.add(new Entry(2,9));
        dataVals.add(new Entry(3,4));
        return  dataVals;
    }
    public void configLineChart(){
        lineChart.setDrawGridBackground(false);
        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setBackgroundColor(Color.TRANSPARENT);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis=lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        //lineChart.getAxisLeft().setDrawGridLines(false);
        //Description
        Description description=new Description();
        description.setText("Time");
        description.setTextColor(Color.RED);
        description.setTextSize(15);
        lineChart.setDescription(description);

    }
    public void configLineDataSet(){
        lineDataSet.setLineWidth(2.5f);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setCircleHoleColor(Color.GREEN);
        lineDataSet.setCircleHoleRadius(8);
        lineDataSet.setCircleRadius(10);
        lineDataSet.setValueTextSize(15f);
        lineDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int)(value));
            }
        });
    }

}
