package com.example.simplemobilebanking.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.simplemobilebanking.user.MainActivity;
import com.example.simplemobilebanking.R;

public class SplashScreenActivity extends AppCompatActivity implements SplashScreenActivitys {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        onBindView();
    }
    @Override
    public void onBindView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}