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
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ebayrevised.CategoryActivity;
import com.example.ebayrevised.R;
import com.example.ebayrevised.network.MySingleton;
import com.util.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {
    private final String TAG = ResetPassword.class.getSimpleName();
    private EditText etMobile, etPassword, etNewPassword;
    private Button buttonSubmitResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        etMobile = findViewById(R.id.editTextMobileResetPass);
        etPassword = findViewById(R.id.editTextPasswordResetPass);
        etNewPassword = findViewById(R.id.editTextNewPasswordResetPass);
        buttonSubmitResetPass = findViewById(R.id.buttonSubmitResetPass);

        buttonSubmitResetPass.setOnClickListener(new View.OnClickListener() {
            final String mobile = etMobile.getText().toString();
            final String password = etPassword.getText().toString();
            final String newpassword = etNewPassword.getText().toString();

            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constant.RESET_PASSWORD_URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG,response.toString());

                        try {
                            JSONArray jsonArray = response.getJSONArray("msg");
                            String msg = jsonArray.getString(0);
                            Log.d(TAG, msg);
                            if(msg.equals("password reset successfully")) {
                                Intent intent = new Intent(ResetPassword.this, CategoryActivity.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(ResetPassword.this, msg, Toast.LENGTH_LONG).show();

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
                        params.put("mobile", mobile);
                        params.put("password", password);
                        params.put("newpassword", newpassword);
                        return params;
                    }
                };
                MySingleton.getInstance(getApplicationContext()).getRequestQueue().add(jsonObjectRequest);
            }
        });


    }
}