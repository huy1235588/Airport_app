package com.huy.airport_app_java.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.huy.airport_app_java.R;
import com.huy.airport_app_java.utils.SharedPreferencesManager;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // 2 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        // Initialize SharedPreferencesManager
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        
        // Use Handler to delay navigation
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            
            // Check if there are saved login credentials
            if (sharedPreferencesManager.hasSavedCredentials()) {
                // If credentials exist, go directly to MainActivity
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                // If no saved credentials, go to LoginActivity
                intent = new Intent(SplashActivity.this, LoginActivity.class);
            }
            
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
} 