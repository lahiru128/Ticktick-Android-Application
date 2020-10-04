package com.example.ticktick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btntodo;
    private Button btnincom;
    private Button btnloan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btntodo = (Button) findViewById(R.id.btntodo);
        btntodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodoView();
            }
        });
        btnincom = (Button) findViewById(R.id.btnincom);
        btnincom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashBoard();
            }
        });
        btnloan = (Button) findViewById(R.id.btnloan);
        btnloan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFinalActivity();
            }
        });
    }

    public void openTodoView() {
        Intent intent = new Intent(this, TodoView.class);
        startActivity(intent);
    }

    public void openDashBoard() {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
    }
    public void openFinalActivity() {
        Intent intent = new Intent(this, FinalActivity.class);
        startActivity(intent);
    }



}