package com.example.d308.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "excursions")
public class Excursion {
    @PrimaryKey(autoGenerate = true)
    private int excursionID;
    private String excursionName;
    private double price;
    private int vacaID;
    private String excursionDate;

    public Excursion(int excursionID, String excursionName, double price, int vacaID, String excursionDate) {
        this.excursionID = excursionID;
        this.excursionName = excursionName;
        this.price = price;
        this.vacaID = vacaID;
        this.excursionDate = excursionDate;
    }

    public int getExcursionID() {
        return excursionID;
    }

    public void setExcursionID(int excursionID) {
        this.excursionID = excursionID;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getVacaID() {
        return vacaID;
    }

    public void setVacaID(int vacationID) {
        this.vacaID = vacationID;
    }

    public String getExcursionDate() {
        return excursionDate;
    }

    public void setExcursionDate(String excursionDate) {
        this.excursionDate = excursionDate;
    }

}
