package com.huy.airport_app_java.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {
    private static final String PREF_NAME = "VN_Airlines_PREF";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;

    public SharedPreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // Lưu thông tin đăng nhập
    public void saveLoginCredentials(String email, String password) {
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    // Xóa thông tin đăng nhập đã lưu
    public void clearLoginCredentials() {
        editor.clear();
        editor.apply();
    }

    // Lấy thông tin đăng nhập đã lưu
    public String getSavedEmail() {
        return sharedPreferences.getString(KEY_EMAIL, "");
    }

    public String getSavedPassword() {
        return sharedPreferences.getString(KEY_PASSWORD, "");
    }

    // Kiểm tra xem có thông tin đăng nhập đã lưu hay không
    public boolean hasSavedCredentials() {
        return !getSavedEmail().isEmpty();
    }
} 