package com.example.dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.adapter.RecyclerViewAdapter;
import com.example.modules.Products;

import java.util.ArrayList;
import java.util.List;

public class ProductsInformation extends AppCompatActivity {
    List<Products> listProducts;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
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


    }
    public void setDataForList(){
        listProducts.add(new Products(R.drawable.coca,"Cocacola"));
        listProducts.add(new Products(R.drawable.pepsi,"Pepsi"));
        listProducts.add(new Products(R.drawable.mirinda,"Mirinda"));

    }
}
