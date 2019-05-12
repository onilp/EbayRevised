package com.example.ebayrevised.authentication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ebayrevised.CategoryActivity;
import com.example.ebayrevised.R;
import com.example.ebayrevised.network.MySingleton;
import com.util.Constant;
import com.util.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView textViewResetPassword;
    private Button loginButton;
    private EditText editTextMobile, editTextPassword;
    private final String TAG = LoginActivity.class.getSimpleName();
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        textViewResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ResetPassword.class);
                startActivity(intent);
            }
        });
    }

    /**
     * login the user with credentials
     */
    private void loginUser() {
        final String mobile = editTextMobile.getText().toString();
        final String password = editTextPassword.getText().toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Constant.LOGIN_URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    Log.d(TAG,"Response :" + response.toString());
                    if(response.getJSONObject(0).getString("msg").equals("success")){
                        Log.d(TAG, "Testing: " + response.getJSONObject(0).getString("msg"));
                        Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                        startActivity(intent);

                        String id = response.getJSONObject(0).getString("id");
                        String firstname =response.getJSONObject(0).getString("firstname");
                        String lastname = response.getJSONObject(0).getString("lastname");
                        String email = response.getJSONObject(0).getString("email");
                        String mobile = response.getJSONObject(0).getString("mobile");
                        String appapikey = response.getJSONObject(0).getString("appapikey ");

                        sharedPreferences = getSharedPreferences("userPref",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        Log.d(TAG,"api key" + appapikey);
                        editor.putString("id", id);
                        editor.putString("firstname",firstname);
                        editor.putString("lastname",lastname);
                        editor.putString("email",email);
                        editor.putString("mobile",mobile);
                        editor.putString("appapikey", appapikey);
                        editor.apply();

                    }
                    else if(response.getJSONObject(0).getString("msg").equals("Mobile number not register")){
                        Toast.makeText(LoginActivity.this, response.getJSONObject(0).getString( "msg"), Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(LoginActivity.this, "Mobile or Username do not match.", Toast.LENGTH_SHORT).show();

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,error.getMessage());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("mobile", mobile);
                params.put("password", password);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).getRequestQueue().add(jsonArrayRequest);
    }

    private void init() {
        loginButton = findViewById(R.id.loginButtonLoginScreen);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewResetPassword =findViewById(R.id.textViewResetPassoword);
    }
}