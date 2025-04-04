package com.huy.airport_app_java.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.huy.airport_app_java.databinding.ActivityLoginBinding;
import com.huy.airport_app_java.models.User;
import com.huy.airport_app_java.utils.SharedPreferencesManager;
import com.huy.airport_app_java.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private LoginViewModel loginViewModel;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo SharedPreferencesManager để quản lý thông tin đăng nhập
        sharedPreferencesManager = new SharedPreferencesManager(this);

        // Tải thông tin đăng nhập đã lưu nếu có
        if (sharedPreferencesManager.hasSavedCredentials()) {
            binding.etUsername.setText(sharedPreferencesManager.getSavedEmail());
            binding.etPassword.setText(sharedPreferencesManager.getSavedPassword());
            binding.cbRemember.setChecked(true);
        }

        // Khởi tạo và theo dõi ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                binding.btnLogin.setEnabled(true);

                if (user != null) {
                    // Lưu thông tin đăng nhập nếu checkbox "Ghi nhớ" được chọn
                    if (binding.cbRemember.isChecked()) {
                        sharedPreferencesManager.saveLoginCredentials(user.email, user.password);
                    } else {
                        sharedPreferencesManager.clearLoginCredentials();
                    }
                    
                    // Chuyển đến MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("userName", user.firstName + " " + user.lastName);
                    intent.putExtra("userEmail", user.email);
                    startActivity(intent);
                    finish();
                } else {
                    binding.tvError.setText("Invalid credentials");
                    binding.tvError.setVisibility(TextView.VISIBLE);
                }
            }
        });

        // Xử lý sự kiện click nút đăng nhập
        binding.btnLogin.setOnClickListener(v -> {
            if (!binding.btnLogin.isEnabled()) return;

            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                binding.tvError.setText("Please enter username and password");
                binding.tvError.setVisibility(TextView.VISIBLE);
            } else {
                binding.tvError.setVisibility(TextView.GONE);
                binding.btnLogin.setEnabled(false);
                loginViewModel.login(username, password);
            }
        });
    }
}
