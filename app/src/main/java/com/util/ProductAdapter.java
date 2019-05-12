package com.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.ebayrevised.ProductDetailActivity;
import com.example.ebayrevised.R;
import com.example.ebayrevised.model.Product;
import com.example.ebayrevised.model.ProductDetail;
import com.example.ebayrevised.network.MySingleton;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private List<Product> productList;
    private Context context;

    public ProductAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.MyViewHolder myViewHolder, int i) {
        final Product p = productList.get(i);
        myViewHolder.textViewProductPname.setText(p.getPname());
        myViewHolder.textViewProductPrize.setText(p.getPrize());
        myViewHolder.textViewProductDiscription.setText(p.getDiscription());
        ImageRequest imageRequest = new ImageRequest(p.getImage(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                myViewHolder.imageView.setImageBitmap(response);

            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });

        MySingleton.getInstance(context).getRequestQueue().add(imageRequest);
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("name",p.getPname());
                intent.putExtra("prize",p.getPrize());
                intent.putExtra("discription",p.getDiscription());
                intent.putExtra("imageurl",p.getImage());
                intent.putExtra("quantity",p.getQuantity());
                intent.putExtra("pid",p.getId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProductPname, textViewProductPrize,textViewProductDiscription;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProductPname = itemView.findViewById(R.id.textViewProductPname);
            textViewProductPrize = itemView.findViewById(R.id.textViewProductPrize);
            textViewProductDiscription = itemView.findViewById(R.id.textViewProductDiscription);
            imageView = itemView.findViewById(R.id.productImage);
        }
    }
}
