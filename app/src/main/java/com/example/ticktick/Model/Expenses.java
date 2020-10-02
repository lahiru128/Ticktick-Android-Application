package com.example.ticktick.Model;

public class Expenses {

    String expenseId;
    String expenseAmount;
    String expenseName;
    String expenseNote;

    public Expenses() {
    }

    public Expenses(String expenseId, String expenseAmount, String expenseName, String expenseNote) {
        this.expenseId = expenseId;
        this.expenseAmount = expenseAmount;
        this.expenseName = expenseName;
        this.expenseNote = expenseNote;
    }

    public String getExpenseId() {
        return expenseId;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getExpenseNote() {
        return expenseNote;
    }
}
