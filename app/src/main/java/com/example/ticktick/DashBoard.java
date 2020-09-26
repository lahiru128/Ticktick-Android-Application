package com.example.ticktick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.Adapter.DashboardExpenseAdapter;
import com.example.ticktick.Adapter.DashboardIncomeAdapter;
import com.example.ticktick.Model.Expenses;
import com.example.ticktick.Model.Incomes;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DashBoard extends AppCompatActivity {
    DashboardIncomeAdapter incomeAdapter;
    DashboardExpenseAdapter expenseAdapter;
    RecyclerView dashboardRecyclerView;
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


        //Incomes HorizontalRecycler
        dashboardRecyclerView = (RecyclerView) findViewById(R.id.dashboardRecyclerView);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        FirebaseRecyclerOptions<Incomes> options1 =
                new FirebaseRecyclerOptions.Builder<Incomes>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Incomes"), Incomes.class)
                        .build();
        incomeAdapter = new DashboardIncomeAdapter(options1);
        dashboardRecyclerView.setAdapter(incomeAdapter);

        //Expenses HorizontalRecycler
        dashboardRecyclerView = (RecyclerView) findViewById(R.id.dashboardExRecyclerView);
        dashboardRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false));
        FirebaseRecyclerOptions<Expenses> options =
                new FirebaseRecyclerOptions.Builder<Expenses>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Expenses"), Expenses.class)
                        .build();
        expenseAdapter = new DashboardExpenseAdapter(options);
        dashboardRecyclerView.setAdapter(expenseAdapter);


    }

    public void openIncomeView() {
        Intent intent = new Intent(this, IncomeView.class);
        startActivity(intent);
    }

    public void openExpensesView() {
        Intent intent = new Intent(this, ExpensesView.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        incomeAdapter.startListening();
        expenseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        incomeAdapter.stopListening();
        expenseAdapter.stopListening();
    }


}