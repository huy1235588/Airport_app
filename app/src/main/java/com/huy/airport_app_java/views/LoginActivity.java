package com.huy.airport_app_java.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.huy.airport_app_java.R;
import com.huy.airport_app_java.databinding.ActivityLoginBinding;
import com.huy.airport_app_java.models.User;
import com.huy.airport_app_java.utils.SharedPreferencesManager;
import com.huy.airport_app_java.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    // Các view trong layout
    private EditText etUsername, etPassword;  // Ô nhập tên đăng nhập và mật khẩu
    private CheckBox cbRemember;  // Checkbox ghi nhớ đăng nhập
    private Button btnLogin;  // Nút đăng nhập

    // ViewModel và SharedPreferences
    private LoginViewModel loginViewModel;  // ViewModel xử lý logic đăng nhập
    private SharedPreferencesManager sharedPreferencesManager;  // Quản lý thông tin đăng nhập

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo SharedPreferencesManager
        sharedPreferencesManager = new SharedPreferencesManager(this);

        // Tải thông tin đăng nhập đã lưu nếu có
        binding.etUsername.setText(sharedPreferencesManager.getSavedEmail());
        binding.etPassword.setText(sharedPreferencesManager.getSavedPassword());
        binding.cbRemember.setChecked(sharedPreferencesManager.hasSavedCredentials());

        // Khởi tạo và quan sát ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                // Đặt lại trạng thái của nút đăng nhập
                binding.btnLogin.setEnabled(true);

                if (user != null) {
                    // Lưu thông tin đăng nhập nếu checkbox "Ghi nhớ" được chọn
                    if (binding.cbRemember.isChecked()) {
                        sharedPreferencesManager.saveLoginCredentials(user.email, user.password);
                    } else {
                        sharedPreferencesManager.clearLoginCredentials();
                    }
                    // Chuyển đến MainActivity (Dashboard)
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
            // Kiểm tra và ngăn chặn click quá nhanh
            if (!binding.btnLogin.isEnabled()) return;

            // Lấy thông tin từ các ô nhập
            String username = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            // Kiểm tra xem các ô nhập có rỗng không
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
