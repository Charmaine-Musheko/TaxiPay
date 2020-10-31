package com.example.taxipay;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Payment extends AppCompatActivity implements PaymentResultListener {
    private TextView textViewResult;
    Button pay;
    String TAG = "Payment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);
        Checkout.preload(getApplicationContext());
        pay = findViewById(R.id.pay_button);
        String sAmount = "100";
        final int amount = Math.round(Float.parseFloat(sAmount) * 100);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout = new Checkout();
                checkout.setKeyID("<rzp_test_KRN7IqdNhqtqPq>");
                JSONObject options = new JSONObject();
                try {
                    options.put("name", "Merchant Name");
                    options.put("description", "Reference No. #123456");
                    options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
                    //from response of step 3.
                    options.put("theme.color", "#3399cc");
                    options.put("currency", "INR");
                    options.put("amount", amount);//pass amount in currency subunits
                    options.put("prefill.email", "gaurav.kumar@example.com");
                    options.put("prefill.contact", "9988776655");
                    checkout.open(Payment.this, options);
                } catch (Exception e) {
                    Log.e(TAG, "Error in starting Razorpay Checkout", e);
                }

            }
        });
    }


    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();


    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }
}