package com.huy.airport_app_java.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.models.User;
import com.huy.airport_app_java.utils.SharedPreferencesManager;
import com.huy.airport_app_java.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_login);

        // Khởi tạo SharedPreferencesManager
        sharedPreferencesManager = new SharedPreferencesManager(this);
        
        // Ánh xạ các view từ layout
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        cbRemember = findViewById(R.id.cbRemember);
        btnLogin = findViewById(R.id.btnLogin);

        // Tải thông tin đăng nhập đã lưu nếu có
        etUsername.setText(sharedPreferencesManager.getSavedEmail());
        etPassword.setText(sharedPreferencesManager.getSavedPassword());
        cbRemember.setChecked(sharedPreferencesManager.hasSavedCredentials());

        // Khởi tạo và quan sát ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.userLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(user != null) {
                    // Lưu thông tin đăng nhập nếu checkbox "Ghi nhớ" được chọn
                    if(cbRemember.isChecked()) {
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
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện click nút đăng nhập
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                loginViewModel.login(username, password);
            }
        });
    }
}
