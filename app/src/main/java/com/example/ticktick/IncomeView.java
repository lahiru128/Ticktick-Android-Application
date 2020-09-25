package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ticktick.Adapter.MyAdapter;
import com.example.ticktick.Model.Incomes;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class IncomeView extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;

    private Button addIncome;

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



        recyclerView=(RecyclerView)findViewById(R.id.incomeRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Incomes> options =
                new FirebaseRecyclerOptions.Builder<Incomes>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Incomes"), Incomes.class)
                        .build();

        adapter =new MyAdapter(options);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
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