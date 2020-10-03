package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ticktick.Adapter.ExpenseAdapter;
import com.example.ticktick.Model.Expenses;
import com.example.ticktick.Model.Incomes;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ExpensesView extends AppCompatActivity {

    ImageButton addExpenses;
    RecyclerView recyclerViewExpense;
    ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_view);

        addExpenses = (ImageButton) findViewById(R.id.expenseAddBtn);
        addExpenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddExpensesForm();
            }
        });
        recyclerViewExpense = (RecyclerView) findViewById(R.id.expenseRecycleView);
        recyclerViewExpense.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<Expenses> options =
                new FirebaseRecyclerOptions.Builder<Expenses>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Expenses"), Expenses.class)
                        .build();
        expenseAdapter = new ExpenseAdapter(options);
        recyclerViewExpense.setAdapter(expenseAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        expenseAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        expenseAdapter.stopListening();
    }

    public void openAddExpensesForm() {
        Intent intent = new Intent(this, AddExpenses.class);
        startActivity(intent);
    }
}