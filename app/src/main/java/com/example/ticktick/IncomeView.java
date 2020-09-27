package com.example.ticktick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.Adapter.MyAdapter;
import com.example.ticktick.Model.Incomes;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class IncomeView extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter adapter;
    ImageButton addIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_view);

        addIncome = (ImageButton) findViewById(R.id.incomeAddBtn);
        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIncomeAddFrom();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.incomeRecycleView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false));

        FirebaseRecyclerOptions<Incomes> options =
                new FirebaseRecyclerOptions.Builder<Incomes>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Incomes"), Incomes.class)
                        .build();
        adapter = new MyAdapter(options);
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

    public void openIncomeAddFrom() {
        Intent intent = new Intent(this, AddIncome.class);
        startActivity(intent);
    }
}