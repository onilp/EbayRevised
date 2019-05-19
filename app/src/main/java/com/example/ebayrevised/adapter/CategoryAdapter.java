package com.example.ebayrevised.adapter;

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
import com.example.ebayrevised.R;
import com.example.ebayrevised.ui.SubCategoryActivity;
import com.example.ebayrevised.model.Category;
import com.example.ebayrevised.network.MySingleton;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter <CategoryAdapter.MyViewHolder>{
    private List <Category> categoryList;
    private Context context;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
        final Category c = categoryList.get(position);
        myViewHolder.textViewCname.setText(c.getCname());
        myViewHolder.textViewCdiscription.setText(c.getCdiscription());

        ImageRequest imageRequest = new ImageRequest(c.getCimagerl(), new Response.Listener<Bitmap>() {
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

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SubCategoryActivity.class);
                intent.putExtra("cid",c.getCid());
                context.startActivity(intent);
            }
        });
        MySingleton.getInstance(context).getRequestQueue().add(imageRequest);
    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCid, textViewCname, textViewCdiscription;
        ImageView imageView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCname = itemView.findViewById(R.id.textViewCname);
            textViewCdiscription = itemView.findViewById(R.id.textViewCdiscription);
            imageView = itemView.findViewById(R.id.cImage);
        }

    }
}
