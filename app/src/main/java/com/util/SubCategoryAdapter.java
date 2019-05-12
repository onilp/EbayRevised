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
import com.example.ebayrevised.ProductActivity;
import com.example.ebayrevised.R;
import com.example.ebayrevised.model.SubCategory;
import com.example.ebayrevised.network.MySingleton;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    private List<SubCategory> subCategoryList;
    private Context context;

    public SubCategoryAdapter(Context context,List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.sub_category_list,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int position) {
        final SubCategory sc = subCategoryList.get(position);
        myViewHolder.textViewScname.setText(sc.getScname());
        myViewHolder.textViewScdiscription.setText(sc.getScdiscription());

        ImageRequest imageRequest = new ImageRequest(sc.getScimagerl(), new Response.Listener<Bitmap>() {
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
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("scid",sc.getScid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCategoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewScid, textViewScname, textViewScdiscription;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewScname=itemView.findViewById(R.id.textViewScname);
            textViewScdiscription=itemView.findViewById(R.id.textViewScdiscription);
            imageView = itemView.findViewById(R.id.sCimage);
        }
    }
}
