package com.util;

import android.content.Context;
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
import com.example.ebayrevised.R;
import com.example.ebayrevised.model.ProductDetail;
import com.example.ebayrevised.network.MySingleton;


public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.MyViewHolder> {

    ProductDetail productDetail;
    Context context;

    public ProductDetailAdapter(ProductDetail productDetail, Context context) {
        this.productDetail = productDetail;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductDetailAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_detail,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductDetailAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.textViewName.setText(productDetail.getName());
        myViewHolder.textViewPrize.setText(productDetail.getPrize());
        myViewHolder.textViewDiscription.setText(productDetail.getDiscription());

        ImageRequest imageRequest = new ImageRequest(productDetail.getImageurl(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                myViewHolder.imageView.setImageBitmap(response);

            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(context).getRequestQueue().add(imageRequest);


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPrize, textViewDiscription;
        ImageView imageView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewProductDetailName);
            textViewPrize = itemView.findViewById(R.id.textViewProductDetailPrize);
            textViewDiscription = itemView.findViewById(R.id.textViewProductDetailDiscription);
            imageView = itemView.findViewById(R.id.imageViewProductDetailImage);
        }
    }
}
