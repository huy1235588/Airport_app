package com.huy.airport_app_java.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "passengers")
public class Passenger {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int flightId; // foreign key reference to Flight
    public String name;
    public String dateOfBirth; // format: YYYY-MM-DD
    public String passportNumber;
    public String nationality;
    public String phoneNumber;
    public String departurePoint;
    public String destinationPoint;
    public String cabinClass; // e.g., "Phổ thông" or "Thương gia"
    public String seatNumber;

    public Passenger(int flightId, String name, String dateOfBirth, String passportNumber,
                     String nationality, String phoneNumber, String departurePoint,
                     String destinationPoint, String cabinClass, String seatNumber) {
        this.flightId = flightId;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.passportNumber = passportNumber;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.departurePoint = departurePoint;
        this.destinationPoint = destinationPoint;
        this.cabinClass = cabinClass;
        this.seatNumber = seatNumber;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDeparturePoint() {
        return departurePoint;
    }

    public void setDeparturePoint(String departurePoint) {
        this.departurePoint = departurePoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public String getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(String cabinClass) {
        this.cabinClass = cabinClass;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }
}
