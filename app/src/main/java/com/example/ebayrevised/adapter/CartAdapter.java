package com.example.ebayrevised.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.ebayrevised.R;
import com.example.ebayrevised.database.DbHelper;
import com.example.ebayrevised.model.ProductDetail;
import com.example.ebayrevised.network.MySingleton;
import com.example.ebayrevised.network.PaymentActivity;
import com.example.ebayrevised.ui.CartActivity;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    private Context context;
    private List<ProductDetail> productDetailList;
    private DbHelper dbHelper;
    private int counter = 1;
    private int totalAmount;


    public CartAdapter(Context context, List<ProductDetail> productDetailList) {
        this.context = context;
        this.productDetailList = productDetailList;
        this.dbHelper = new DbHelper(context);

    }

    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartAdapter.MyViewHolder myViewHolder, int i) {

            final ProductDetail pd = productDetailList.get(i);
            myViewHolder.textViewCartItemName.setText(pd.getName());
            myViewHolder.textViewCartItemPrize.setText(pd.getPrize());
            myViewHolder.textViewCartItemDiscription.setText(pd.getDiscription());
            //myViewHolder.textViewCartItemQuantity.setText(pd.getQuantity());
            ImageRequest imageRequest = new ImageRequest(pd.getImageurl(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    myViewHolder.imageViewCart.setImageBitmap(response);
                }
            }, 0, 0, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("CartAdapter :",error.getMessage());

                }
            });

            //MySingleton.getInstance(context).getRequestQueue().add(imageRequest);

            myViewHolder.buttonDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dbHelper.deleteItemFromCart(pd.getName());
                    CartAdapter.this.notifyDataSetChanged();
                }
            });
            myViewHolder.textViewItemQuantity.setText(String.valueOf(counter));
            totalAmount = Integer.parseInt(pd.getPrize());

            myViewHolder.buttonMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Integer.parseInt(myViewHolder.textViewItemQuantity.getText().toString()) > 1) {
                        counter--;
                        totalAmount = counter* Integer.parseInt(pd.getPrize());
                        myViewHolder.textViewItemQuantity.setText(String.valueOf(counter));

                    }
                }
            });
            myViewHolder.buttonPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    counter++;
                    totalAmount = counter *Integer.parseInt(pd.getPrize());;
                    myViewHolder.textViewItemQuantity.setText(String.valueOf(counter));
                    //totalAmount += counter * Integer.parseInt(pd.getPrize());

                }
            });
            myViewHolder.buttonCheckout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CartAdapter.this.context, PaymentActivity.class);
                    intent.putExtra("totalAmount", String.valueOf(totalAmount));
                    context.startActivity(intent);
                }
            });
        }


    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCartItemName,textViewCartItemPrize,textViewCartItemDiscription,textViewCartItemQuantity,textViewItemQuantity;
        Button buttonDeleteItem, buttonMinus, buttonPlus,buttonCheckout;
        ImageView imageViewCart;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCartItemName = itemView.findViewById(R.id.textViewCartName);
            textViewCartItemPrize = itemView.findViewById(R.id.textViewCartPrize);
            textViewCartItemDiscription = itemView.findViewById(R.id.textViewCartDiscription);
            buttonDeleteItem = itemView.findViewById(R.id.buttonDeleteItemFromCart);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            textViewItemQuantity = itemView.findViewById(R.id.textViewItemQuantity);
            imageViewCart = itemView.findViewById(R.id.imageViewCart);
            buttonCheckout = itemView.findViewById(R.id.buttonCheckout);


        }
    }
}