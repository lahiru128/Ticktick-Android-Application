package com.example.ticktick;

import android.content.Intent;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.Adapter.DashboardExpenseAdapter;
import com.example.ticktick.Adapter.DashboardIncomeAdapter;
import com.example.ticktick.Adapter.MyAdapter;
import com.example.ticktick.Model.Expenses;
import com.example.ticktick.Model.Incomes;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class DashBoard extends AppCompatActivity {
    DashboardIncomeAdapter incomeAdapter;
    DashboardExpenseAdapter expenseAdapter;
    RecyclerView dashboardRecyclerView;
    DatabaseReference incomeDb;
    DatabaseReference expenseDb;
    private TextView incomeTotal;
    private TextView expenseTotal;
    private TextView balance;
    private ImageButton incomeButton;
    private ImageButton expenseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toast.makeText(DashBoard.this, "Firebase connection is success", Toast.LENGTH_LONG).show();

        incomeDb = FirebaseDatabase.getInstance().getReference().child("Incomes");
        expenseDb = FirebaseDatabase.getInstance().getReference().child("Expenses");

        incomeTotal = (TextView) findViewById(R.id.incomeResultDashboard);
        expenseTotal = (TextView) findViewById(R.id.expensesResultDashboard);

        incomeDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalIncomeSum =0;
                for (DataSnapshot mysnap:snapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) mysnap.getValue();
                    Object incomeAmount = map.get("incomeAmount");
                    double pValue = Double.parseDouble(String.valueOf(incomeAmount));
                    totalIncomeSum += pValue;

                    incomeTotal.setText(String.valueOf(totalIncomeSum));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        expenseDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalExpenseSum =0;
                for (DataSnapshot mysnap:snapshot.getChildren()){
                    Map<String,Object> map = (Map<String, Object>) mysnap.getValue();
                    Object expenseAmount = map.get("expenseAmount");
                    double pValue = Double.parseDouble(String.valueOf(expenseAmount));
                    totalExpenseSum += pValue;
                    expenseTotal.setText(String.valueOf(totalExpenseSum));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
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

        incomeButton = (ImageButton) findViewById(R.id.ViewIncomeBtn);
        incomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIncomeView();
            }
        });

        expenseButton = (ImageButton) findViewById(R.id.ViewExpenseBtn);
        expenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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