package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btntodo;
    private Button btnincom;


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
    }

    public void openTodoView() {
        Intent intent = new Intent(this, TodoView.class);
        startActivity(intent);
    }

    public void openDashBoard() {
        Intent intent = new Intent(this, DashBoard.class);
        startActivity(intent);
    }



}