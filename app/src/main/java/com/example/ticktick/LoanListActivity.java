package com.example.ticktick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ticktick.ViewHolders.LoanViewHolder;
import com.example.ticktick.model.LoanInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoanListActivity extends AppCompatActivity {
    private RecyclerView recylerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button addLoanRedirectBtn, addLendingRedirectBtn;
    private TextView txtTotalAmount;
    private int totalPrice = 0;
    public String key="", loanTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_list);


        loanTotal = getIntent().getStringExtra("loanTotal");
        recylerView = (RecyclerView) findViewById(R.id.loanList);
        recylerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recylerView.setLayoutManager(layoutManager);
        addLoanRedirectBtn = (Button) findViewById(R.id.addLoanRedirectBtn);
        addLendingRedirectBtn = (Button) findViewById(R.id.addLendingRedirectBtn);
        txtTotalAmount = (TextView) findViewById(R.id.loanTotalTxt);

        addLoanRedirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoanListActivity.this, AddLoanActivity.class);
                startActivity(intent);
            }
        });
        addLendingRedirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoanListActivity.this, AddLendingActivity.class);
                intent.putExtra("loanTotal", String.valueOf(totalPrice));
                startActivity(intent);
            }
        });


        final DatabaseReference loanListRef = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerOptions<LoanInfo> options =
                new FirebaseRecyclerOptions.Builder<LoanInfo>()
                        .setQuery(loanListRef
                                .child("Loans"),LoanInfo.class)
                        .build();

        FirebaseRecyclerAdapter<LoanInfo, LoanViewHolder> adapter
                = new FirebaseRecyclerAdapter<LoanInfo, LoanViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull LoanViewHolder holder, int position, @NonNull final LoanInfo model)
            {
                // Retrieving data from Firebase to the recyclerview
                holder.txtLoanDate.setText("Date: " + model.getDate());
                holder.txtLoanName.setText("Name: " + model.getName());
                holder.txtLoanAmount.setText("Amount: Rs. " + model.getAmount());

                // Calculating and Displaying the total amount
                int oneTypeProductTotalPrice = (((Integer.valueOf(model.getAmount()))));
                totalPrice = totalPrice + oneTypeProductTotalPrice;
                txtTotalAmount.setText("Total Amount : Rs. " + String.valueOf(totalPrice));

                // OnClick Options Function
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Edit"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoanListActivity.this);
                        builder.setTitle("Options: ");
                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(i==0)
                                {
                                    Intent intent = new Intent(LoanListActivity.this, UpdateLoanActivity.class);
                                    key = loanListRef.push().getKey();
                                    intent.putExtra("loanID", model.getLoanID());
                                    intent.putExtra("loanTotal", String.valueOf(totalPrice));
                                    startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loan_items_layout, parent, false);
                LoanViewHolder holder = new LoanViewHolder(view);
                return holder;
            }
        };
        recylerView.setAdapter(adapter);
        adapter.startListening();

    }

}