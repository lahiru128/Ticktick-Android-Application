package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.ticktick.Adapter.TodoAdapter;
import com.example.ticktick.Model.Todo;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class TodoView extends AppCompatActivity {

    RecyclerView recyclerView;
    TodoAdapter adapter;

    ImageView addTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_view);

        addTodo = (ImageView) findViewById(R.id.todoAddBtn);
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodoAddFrom();
            }
        });



        recyclerView=(RecyclerView)findViewById(R.id.todoRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Todo> options =
                new FirebaseRecyclerOptions.Builder<Todo>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Todo"), Todo.class)
                        .build();

        adapter =new TodoAdapter(options);
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
    public void openTodoAddFrom(){
        Intent intent = new Intent(this,TodoAdd.class);
        startActivity(intent);
    }

    public void openTodoUpdateForm(){
        Intent intent = new Intent(this,TodoUpdate.class);
        startActivity(intent);
    }
}