package com.example.ticktick.Model;

public class Incomes {

    String incomeId;
    String incomeName;
    String incomeAmount;
    String incomeNote;

    public Incomes() {
    }

    public Incomes(String incomeId,String incomeName, String incomeAmount, String incomeNote) {
        this.incomeId = incomeId;
        this.incomeName = incomeName;
        this.incomeAmount = incomeAmount;
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
