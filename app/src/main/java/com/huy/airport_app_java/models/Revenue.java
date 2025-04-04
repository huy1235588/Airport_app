package com.huy.airport_app_java.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "revenues")
public class Revenue {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String date; // format: YYYY-MM-DD
    public double amount;

    public Revenue(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
