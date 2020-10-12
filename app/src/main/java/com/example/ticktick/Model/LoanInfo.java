package com.example.ticktick.model;

public class LoanInfo {

    private String name, date, amount, description, loanID;

    public LoanInfo() {
    }

    public LoanInfo(String name, String date, String amount, String description, String loanID) {
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.loanID = loanID;
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

    public String getLoanID() {
        return loanID;
    }

    public void setLoanID(String loanID) {
        this.loanID = loanID;
    }
}
