package com.example.ticktick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashBoard extends AppCompatActivity {

    private Button incomeButton;
    private Button expenseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toast.makeText(DashBoard.this, "Firebase connection is success", Toast.LENGTH_LONG).show();

        incomeButton = (Button) findViewById(R.id.ViewIncomeBtn);
        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIncomeView();
            }
        });
        expenseButton = (Button) findViewById(R.id.ViewExpenseBtn);
        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openExpensesView();
            }
        });
    }

    public void openIncomeView() {
        Intent intent = new Intent(this, IncomeView.class);
        startActivity(intent);
    }

    public void openExpensesView() {
        Intent intent = new Intent(this, ExpensesView.class);
        startActivity(intent);
    }
}