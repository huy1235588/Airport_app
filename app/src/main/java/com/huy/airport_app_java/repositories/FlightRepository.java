package com.huy.airport_app_java.repositories;

import com.huy.airport_app_java.database.SQLServerConnector;
import com.huy.airport_app_java.models.Flight;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlightRepository {

    // Lấy danh sách chuyến bay theo ngày
    public List<Flight> getFlightsByDate(String date) {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM flights WHERE flightDate = ?";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, date);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                flights.add(new Flight(
                        rs.getString("flightCode"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getString("departureTime"),
                        rs.getString("landingTime"),
                        rs.getString("flightDate")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    // Lấy tất cả chuyến bay
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();
        String query = "SELECT * FROM flights";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                flights.add(new Flight(
                        rs.getString("flightCode"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getString("departureTime"),
                        rs.getString("landingTime"),
                        rs.getString("flightDate")
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flights;
    }

    // Thêm chuyến bay mới
    public void insertFlight(Flight flight) {
        String query = "INSERT INTO flights (flightCode, departure, destination, departureTime, landingTime, flightDate) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = SQLServerConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, flight.getFlightCode());
            stmt.setString(2, flight.getDeparture());
            stmt.setString(3, flight.getDestination());
            stmt.setString(4, flight.getDepartureTime());
            stmt.setString(5, flight.getLandingTime());
            stmt.setString(6, flight.getFlightDate());
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
