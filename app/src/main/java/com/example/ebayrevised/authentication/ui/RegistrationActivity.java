package com.example.ebayrevised.authentication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.ebayrevised.network.MySingleton;
import com.example.ebayrevised.R;
import com.example.ebayrevised.util.Constant;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {
    private Button registerButton;
    private EditText editTextFname,editTextLname,editTextAddress,editTextMobile,editTextEmail,editTextPassword;
    private final String TAG = RegistrationActivity.class.getSimpleName();
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //initialize the views
        init();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(Patterns.EMAIL_ADDRESS.matcher(editTextEmail.getText().toString()).matches())){
                    editTextEmail.setError("Please enter a valid email address");
                }
                else if(editTextEmail.getText().toString().equals("") || editTextMobile.getText().toString().equals(""))
                    Toast.makeText(RegistrationActivity.this, "Any field cannot be empty", Toast.LENGTH_SHORT).show();
                else if(!(editTextMobile.getText().toString().matches("[0-9]{10}")))
                    editTextMobile.setError("Please enter a valid mobile number");
                else if (!(editTextMobile.getText().toString().equals("")) || !(editTextEmail.getText().toString().equals("")) || !(editTextPassword.getText().toString().equals(""))) {
                    sharedPreferences = getSharedPreferences("userPref",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("address", editTextAddress.getText().toString());
                    editor.apply();
                    registerUser(editTextFname.getText().toString(), editTextLname.getText().toString(), editTextAddress.getText().toString(),
                            editTextMobile.getText().toString(), editTextEmail.getText().toString(), editTextPassword.getText().toString());
                }
            }
        });
    }

    private void init() {
        registerButton = findViewById(R.id.buttonRegisterFinal);
        editTextFname = findViewById(R.id.etFirstName);
        editTextLname = findViewById(R.id.etLastName);
        editTextAddress = findViewById(R.id.etAddress);
        editTextMobile = findViewById(R.id.etMobile);
        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword = findViewById(R.id.etPassword);
    }

    /**
     *
     * register the user with given parameters
     * @param fname
     * @param lname
     * @param address
     * @param mobile
     * @param email
     * @param password
     */
    private void registerUser(final String fname, final String lname, final String address, final String mobile, final String email, final String password) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.REGISTRATION_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"response" + response);
                if(response.equals(getString(R.string.str_successfully_registered))) {
                    Toast.makeText(RegistrationActivity.this, response, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(RegistrationActivity.this,  response, Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"error :" + error.getMessage());
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fname", fname);
                params.put("lname", lname);
                params.put("address", address);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);

                return params;
            }
        };
        MySingleton.getInstance(this).getRequestQueue().add(stringRequest);
    }
}
