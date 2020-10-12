package com.example.ticktick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class AddLoanActivity extends AppCompatActivity {

    private TextInputEditText AddLoanName, AddLoanDate, AddLoanAmount, AddLoanDescription;
    private Button LoanSaveBtn, LoanCancelBtn, loanListRedirectBtn;
    String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loan);

        AddLoanName = (TextInputEditText) findViewById(R.id.addLoanNameTxt);
        AddLoanDate = (TextInputEditText) findViewById(R.id.addLoanDateTxt);
        AddLoanAmount = (TextInputEditText) findViewById(R.id.addLoanAmountTxt);
        AddLoanDescription = (TextInputEditText) findViewById(R.id.addLoanDescriptionTxt);
        LoanSaveBtn = (Button) findViewById(R.id.loanSaveBtn);
        LoanCancelBtn = (Button) findViewById(R.id.loanCancelBtn);
        loanListRedirectBtn = (Button) findViewById(R.id.loanListRedirectBtn);
        LoanSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLoanInfo();
            }
        });
        loanListRedirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLoanActivity.this, LoanListActivity.class);
                startActivity(intent);
            }
        });

        // Clearing Fields on Click
        LoanCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLoanName.setText("");
                AddLoanDate.setText("");
                AddLoanAmount.setText("");
                AddLoanDescription.setText("");
            }
        });
    }

    // Checking if the fields are empty
    private void AddLoanInfo() {
        String loanName = AddLoanName.getText().toString();
        String loanDate = AddLoanDate.getText().toString();
        String loanAmount = AddLoanAmount.getText().toString();
        String loanDescription = AddLoanDescription.getText().toString();
        if (TextUtils.isEmpty(loanName)) {
            Toast.makeText(this, "Please enter your Name", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(loanDate)) {
            Toast.makeText(this, "Please enter the Date", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(loanAmount)) {
            Toast.makeText(this, "Please enter the Loan Amount", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(loanDescription)) {
            Toast.makeText(this, "Please enter the Description", Toast.LENGTH_SHORT).show();
        }else
        {
            // If all the fields are filled, execute the Validate() method
            Validate(loanName, loanDate, loanAmount, loanDescription);
        }

}

    private void Validate(final String loanName, final String loanDate, final String loanAmount, final String loanDescription) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // key - A Random Push Key Firebase randomly generates
                key = RootRef.child("Loans").push().getKey();

                // Uploading data to Firebase database
                HashMap<String, Object> loandataMap = new HashMap<>();
                loandataMap.put("loanID", key);
                loandataMap.put("name", loanName);
                loandataMap.put("date", loanDate);
                loandataMap.put("amount", loanAmount);
                loandataMap.put("description", loanDescription);

                RootRef.child("Loans").child(key).updateChildren(loandataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(AddLoanActivity.this, "Loan details added successfully!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(AddLoanActivity.this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}