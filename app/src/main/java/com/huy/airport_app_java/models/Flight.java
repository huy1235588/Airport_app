package com.huy.airport_app_java.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flights")
public class Flight {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String flightCode;
    public String departure;
    public String destination;
    public String departureTime; // stored as String for simplicity
    public String landingTime;
    public String flightDate; // format YYYY-MM-DD

    public Flight(String flightCode, String departure, String destination, String departureTime, String landingTime, String flightDate) {
        this.flightCode = flightCode;
        this.departure = departure;
        this.destination = destination;
        this.departureTime = departureTime;
        this.landingTime = landingTime;
        this.flightDate = flightDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getLandingTime() {
        return landingTime;
    }

    public void setLandingTime(String landingTime) {
        this.landingTime = landingTime;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }
}