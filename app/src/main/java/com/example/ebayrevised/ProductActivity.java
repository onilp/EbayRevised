package com.example.ebayrevised;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ebayrevised.model.Product;
import com.example.ebayrevised.network.MySingleton;
import com.util.Constant;
import com.util.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {
    private final String TAG = ProductActivity.class.getSimpleName();
    List<Product> productList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        SharedPreferences prefs = getSharedPreferences("userPref",MODE_PRIVATE);
        final String api_key = prefs.getString("appapikey","");
        final String user_id = prefs.getString("id","");
        final String cid = prefs.getString("cid","");
        final String scid = getIntent().getStringExtra("scid");


        init();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.PRODUCT_LIST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Response :" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("products");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject product = jsonArray.getJSONObject(i);
                        String id = product.getString("id");
                        String pname = product.getString("pname");
                        String quantity = product.getString("quantity");
                        String prize = product.getString("prize");
                        String discription = product.getString("discription");
                        String image = product.getString("image");

                        productList.add(new Product(id,pname,quantity,prize,discription,image));

                    }
                    productAdapter = new ProductAdapter(productList,getApplicationContext());
                    recyclerView.setAdapter(productAdapter);

                } catch (JSONException e) {
                    Log.d(TAG, "Exception :" +e.getMessage());

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error :" + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("cid",cid);
                params.put("scid",scid);
                params.put("api_key",api_key);
                params.put("user_id",user_id);
                return params;
            }
        };

        MySingleton.getInstance(this).getRequestQueue().add(stringRequest);

    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerViewProduct);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        productList = new ArrayList<>();
    }
}
