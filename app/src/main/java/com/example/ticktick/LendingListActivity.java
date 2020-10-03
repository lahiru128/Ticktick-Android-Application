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

import com.example.ticktick.ViewHolders.LendingViewHolder;
import com.example.ticktick.model.LendingInfo;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LendingListActivity extends AppCompatActivity {
    private RecyclerView recylerView;
    private RecyclerView.LayoutManager layoutManager;
    private Button addLendingRedirectBtn, checkBalanceBtn;
    private TextView txtTotalAmount;
    private int totalPrice = 0;
    public String key="", loanTotal = "", lendingTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending_list);

        lendingTotal = getIntent().getStringExtra("lendingTotal");
        loanTotal = getIntent().getStringExtra("loanTotal");
        recylerView = (RecyclerView) findViewById(R.id.lendingList);
        recylerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recylerView.setLayoutManager(layoutManager);
        addLendingRedirectBtn = (Button) findViewById(R.id.addLendingRedirectBtn);
        checkBalanceBtn = (Button) findViewById(R.id.checkBalanceBtn);
        txtTotalAmount = (TextView) findViewById(R.id.lendingTotalTxt);

        addLendingRedirectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendingListActivity.this, AddLendingActivity.class);
                startActivity(intent);
            }
        });


        final DatabaseReference lendingListRef = FirebaseDatabase.getInstance().getReference();
        FirebaseRecyclerOptions<LendingInfo> options =
                new FirebaseRecyclerOptions.Builder<LendingInfo>()
                        .setQuery(lendingListRef
                                .child("Lendings"),LendingInfo.class)
                        .build();

        FirebaseRecyclerAdapter<LendingInfo, LendingViewHolder> adapter
                = new FirebaseRecyclerAdapter<LendingInfo, LendingViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull LendingViewHolder holder, int position, @NonNull final LendingInfo model)
            {
                // Retrieving data from Firebase to the recyclerview
                holder.txtLendingDate.setText("Date: " + model.getDate());
                holder.txtLendingName.setText("Name: " + model.getName());
                holder.txtLendingAmount.setText("Amount: Rs. " + model.getAmount());

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
                        AlertDialog.Builder builder = new AlertDialog.Builder(LendingListActivity.this);
                        builder.setTitle("Options: ");
                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                if(i==0)
                                {
                                    Intent intent = new Intent(LendingListActivity.this, UpdateLendingActivity.class);
                                    key = lendingListRef.push().getKey();
                                    intent.putExtra("lendingID", model.getLendingID());
                                    intent.putExtra("loanTotal", loanTotal);
                                    intent.putExtra("lendingTotal", String.valueOf(totalPrice));
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
            public LendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lending_items_layout, parent, false);
                LendingViewHolder holder = new LendingViewHolder(view);
                return holder;
            }


        };

        checkBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LendingListActivity.this, FinalActivity.class);
                intent.putExtra("lendingTotal", String.valueOf(totalPrice));
                intent.putExtra("loanTotal", loanTotal);
                startActivity(intent);
            }
        });

        recylerView.setAdapter(adapter);
        adapter.startListening();
    }

}