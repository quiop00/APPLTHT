package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//  HOME
public class MainActivity extends AppCompatActivity {
    CardView cVDashboard;
    CardView cVRecovery;
    CardView cVStatus;
    CardView cVReport;
    CardView cVProducts;
    CardView cVExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addOnClickListener();
    }
    public void init(){
        cVDashboard=(CardView) findViewById(R.id.card_view_dashboard);
        cVRecovery=(CardView) findViewById(R.id.card_view_recovery);
        cVStatus=(CardView) findViewById(R.id.card_view_status);
        cVReport=(CardView) findViewById(R.id.card_view_report);
        cVProducts=(CardView)findViewById(R.id.card_view_products_info);
        cVExit=(CardView) findViewById(R.id.card_view_exit);
    }
    public void addOnClickListener(){
        cVDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(MainActivity.this,Dashboard.class);
                startActivity(intent);
            }
        });
        cVProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(MainActivity.this,ProductsInformation.class);
                startActivity(intent);
            }
        });
        cVStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(MainActivity.this,Status.class);
                startActivity(intent);
            }
        });
        cVReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent=new Intent(MainActivity.this,Report.class);
                startActivity(intent);
            }
        });
        cVRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cVExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(1);
            }
        });
    }
}
