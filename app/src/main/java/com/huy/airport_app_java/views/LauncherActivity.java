package com.huy.airport_app_java.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.huy.airport_app_java.R;
import com.huy.airport_app_java.utils.SharedPreferencesManager;

public class LauncherActivity extends AppCompatActivity {
    private static final long SPLASH_DELAY = 2000; // 2 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        // Khởi tạo SharedPreferencesManager để quản lý thông tin đăng nhập
        SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
        
        // Delay chuyển đổi giữa các Activity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent;
            
            // Kiểm tra xem có thông tin đăng nhập đã lưu hay không
            if (sharedPreferencesManager.hasSavedCredentials()) {
                // Nếu có thông tin đăng nhập đã lưu, chuyển đến MainActivity
                intent = new Intent(LauncherActivity.this, MainActivity.class);
            } else {
                // Nếu không có thông tin đăng nhập đã lưu, chuyển đến LoginActivity
                intent = new Intent(LauncherActivity.this, LoginActivity.class);
            }
            
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
} 