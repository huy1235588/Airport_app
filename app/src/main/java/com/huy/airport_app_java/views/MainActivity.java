package com.huy.airport_app_java.views;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    // Các thành phần UI
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo và cấu hình các thành phần UI
        initViews();
        setupDrawerLayout();
        setupUserInfo();
    }

    /**
     * Khởi tạo các view
     */
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * Cấu hình navigation drawer
     */
    private void setupDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * Thiết lập thông tin người dùng trong header của navigation drawer
     */
    private void setupUserInfo() {
        View headerView = navigationView.getHeaderView(0);
        ImageView ivAvatar = headerView.findViewById(R.id.ivAvatar);
        TextView tvName = headerView.findViewById(R.id.tvName);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);

        // Lấy thông tin người dùng từ intent
        String name = getIntent().getStringExtra("userName");
        String email = getIntent().getStringExtra("userEmail");

        // Hiển thị thông tin người dùng nếu có
        if (name != null) tvName.setText(name);
        if (email != null) tvEmail.setText(email);
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
        drawerLayout.closeDrawer(GravityCompat.START);
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
