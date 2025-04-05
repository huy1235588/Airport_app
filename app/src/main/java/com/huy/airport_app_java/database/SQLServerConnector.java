package com.huy.airport_app_java.database;

import android.util.Log;

import com.huy.airport_app_java.BuildConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnector {
    // Cấu hình kết nối (cập nhật theo server của bạn)
    private static final String URL = BuildConfig.URL_SQLSERVER;

    private static final String USER = "sa";
    private static final String PASSWORD = "sa";
    private static Connection connection;

    // Phương thức để lấy kết nối
    public static synchronized Connection getConnection() throws SQLException {
        Log.d("TAG", "URL: " + URL);
        if (connection == null || connection.isClosed()) {
            try {
                // Nạp driver JDBC cho SQL Server (với jTDS)
                Class.forName("net.sourceforge.jtds.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver JDBC không được tìm thấy.", e);
            }
        }
        return connection;
    }

    // Phương thức để đóng kết nối
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
