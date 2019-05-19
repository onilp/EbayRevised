package com.example.ebayrevised.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ebayrevised.R;
import com.example.ebayrevised.database.DbHelper;
import com.example.ebayrevised.model.ProductDetail;

import java.util.List;

public class OrderSummaryAdapter extends RecyclerView.Adapter<OrderSummaryAdapter.MyViewHolder> {

    private Context context;
    private List<ProductDetail> productDetailList;


    public OrderSummaryAdapter(Context context, List<ProductDetail> productDetailList) {
        this.context = context;
        this.productDetailList = productDetailList;
    }

    @NonNull
    @Override
    public OrderSummaryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_summary_items,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderSummaryAdapter.MyViewHolder myViewHolder, int i) {
        final ProductDetail pd = productDetailList.get(i);
            myViewHolder.name.setText(pd.getName());
            myViewHolder.prize.setText(pd.getPrize());
            myViewHolder.discription.setText(pd.getDiscription());
            SharedPreferences preferences = context.getSharedPreferences("userPref",Context.MODE_PRIVATE);
            String tId = preferences.getString("transactionId","");
            myViewHolder.transactionId.setText("Order Id :" + tId);

    }
    @Override
    public int getItemCount() {
        return productDetailList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, prize, discription,transactionId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewSummaryName);
            prize = itemView.findViewById(R.id.textViewSummaryPrize);
            discription = itemView.findViewById(R.id.textViewSummaryDiscription);
            transactionId = itemView.findViewById(R.id.transactionId);


        }
    }
}
