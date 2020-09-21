package com.example.ticktick.Model;

public class Incomes {

    String incomeId;
    String incomeAmount;
    String incomeName;
    String incomeNote;

    public Incomes() {
    }

    public Incomes(String incomeId, String incomeAmount, String incomeName, String incomeNote) {
        this.incomeId = incomeId;
        this.incomeAmount = incomeAmount;
        this.incomeName = incomeName;
        this.incomeNote = incomeNote;
    }

    public String getIncomeId() {
        return incomeId;
    }

    public String getIncomeAmount() {
        return incomeAmount;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public String getIncomeNote() {
        return incomeNote;
    }


}
