package com.example.ebayrevised.ui;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ebayrevised.R;
import com.example.ebayrevised.database.DbHelper;
import com.example.ebayrevised.model.ProductDetail;
import com.example.ebayrevised.adapter.ProductDetailAdapter;

public class ProductDetailActivity extends AppCompatActivity {
    private final String TAG = ProductDetailActivity.class.getSimpleName();
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ProductDetailAdapter detailAdapter;
    ProductDetail productDetails;
    DbHelper dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        recyclerView = findViewById(R.id.recyclerViewProductDetail);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dbHelper = new DbHelper(this);

        final String name = getIntent().getStringExtra("name");
        final String prize = getIntent().getStringExtra("prize");
        final String discription = getIntent().getStringExtra("discription");
        final String imageurl = getIntent().getStringExtra("imageurl");
        final String quantity = getIntent().getStringExtra("quantity");
        final String pid = getIntent().getStringExtra("pid");
        Log.d(TAG,"proDetailAct " + quantity + " " + pid);
        productDetails = new ProductDetail(pid,name,prize,discription,imageurl,quantity);
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
                Intent intent = new Intent(ProductDetailActivity.this,CartActivity.class);
                intent.putExtra("name",productDetails.getName());
                intent.putExtra("prize",productDetails.getPrize());
                intent.putExtra("discription",productDetails.getDiscription());
                intent.putExtra("quantity",productDetails.getQuantity());
                intent.putExtra("imageurl",productDetails.getImageurl());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return super.onOptionsItemSelected(item);
    }
}
