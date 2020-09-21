package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IncomeView extends AppCompatActivity {
    private Button addIncome;
    private Button updateIncome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_view);

        addIncome = (Button) findViewById(R.id.incomeAddBtn);
        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIncomeAddFrom();
            }
        });

        updateIncome = (Button) findViewById(R.id.IncomeUpdateBtn);
        updateIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIncomeUpdateForm();
            }
        });
    }

    public void openIncomeAddFrom(){
        Intent intent = new Intent(this,AddIncome.class);
        startActivity(intent);
    }

    public void openIncomeUpdateForm(){
       Intent intent = new Intent(this,IncomeUpdate.class);
       startActivity(intent);
    }


}