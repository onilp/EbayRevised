package com.example.ebayrevised.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ebayrevised.R;
import com.example.ebayrevised.model.SubCategory;
import com.example.ebayrevised.network.MySingleton;
import com.example.ebayrevised.util.Constant;
import com.example.ebayrevised.adapter.SubCategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SubCategoryAdapter subCategoryAdapter;
    List<SubCategory> subCategoryList;
    private final String TAG = SubCategoryActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sub_category);
        init();

        progressDialog.setMessage("Loading..");
        progressDialog.show();
        SharedPreferences prefs = getSharedPreferences("userPref",MODE_PRIVATE);
        final String api_key = prefs.getString("appapikey","");
        final String user_id = prefs.getString("id","");
        final String cid = getIntent().getStringExtra("cid");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("cid",cid);
        editor.apply();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.PRODUCT_SUB_CATEGORY_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, "response :" + response);
                try {
                    Log.d(TAG, "Shared Pref cid : " + cid + "api :" + api_key + "user id " + user_id);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("subcategory");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject sc = jsonArray.getJSONObject(i);
                        String scid = sc.getString("scid");
                        String scname = sc.getString("scname");
                        String scdiscription = sc.getString("scdiscription");
                        String scimageurl = sc.getString("scimageurl");

                        subCategoryList.add(new SubCategory(scid,scname,scdiscription,scimageurl));

                    }
                    subCategoryAdapter = new SubCategoryAdapter(getApplicationContext(),subCategoryList);
                    recyclerView.setAdapter(subCategoryAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error : " + error.getMessage());

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Id",cid);
                params.put("api_key",api_key);
                params.put("user_id",user_id);
                return params;
            }
        };

        MySingleton.getInstance(this).getRequestQueue().add(stringRequest);
    }

    private void init() {
        recyclerView = findViewById(R.id.recyclerViewSub);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        subCategoryList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
    }
}
