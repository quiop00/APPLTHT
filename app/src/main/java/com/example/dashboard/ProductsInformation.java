package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.adapter.RecyclerViewAdapter;
import com.example.modules.Products;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductsInformation extends AppCompatActivity {
    List<Products> listProducts;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    Connection conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_information);
        init();
        setDataForList();
        recyclerViewAdapter=new RecyclerViewAdapter(this,listProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(recyclerViewAdapter);
    }
    public void init(){
        listProducts=new ArrayList<>();
        recyclerView=(RecyclerView)findViewById(R.id.rv_list_products);
        getConnect();

    }
    public void setDataForList(){
        listProducts=getData();
    }
    public void getConnect(){
        final String DB_URL = "jdbc:mysql://103.1.236.4:3306/img_train_db";
        final String USER = "thuan";
        final String PASS = "Thuan*884578";
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(DB_URL, USER, PASS); //Connection Object
            Toast.makeText(this,"Connect successfully",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("LOI A","Loi connect data");
        }

    }
    public ArrayList<Products> getData(){
        ArrayList<Products> listProducts=new ArrayList<>();
        try {
            // Change below query according to your own database.
            String query = "SELECT path,name FROM image";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                Log.d("INFO",rs.getString(1));
                listProducts.add(new Products(rs.getString(1),rs.getString(2)));
            }
            conn.close();
        } catch (Exception e) {
            Log.d("LOI C","Loi connect");
        }
        return listProducts;
    }

}
