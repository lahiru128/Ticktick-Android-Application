package com.example.ticktick;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.ticktick.Model.Incomes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

public class AddIncome extends AppCompatActivity {

    EditText amount, name, note;
    ImageButton btnIncomeSave, btnIncomeCancel;
    DatabaseReference databaseIncomes;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        //Get instances from the database
        databaseIncomes = FirebaseDatabase.getInstance().getReference("Incomes");

        //Assign Variables
        name = (EditText) findViewById(R.id.incomeNameAdd);
        amount = (EditText) findViewById(R.id.incomeAmountAdd);
        note = (EditText) findViewById(R.id.incomeNoteAdd);
        btnIncomeSave = (ImageButton) findViewById(R.id.incomeSaveBtn);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(BASIC);

        //Add Validations For Amount
        awesomeValidation.addValidation(AddIncome.this, R.id.incomeAmountAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_amount);
        awesomeValidation.addValidation(AddIncome.this, R.id.incomeNameAdd, RegexTemplate.NOT_EMPTY, R.string.invalid_name);

        //onclick listener for save button and validations
        btnIncomeSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check validation
                if (awesomeValidation.validate()) {
                    //On Success
                    addIncome();
                    openIncomeView();
                } else {
                    Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Onclick listener for cancellation button
        btnIncomeCancel = (ImageButton) findViewById(R.id.incomeCancelBtn);
        btnIncomeCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIncomeView();
            }
        });
    }

    //Add income method
    private void addIncome() {
        String incomeName = name.getText().toString().trim();
        String incomeAmount = amount.getText().toString().trim();
        String incomeNote = note.getText().toString().trim();

        //Check whether the amount is entered
        if (!TextUtils.isEmpty(incomeAmount)) {
            String id = databaseIncomes.push().getKey();
            Incomes incomes = new Incomes(id,incomeName, incomeAmount, incomeNote);
            databaseIncomes.child(id).setValue(incomes);
            Toast.makeText(this, "Income added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "You Should enter a Amount", Toast.LENGTH_LONG).show();
        }
    }

    //Cancellation button
    public void openIncomeView() {
        Intent intent = new Intent(this, IncomeView.class);
        startActivity(intent);
    }
}