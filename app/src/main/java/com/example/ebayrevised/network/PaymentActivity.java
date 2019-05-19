package com.example.ebayrevised.network;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import com.example.ebayrevised.R;
import com.example.ebayrevised.ui.OrderSummaryActivity;
import com.simplify.android.sdk.Card;
import com.simplify.android.sdk.CardToken;
import com.simplify.android.sdk.Simplify;

public class PaymentActivity extends AppCompatActivity {

    private Simplify simplify;
    private Button buttonPay;
    private EditText etCardNumber, etExpiryMonth, etExpiryYear, etCvcNumber;
    TextView textViewTotalAmount;
    private static final String TAG = PaymentActivity.class.getSimpleName();
    ProgressDialog progressDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        String totalAmount = getIntent().getStringExtra("totalAmount");
        textViewTotalAmount.setText("Total Amount :" + totalAmount);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Card card = new Card()
                        .setNumber(etCardNumber.getText().toString())
                        .setExpYear(etExpiryYear.getText().toString())
                        .setExpMonth(etExpiryMonth.getText().toString())
                        .setCvc(etCvcNumber.getText().toString());

                simplify.createCardToken(card, new CardToken.Callback() {
                    @Override
                    public void onSuccess(CardToken cardToken) {
                        Log.e(TAG,"Transaction ID :" + cardToken.getId());
                        SharedPreferences sharedPreferences = getSharedPreferences("userPref",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("transactionId",cardToken.getId());
                        editor.apply();

                        progressDialog.dismiss();
                        Intent intent = new Intent(PaymentActivity.this, OrderSummaryActivity.class);
                        //intent.putExtra("transactionId",cardToken.getId());
                        startActivity(intent);


                    }

                    @Override
                    public void onError(Throwable throwable) {
                        //Log.e(TAG,"Transaction ID :" + throwable.getMessage());
                        Toast.makeText(PaymentActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                });

            }
        });

        simplify = new Simplify();
        simplify.setApiKey("sbpb_YWJkODBkZDktNWE0Ny00ZGI5LThhMzgtMzA1MzNlMTljNzk4");

    }

    private void init() {
        etCardNumber = findViewById(R.id.etCardNumber);
        etExpiryMonth = findViewById(R.id.etExpiryMonth);
        etExpiryYear = findViewById(R.id.etExpiryYear);
        etCvcNumber = findViewById(R.id.etCvcNumber);
        buttonPay = findViewById(R.id.buttonPay);
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount);
    }
}
