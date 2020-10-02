package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExpensesView extends AppCompatActivity {

    Button addExpenses;
    Button updateExpenses;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_view);

        addExpenses = (Button) findViewById(R.id.expensesAddBtn);
        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddExpensesForm();
            }
        });

        updateExpenses = (Button) findViewById(R.id.ExpenseUpdateBtn);
        updateExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateExpensesForm();
            }
        });
    }

    public void openAddExpensesForm(){
        Intent intent = new Intent(this,AddExpenses.class);
        startActivity(intent);
    }

    public void  openUpdateExpensesForm(){
        Intent intent = new Intent(this,ExpenseUpdate.class);
        startActivity(intent);
    }


}