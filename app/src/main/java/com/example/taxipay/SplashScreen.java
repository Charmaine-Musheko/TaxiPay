package com.example.taxipay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends Activity {
    ProgressBar nProgressBar;
    TextView pay;
    ImageView taxi,frame;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        pay = findViewById(R.id.textView2);
        taxi = findViewById(R.id.for_taxi);
        frame = findViewById(R.id.frame_back);

        nProgressBar = findViewById(R.id.progressBar3);
        nProgressBar.setVisibility(View.VISIBLE);
        int secondsDelayed = 5;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(SplashScreen.this, Register.class));
                finish();
            }
        }, secondsDelayed * 5000);
    }
}

