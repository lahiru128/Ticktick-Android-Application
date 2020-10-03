package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.ticktick.Model.Incomes;
import com.example.ticktick.Model.Todo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class TodoAdd extends AppCompatActivity {

    EditText title, date, note;
    Button btnTodoSave,btnTodoCancel;
    DatabaseReference databaseTodo;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_add);

        //Get instances from the database
        databaseTodo = FirebaseDatabase.getInstance().getReference("Todo");

        //Assign Variables
        title = (EditText) findViewById(R.id.todoTitleAdd);
        date = (EditText) findViewById(R.id.todoDateAdd);
        note = (EditText) findViewById(R.id.todoNoteAdd);
        btnTodoSave = (Button) findViewById(R.id.todoSaveBtn);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(BASIC);

        //Add Validations For Amount
        awesomeValidation.addValidation(TodoAdd.this, R.id.todoTitleAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_title);
        awesomeValidation.addValidation(TodoAdd.this, R.id.todoDateAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_date);

        //onclick listener for save button and validations
        btnTodoSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check validation
                if (awesomeValidation.validate()) {
                    //On Success
                    addTodo();
                } else {
                    Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Onclick listener for cancellation button
        btnTodoCancel = (Button) findViewById(R.id.todoCancelBtn);
        btnTodoCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTodoView();
            }
        });
    }

    //Add income method
    private void addTodo() {
        String todoTitle = title.getText().toString().trim();
        String todoDate = date.getText().toString().trim();
        String todoNote = note.getText().toString().trim();

        //Check whether the amount is entered
        if (!TextUtils.isEmpty(todoTitle)) {
            String id = databaseTodo.push().getKey();
            Todo todo = new Todo(id, todoTitle, todoDate, todoNote);
            databaseTodo.child(id).setValue(todo);
            Toast.makeText(this, "Todo added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You Should enter a Title", Toast.LENGTH_LONG).show();
        }
    }


    //Cancellation button
    public void openTodoView() {
        Intent intent = new Intent(this, TodoView.class);
        startActivity(intent);
    }


}