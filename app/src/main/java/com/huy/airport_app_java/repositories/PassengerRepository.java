package com.huy.airport_app_java.repositories;

import com.huy.airport_app_java.database.SQLServerConnector;
import com.huy.airport_app_java.models.Passenger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PassengerRepository {

    // Lấy danh sách hành khách theo flightId
    public List<Passenger> getPassengersByFlightId(int flightId) {
        List<Passenger> passengers = new ArrayList<>();
        String query = "SELECT * FROM passengers WHERE flightId = ?";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, flightId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                passengers.add(new Passenger(
                        rs.getInt("flightId"),
                        rs.getString("name"),
                        rs.getString("dateOfBirth"),
                        rs.getString("passportNumber"),
                        rs.getString("nationality"),
                        rs.getString("phoneNumber"),
                        rs.getString("departurePoint"),
                        rs.getString("destinationPoint"),
                        rs.getString("cabinClass"),
                        rs.getString("seatNumber")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passengers;
    }

    // Lấy chi tiết hành khách theo id
    public Passenger getPassengerById(int passengerId) {
        Passenger passenger = null;
        String query = "SELECT TOP 1 * FROM passengers WHERE id = ?";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, passengerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                passenger = new Passenger(
                        rs.getInt("flightId"),
                        rs.getString("name"),
                        rs.getString("dateOfBirth"),
                        rs.getString("passportNumber"),
                        rs.getString("nationality"),
                        rs.getString("phoneNumber"),
                        rs.getString("departurePoint"),
                        rs.getString("destinationPoint"),
                        rs.getString("cabinClass"),
                        rs.getString("seatNumber")
                );
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return passenger;
    }

    // Thêm hành khách mới
    public void insertPassenger(Passenger passenger) {
        String query = "INSERT INTO passengers (flightId, name, dateOfBirth, passportNumber, nationality, phoneNumber, departurePoint, destinationPoint, cabinClass, seatNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, passenger.getFlightId());
            stmt.setString(2, passenger.getName());
            stmt.setString(3, passenger.getDateOfBirth());
            stmt.setString(4, passenger.getPassportNumber());
            stmt.setString(5, passenger.getNationality());
            stmt.setString(6, passenger.getPhoneNumber());
            stmt.setString(7, passenger.getDeparturePoint());
            stmt.setString(8, passenger.getDestinationPoint());
            stmt.setString(9, passenger.getCabinClass());
            stmt.setString(10, passenger.getSeatNumber());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật thông tin hành khách
    public void updatePassenger(Passenger passenger) {
        String query = "UPDATE passengers SET name = ?, dateOfBirth = ?, passportNumber = ?, nationality = ?, phoneNumber = ?, departurePoint = ?, destinationPoint = ?, cabinClass = ?, seatNumber = ? WHERE id = ?";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, passenger.getName());
            stmt.setString(2, passenger.getDateOfBirth());
            stmt.setString(3, passenger.getPassportNumber());
            stmt.setString(4, passenger.getNationality());
            stmt.setString(5, passenger.getPhoneNumber());
            stmt.setString(6, passenger.getDeparturePoint());
            stmt.setString(7, passenger.getDestinationPoint());
            stmt.setString(8, passenger.getCabinClass());
            stmt.setString(9, passenger.getSeatNumber());
            stmt.setInt(10, passenger.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa hành khách
    public void deletePassenger(Passenger passenger) {
        String query = "DELETE FROM passengers WHERE id = ?";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, passenger.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
