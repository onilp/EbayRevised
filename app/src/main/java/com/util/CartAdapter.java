package com.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.ebayrevised.R;
import com.example.ebayrevised.database.model.CartProduct;
import com.example.ebayrevised.network.MySingleton;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<CartProduct> cartProducts;

    public CartAdapter(Context context, List<CartProduct> cartProducts) {
        this.context = context;
        this.cartProducts = cartProducts;
    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyViewHolder myViewHolder, int i) {
        final CartProduct cp = cartProducts.get(i);
        myViewHolder.textViewCartItemName.setText(cp.getName());
        myViewHolder.textViewCartItemPrize.setText(cp.getPrize());
        myViewHolder.textViewCartItemDiscription.setText(cp.getDiscription());

        ImageRequest imageRequest = new ImageRequest(cp.getImageurl(), new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                myViewHolder.imageViewCartItem.setImageBitmap(response);

            }
        }, 0, 0, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("CartAdapter",error.getMessage());

            }
        });
        MySingleton.getInstance(context).getRequestQueue().add(imageRequest);

    }

    @Override
    public int getItemCount() {
        return cartProducts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCartItemName,textViewCartItemPrize,textViewCartItemDiscription;
        ImageView imageViewCartItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCartItemName = itemView.findViewById(R.id.textViewCartName);
            textViewCartItemPrize = itemView.findViewById(R.id.textViewCartPrize);
            textViewCartItemDiscription = itemView.findViewById(R.id.textViewCartDiscription);
            imageViewCartItem = itemView.findViewById(R.id.imageViewCartImage);
        }
    }
}
