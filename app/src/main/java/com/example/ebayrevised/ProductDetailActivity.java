package com.example.ebayrevised;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import com.example.ebayrevised.model.ProductDetail;
import com.util.ProductDetailAdapter;

import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {
    private final String TAG = ProductDetailActivity.class.getSimpleName();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProductDetailAdapter detailAdapter;
    ProductDetail productDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        recyclerView = findViewById(R.id.recyclerViewProductDetail);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String name = getIntent().getStringExtra("name");
        String prize = getIntent().getStringExtra("prize");
        String discription = getIntent().getStringExtra("discription");
        String imageurl = getIntent().getStringExtra("imageurl");
        String quantity = getIntent().getStringExtra("quantity");
        String pid = getIntent().getStringExtra("pid");

        productDetails = new ProductDetail(name,prize,imageurl,discription);
        detailAdapter = new ProductDetailAdapter(productDetails,getApplicationContext());
        recyclerView.setAdapter(detailAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shoppingCart:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
}
