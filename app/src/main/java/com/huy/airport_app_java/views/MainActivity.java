package com.huy.airport_app_java.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.huy.airport_app_java.R;
import com.huy.airport_app_java.databinding.ActivityMainBinding;
import com.huy.airport_app_java.utils.SharedPreferencesManager;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Khởi tạo và cấu hình các thành phần UI
        initViews();
        setupDrawerLayout();
        setupUserInfo();
    }

    /**
     * Khởi tạo các view
     */
    private void initViews() {
        setSupportActionBar(binding.toolbar);
        binding.navView.setNavigationItemSelectedListener(this);
    }

    /**
     * Cấu hình navigation drawer
     */
    private void setupDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Thiết lập thông tin người dùng trong header của navigation drawer
     */
    private void setupUserInfo() {
        View headerView = binding.navView.getHeaderView(0);
        TextView tvName = headerView.findViewById(R.id.tvName);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);

        // Lấy thông tin người dùng từ intent hoặc SharedPreferences
        String name = getIntent().getStringExtra("userName");
        String email = getIntent().getStringExtra("userEmail");

        // Nếu không có trong intent, lấy từ SharedPreferences
        if (name == null || email == null) {
            SharedPreferencesManager sharedPreferencesManager = new SharedPreferencesManager(this);
            name = sharedPreferencesManager.getUserName();
            email = sharedPreferencesManager.getUserEmail();
        }

        // Hiển thị thông tin người dùng nếu có
        if (name != null && !name.isEmpty()) tvName.setText(name);
        if (email != null && !email.isEmpty()) tvEmail.setText(email);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Xử lý sự kiện khi người dùng chọn item trong navigation drawer
        Intent intent = null;
        int itemId = item.getItemId();

        // Chọn item tương ứng với ID
        if (itemId == R.id.nav_flights) {
            intent = new Intent(this, FlightListActivity.class);
        } else if (itemId == R.id.nav_revenue) {
            intent = new Intent(this, RevenueStatisticsActivity.class);
        } else if (itemId == R.id.nav_settings) {
            intent = new Intent(this, SettingsActivity.class);
        } else if (itemId == R.id.nav_logout) {
            handleLogout();
            return true;
        }

        // Chuyển đến màn hình tương ứng nếu có intent
        if (intent != null) {
            startActivity(intent);
        }

        // Đóng drawer
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Xử lý đăng xuất
     */
    private void handleLogout() {
        // Xóa thông tin đăng nhập đã lưu
        getSharedPreferences("VN_Airlines_PREF", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        // Chuyển về màn hình đăng nhập
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // Xử lý nút back
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
