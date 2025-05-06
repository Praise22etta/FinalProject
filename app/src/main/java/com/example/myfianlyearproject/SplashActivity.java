package com.example.myfianlyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Log.d("SplashActivity", "Splash started...");

        // Delay for 4 seconds, then open InfoActivity
        new Handler().postDelayed(() -> {
            Log.d("SplashActivity", "Navigating to InfoActivity...");
            Intent intent = new Intent(SplashActivity.this, InfoActivity.class);
            startActivity(intent);
            finish(); // Close SplashActivity
        }, 2000); // 2000 milliseconds = 2 seconds
    }
}
