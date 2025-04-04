package com.huy.airport_app_java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLServerConnector {
    // Cấu hình kết nối (cập nhật theo server của bạn)
    private static final String URL = "jdbc:jtds:sqlserver://192.168.1.13:1433/Airport_1";
    private static final String USER = "sa";
    private static final String PASSWORD = "sa";
    private static Connection connection;

    // Phương thức để lấy kết nối
    public static synchronized Connection getConnection() throws SQLException {
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
