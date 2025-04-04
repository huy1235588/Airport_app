package com.huy.airport_app_java.repositories;

import com.huy.airport_app_java.database.SQLServerConnector;
import com.huy.airport_app_java.models.Revenue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RevenueRepository {

    // Lấy doanh thu theo tháng (năm-tháng: "2024-05")
    public List<Revenue> getRevenuesByMonth(String yearMonth) {
        List<Revenue> revenues = new ArrayList<>();
        String query = "SELECT * FROM revenues WHERE CONVERT(varchar(7), date, 120) = ?";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, yearMonth);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                revenues.add(new Revenue(
                        rs.getString("date"),
                        rs.getDouble("amount")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenues;
    }

    // Thêm bản ghi doanh thu mới
    public void insertRevenue(Revenue revenue) {
        String query = "INSERT INTO revenues (date, amount) VALUES (?, ?)";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, revenue.getDate());
            stmt.setDouble(2, revenue.getAmount());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
