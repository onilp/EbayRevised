package com.example.ebayrevised.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.example.ebayrevised.R;
import com.example.ebayrevised.database.DbHelper;
import com.example.ebayrevised.model.ProductDetail;
import com.example.ebayrevised.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartAdapter cartAdapter;
    private List<ProductDetail> productDetailList;
    DbHelper dbHelper = new DbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerViewCart);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //get the list of products in cart from the db
        productDetailList = new ArrayList<>(dbHelper.displayCart());
        cartAdapter = new CartAdapter(getApplicationContext(),productDetailList);
        recyclerView.setAdapter(cartAdapter);

    }

}
