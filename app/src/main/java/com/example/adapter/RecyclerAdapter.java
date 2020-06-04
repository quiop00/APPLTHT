package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dashboard.R;
import com.example.modules.Products;

import java.util.List;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private List<Products> listProducts;
    public RecyclerAdapter(List<Products> listProducts){
        this.listProducts=listProducts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product=listProducts.get(position);
        //holder.tvId.setText(product.getId());
        holder.tvName.setText(product.getName());
        holder.tvCount.setText(product.getCount()+"");
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvId,tvName,tvCount;
        public ViewHolder(View itemView){
            super(itemView);
            //tvId=(TextView) itemView.findViewById(R.id.id);
            tvName=itemView.findViewById(R.id.tv_product_name);
            tvCount=itemView.findViewById(R.id.tv_count);
        }
    }
}
