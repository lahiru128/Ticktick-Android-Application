package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.ticktick.Model.Expenses;
import com.example.ticktick.Model.Incomes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExpenses extends AppCompatActivity {

    EditText e_amount,e_name,e_note;
    ImageButton btnExpenseSave,btnExpenseCancel;
    DatabaseReference databaseExpenses;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expenses);

        //Get instances from the database
        databaseExpenses = FirebaseDatabase.getInstance().getReference("Expenses");

        //Assign Variables
        e_amount = (EditText) findViewById(R.id.expenseAmountAdd);
        e_name = (EditText) findViewById(R.id.expenseNameAdd);
        e_note = (EditText) findViewById(R.id.expenseNoteAdd);
        btnExpenseSave = (ImageButton) findViewById(R.id.expensesSaveBtn);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //Add Validations For Amount And Name
        awesomeValidation.addValidation(AddExpenses.this,R.id.expenseAmountAdd, RegexTemplate.NOT_EMPTY,R.string.invalid_eamount);
        awesomeValidation.addValidation(AddExpenses.this,R.id.expenseNameAdd, RegexTemplate.NOT_EMPTY,R.string.invalid_ename
        );

        //onClick Listener
        btnExpenseSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check Validations
                if (awesomeValidation.validate()){
                    //On success
                    addExpense();
                    openExpenseView();
                }else {
                    Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Onclick listener for cancellation button
        btnExpenseCancel = (ImageButton) findViewById(R.id.expensesCancelBtn);
        btnExpenseCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExpenseView();
            }
        });
    }

    //Add Expense Method
    private void addExpense(){
        String expenseAmount = e_amount.getText().toString().trim();
        String expenseName = e_name.getText().toString().trim();
        String expenseNote = e_note.getText().toString().trim();

        //Check Whether the amount is entered
        if(!TextUtils.isEmpty(expenseAmount)){
            String id = databaseExpenses.push().getKey();
            Expenses expenses = new Expenses(id,expenseAmount,expenseName,expenseNote);
            databaseExpenses.child(id).setValue(expenses);
            Toast.makeText(this,"Expense Added",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"You Should enter the amount",Toast.LENGTH_LONG).show();
        }
    }

    //Cancellation button
    public void openExpenseView() {
        Intent intent = new Intent(this, ExpensesView.class);
        startActivity(intent);
    }
}