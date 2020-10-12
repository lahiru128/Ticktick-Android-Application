package com.example.ticktick.model;

public class LendingInfo {

    private String name, date, amount, description, lendingID;

    public LendingInfo() {
    }

    public LendingInfo(String name, String date, String amount, String description, String lendingID) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.lendingID = lendingID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLendingID() {
        return lendingID;
    }

    public void setLendingID(String lendingID) {
        this.lendingID = lendingID;
    }
}
