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

import java.util.HashMap;

public class UpdateLendingActivity extends AppCompatActivity {
    private EditText UpdateLendingName, UpdateLendingDate, UpdateLendingAmount, UpdateLendingDescription;
    private Button LendingUpdateBtn, LendingDeleteBtn;
    public String lendingID="",loanTotal, lendingTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_lending);

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Lendings");
        lendingID = getIntent().getStringExtra("lendingID");
        loanTotal = getIntent().getStringExtra("loanTotal");
        lendingTotal = getIntent().getStringExtra("lendingTotal");
        UpdateLendingName = (EditText) findViewById(R.id.lendingNameUpdateTxt);
        UpdateLendingDate = (EditText) findViewById(R.id.lendingDateUpdateTxt);
        UpdateLendingAmount = (EditText) findViewById(R.id.lendingAmountUpdateTxt);
        UpdateLendingDescription = (EditText) findViewById(R.id.lendingDescriptionUpdateTxt);
        LendingUpdateBtn = (Button) findViewById(R.id.lendingUpdateBtn);
        LendingDeleteBtn = (Button) findViewById(R.id.lendingDeleteBtn);

        updateLendingInfo(UpdateLendingName, UpdateLendingDate, UpdateLendingAmount, UpdateLendingDescription);

        LendingUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLendingInfoOnClick();
            }
        });


        // Deleting the entry on Button Click
        LendingDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ref.child(lendingID)
                        .removeValue()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(UpdateLendingActivity.this, "Lending Info Removed Successfully!", Toast.LENGTH_SHORT).show();
                                    overridePendingTransition(0, 0);
                                    overridePendingTransition(0, 0);
                                    Intent intent = new Intent(UpdateLendingActivity.this, LendingListActivity.class);
                                    intent.putExtra("loanTotal", loanTotal);
                                    intent.putExtra("lendingTotal", lendingTotal);
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        });
            }
        });
    }


    // Retrieving data from the Firebase database and displaying them realtime
    private void updateLendingInfo(final EditText UpdateLendingName, final EditText UpdateLendingDate, final EditText UpdateLendingAmount, final EditText UpdateLendingDescription) {

        DatabaseReference LendingRef = FirebaseDatabase.getInstance().getReference().child("Lendings").child(lendingID);
        LendingRef.addValueEventListener(new ValueEventListener() {
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

                        UpdateLendingName.setText(name);
                        UpdateLendingDate.setText(date);
                        UpdateLendingAmount.setText(amount);
                        UpdateLendingDescription.setText(description);

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


    // Updating the database with new data inserted, when the user clicks on the 'Update' Button.
    private void updateLendingInfoOnClick(){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Lendings");

        HashMap<String, Object> lendingMap = new HashMap<>();
        lendingMap.put("name", UpdateLendingName.getText().toString());
        lendingMap.put("date", UpdateLendingDate.getText().toString());
        lendingMap.put("amount", UpdateLendingAmount.getText().toString());
        lendingMap.put("description", UpdateLendingDescription.getText().toString());
        ref.child(lendingID).updateChildren(lendingMap);

        Toast.makeText(UpdateLendingActivity.this, "Lending Info updated successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateLendingActivity.this, LendingListActivity.class);
        intent.putExtra("loanTotal", loanTotal);
        intent.putExtra("lendingTotal", lendingTotal);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        overridePendingTransition(0, 0);
    }
}