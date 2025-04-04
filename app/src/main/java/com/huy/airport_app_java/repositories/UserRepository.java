package com.huy.airport_app_java.repositories;

import com.huy.airport_app_java.database.SQLServerConnector;
import com.huy.airport_app_java.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    // Truy vấn đăng nhập và trả về đối tượng User nếu hợp lệ
    public User login(String email, String password) {
        User user = null;
        String query = "SELECT TOP 1 * FROM users WHERE email = ? AND password = ?";
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection conn = SQLServerConnector.getConnection();

            // Tạo PreparedStatement để thực hiện truy vấn
            PreparedStatement stmt = conn.prepareStatement(query);

            // Thiết lập tham số cho truy vấn
            stmt.setString(1, email);
            stmt.setString(2, password);

            // Thực hiện truy vấn và lấy kết quả
            ResultSet rs = stmt.executeQuery();

            // Nếu có kết quả, tạo đối tượng User từ dữ liệu
            if (rs.next()) {
                user = new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getInt("officeId"),
                        rs.getString("birthDate"),
                        rs.getBoolean("active")
                );
            }

            // Đóng ResultSet và PreparedStatement
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // Thêm người dùng mới (nếu cần)
    public void insertUser(User user) {
        String query = "INSERT INTO users (email, password, firstName, lastName, officeId, birthDate, active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            // Kết nối đến cơ sở dữ liệu
            Connection conn = SQLServerConnector.getConnection();

            // Tạo PreparedStatement để thực hiện truy vấn
            PreparedStatement stmt = conn.prepareStatement(query);

            // Thiết lập tham số cho truy vấn
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setInt(5, user.getOfficeId());
            stmt.setString(6, user.getBirthDate());
            stmt.setBoolean(7, user.getActive());

            // Thực hiện truy vấn
            stmt.executeUpdate();

            // Đóng PreparedStatement
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
