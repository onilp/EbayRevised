package com.example.ebayrevised.authentication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ebayrevised.ui.CategoryActivity;
import com.example.ebayrevised.R;
import com.example.ebayrevised.network.MySingleton;
import com.example.ebayrevised.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class UserProfileUpdate extends AppCompatActivity {

    private EditText editTextProfileFname,editTextProfileLname,editTextProfileAddress,editTextProfileEmail,editTextProfileMobile;
    private Button buttonProfileUpdate;
    private final String TAG = UserProfileUpdate.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_update);

        editTextProfileFname = findViewById(R.id.editTextProfileFname);
        editTextProfileLname = findViewById(R.id.editTextProfileLname);
        editTextProfileAddress = findViewById(R.id.editTextProfileAddress);
        editTextProfileEmail = findViewById(R.id.editTextProfileEmail);
        editTextProfileMobile = findViewById(R.id.editTextProfileMobile);
        buttonProfileUpdate = findViewById(R.id.buttonUpdateProfile);

        buttonProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fname = editTextProfileFname.getText().toString();
                final String lname = editTextProfileLname.getText().toString();
                final String address = editTextProfileAddress.getText().toString();
                final String email = editTextProfileEmail.getText().toString();
                final String mobile = editTextProfileMobile.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.PROFILE_UPDATE_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG,"Profile update :"+ response);
                        if(response.equals("successfully updated")) {
                            Toast.makeText(UserProfileUpdate.this, response, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserProfileUpdate.this, CategoryActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(UserProfileUpdate.this, response, Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,error.getMessage());

                    }
                }){

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("fname",fname);
                        params.put("lname",lname);
                        params.put("address",address);
                        params.put("email",email);
                        params.put("mobile",mobile);
                        return params;
                    }
                };

                MySingleton.getInstance(getApplicationContext()).getRequestQueue().add(stringRequest);
            }
        });
    }
}
