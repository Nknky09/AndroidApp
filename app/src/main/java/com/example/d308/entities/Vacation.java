package com.example.d308.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    private int vacationID;
    private String vacationName;
    private double price;
    private String startVacationDate;

    private String endVacationDate;
    private String hotel;

    public Vacation(int vacationID, String vacationName, double price, String hotel, String startVacationDate, String endVacationDate) {
        this.vacationID = vacationID;
        this.vacationName = vacationName;
        this.price = price;
        this.startVacationDate = startVacationDate;
        this.endVacationDate = endVacationDate;
        this.hotel = hotel;
    }

    public int getVacationID() {
        return vacationID;
    }

    public void setVacationID(int vacationID) {
        this.vacationID = vacationID;
    }

    public String getVacationName() {
        return vacationName;
    }

    public void setVacationName(String vacationName) {
        this.vacationName = vacationName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStartVacationDate() {
        return startVacationDate;
    }

    public void setStartVacationDate(String startVacationDate) {
        this.startVacationDate = startVacationDate;
    }

    public String getEndVacationDate() {
        return endVacationDate;
    }

    public void setEndVacationDate(String endVacationDate) {
        this.endVacationDate = endVacationDate;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String toString() {
        return vacationName;
    }

}
