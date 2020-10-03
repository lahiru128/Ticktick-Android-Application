package com.example.ticktick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
//https://github.com/lahiru128/Ticktick-Android-Application.github
public class AddLendingActivity extends AppCompatActivity {
    private TextInputEditText AddLendingName, AddLendingDate, AddLendingAmount, AddLendingDescription;
    private Button LendingSaveBtn, LendingCancelBtn, LendingListRedirectBtn;
    String key="";
    String loanTotal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lending);

        loanTotal = getIntent().getStringExtra("loanTotal");
        AddLendingName = (TextInputEditText) findViewById(R.id.lendingNameTxt);
        AddLendingDate = (TextInputEditText) findViewById(R.id.lendingDateTxt);
        AddLendingAmount = (TextInputEditText) findViewById(R.id.lendingAmountTxt);
        AddLendingDescription = (TextInputEditText) findViewById(R.id.lendingDescriptionTxt);
        LendingSaveBtn = (Button) findViewById(R.id.lendingSaveBtn);
        LendingCancelBtn = (Button) findViewById(R.id.lendingCancelBtn);
        LendingListRedirectBtn = (Button) findViewById(R.id.lendingListRedirectBtn);
        LendingSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLendingInfo();
            }
        });
        LendingListRedirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddLendingActivity.this, LendingListActivity.class);
                intent.putExtra("loanTotal", loanTotal);
                startActivity(intent);
            }
        });
        LendingCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddLendingName.setText("");
                AddLendingDate.setText("");
                AddLendingAmount.setText("");
                AddLendingDescription.setText("");
            }
        });
    }

    // Checking if the fields are empty
    private void AddLendingInfo() {
        String lendingName = AddLendingName.getText().toString();
        String lendingDate = AddLendingDate.getText().toString();
        String lendingAmount = AddLendingAmount.getText().toString();
        String lendingDescription = AddLendingDescription.getText().toString();
        if (TextUtils.isEmpty(lendingName)) {
            Toast.makeText(this, "Please enter your Name", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(lendingDate)) {
            Toast.makeText(this, "Please enter the Date", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(lendingAmount)) {
            Toast.makeText(this, "Please enter the Lending Amount", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(lendingDescription)) {
            Toast.makeText(this, "Please enter the Description", Toast.LENGTH_SHORT).show();
        }else
        {
            // If all the fields are filled, execute the Validate() method
            Validate(lendingName, lendingDate, lendingAmount, lendingDescription);
        }

    }

    private void Validate(final String lendingName, final String lendingDate, final String lendingAmount, final String lendingDescription) {

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                // key - A Random Push Key Firebase randomly generates
                key = RootRef.child("Lendings").push().getKey();

                // Uploading data to Firebase database
                HashMap<String, Object> lendingdataMap = new HashMap<>();
                lendingdataMap.put("lendingID", key);
                lendingdataMap.put("name", lendingName);
                lendingdataMap.put("date", lendingDate);
                lendingdataMap.put("amount", lendingAmount);
                lendingdataMap.put("description", lendingDescription);

                RootRef.child("Lendings").child(key).updateChildren(lendingdataMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(AddLendingActivity.this, "Lending details added successfully!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(AddLendingActivity.this, "Connection Error. Please try again.", Toast.LENGTH_SHORT).show();
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