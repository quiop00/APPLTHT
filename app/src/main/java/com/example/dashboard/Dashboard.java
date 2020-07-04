package com.example.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fragment.Date;
import com.example.fragment.Month;
import com.example.fragment.Year;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();
        loadFragment(new Date());
        navigationView.setOnNavigationItemSelectedListener(onNavigationItemReselectedListener);

    }
    public void init(){
        navigationView=(BottomNavigationView)findViewById(R.id.navigation);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemReselectedListener=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()){
                case R.id.navigation_date:{
                    fragment=new Date();//Date fragment
                    loadFragment(fragment);
                    return true;
                }
                case R.id.navigation_month:{
                    fragment=new Month();
                    loadFragment(fragment);
                    return true;
                }
                case R.id.navigation_home:{
                    Intent intent=new Intent(Dashboard.this,MainActivity.class);
                    startActivity(intent);
                }
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
