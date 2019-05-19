package com.example.ebayrevised.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ebayrevised.R;
import com.example.ebayrevised.adapter.OrderSummaryAdapter;
import com.example.ebayrevised.database.DbHelper;
import com.example.ebayrevised.model.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private OrderSummaryAdapter orderSummaryAdapter;
    private List<ProductDetail> productDetailList;
    DbHelper dbHelper = new DbHelper(this);
    Button btnHome,btnLogout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        recyclerView =findViewById(R.id.recyclerViewOrderSummary);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        productDetailList = new ArrayList<>(dbHelper.displayCart());
        orderSummaryAdapter = new OrderSummaryAdapter(getApplicationContext(),productDetailList);
        recyclerView.setAdapter(orderSummaryAdapter);
        btnHome = findViewById(R.id.buttonHome);
        btnLogout =findViewById(R.id.buttonLogOut);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummaryActivity.this, CategoryActivity.class);
                startActivity(intent);

            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummaryActivity.this, MainActivity.class);
                dbHelper.deleteDatabase();
                startActivity(intent);

            }
        });



    }
}
