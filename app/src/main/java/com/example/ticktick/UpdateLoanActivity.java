package com.example.ticktick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;

public class UpdateLoanActivity extends AppCompatActivity {
    private EditText UpdateLoanName, UpdateLoanDate, UpdateLoanAmount, UpdateLoanDescription;
    private Button LoanUpdateBtn, LoanDeleteBtn;
    public String loanID="", loanTotal="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_loan);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Loans");
        loanID = getIntent().getStringExtra("loanID");
        loanTotal = getIntent().getStringExtra("loanTotal");
        UpdateLoanName = (EditText) findViewById(R.id.loanNameUpdateTxt);
        UpdateLoanDate = (EditText) findViewById(R.id.loanDateUpdateTxt);
        UpdateLoanAmount = (EditText) findViewById(R.id.loanAmountUpdateTxt);
        UpdateLoanDescription = (TextInputEditText) findViewById(R.id.loanDescriptionUpdateTxt);
        LoanUpdateBtn = (Button) findViewById(R.id.loanUpdateBtn);
        LoanDeleteBtn = (Button) findViewById(R.id.loanDeleteBtn);

        updateLoanInfo(UpdateLoanName, UpdateLoanDate, UpdateLoanAmount, UpdateLoanDescription);

        LoanUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLoanInfoOnClick();
            }
        });

        // Deleting the entry on Button Click
        LoanDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child(loanID)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(UpdateLoanActivity.this, "Loan Info Removed Successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                    overridePendingTransition(0, 0);
                                    overridePendingTransition(0, 0);
                                    Intent intent = new Intent(UpdateLoanActivity.this, LoanListActivity.class);
                                    intent.putExtra("loanTotal", loanTotal);
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }

    // Updating the database with new data inserted, when the user clicks on the 'Update' Button.
    private void updateLoanInfoOnClick(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Loans");

        // Updating the fields with the new data inserted
        HashMap<String, Object> loanMap = new HashMap<>();
        loanMap.put("name", UpdateLoanName.getText().toString());
        loanMap.put("date", UpdateLoanDate.getText().toString());
        loanMap.put("amount", UpdateLoanAmount.getText().toString());
        loanMap.put("description", UpdateLoanDescription.getText().toString());
        ref.child(loanID).updateChildren(loanMap);

        Toast.makeText(UpdateLoanActivity.this, "Loan Info updated successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateLoanActivity.this, LoanListActivity.class);
        intent.putExtra("loanTotal", loanTotal);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        overridePendingTransition(0, 0);
    }


    // Retrieving data from the Firebase database and displaying them realtime
    private void updateLoanInfo(final EditText UpdateLoanName, final EditText UpdateLoanDate, final EditText UpdateLoanAmount, final EditText UpdateLoanDescription)
    {
        DatabaseReference LoanRef = FirebaseDatabase.getInstance().getReference().child("Loans").child(loanID);
        LoanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if(dataSnapshot.exists())
                {
                    if(dataSnapshot.child("name").exists()) {

                        String name = dataSnapshot.child("name").getValue().toString();
                        String date = dataSnapshot.child("date").getValue().toString();
                        String amount = dataSnapshot.child("amount").getValue().toString();
                        String description = dataSnapshot.child("description").getValue().toString();

                        UpdateLoanName.setText(name);
                        UpdateLoanDate.setText(date);
                        UpdateLoanAmount.setText(amount);
                        UpdateLoanDescription.setText(description);

                    }

                }else{
                    Log.d("Message", "Error Occurred!");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}