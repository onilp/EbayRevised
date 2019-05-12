package com.example.ebayrevised;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ebayrevised.model.Category;
import com.example.ebayrevised.network.MySingleton;
import com.util.Constant;
import com.util.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    private final String TAG = CategoryActivity.class.getSimpleName();
    SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);

        init();

        prefs = getSharedPreferences("userPref",MODE_PRIVATE);
        String api_key = prefs.getString("appapikey","");
        Log.d(TAG, "Shared pref api Key " + api_key);
        String user_id = prefs.getString("id","");
        Log.d(TAG, "Shared pref user ID " + user_id);


        getCategoryResponse(api_key,user_id);
    }

    /**
     * get category list from server
     * @param api_key
     * @param user_id
     */
    public void getCategoryResponse(final String api_key,final String user_id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.PRODUCT_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "String response : " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("category");
                    for(int i=0; i<jsonArray.length();i++)
                    {
                        JSONObject c = jsonArray.getJSONObject(i);
                        String cid = c.getString("cid");
                        String cname = c.getString("cname");
                        String cdiscription = c.getString("cdiscription");
                        String cimagerl = c.getString("cimagerl");

                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("cid",cid);
                        editor.apply();

                        categoryList.add(new Category(cid,cname,cdiscription,cimagerl));
                    }

                    categoryAdapter = new CategoryAdapter(getApplicationContext(),categoryList);
                    recyclerView.setAdapter(categoryAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("api_key",api_key);
                params.put("user_id",user_id);
                return params;
            }
        };

        MySingleton.getInstance(this).getRequestQueue().add(stringRequest);
    }
    private void init() {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        categoryList = new ArrayList<>();

    }

    @Override
    public void onBackPressed() {

    }
}
