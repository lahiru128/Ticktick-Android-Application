package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FinalActivity extends AppCompatActivity {
    private TextView totalLoansTxt, totalLendingsTxt, balanceTxt;
    private Button finalLoansBtn, finalLendingsBtn;
    String loanTotal="";
    String lendingTotal;
    String newBalance;
    int balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        loanTotal = getIntent().getStringExtra("loanTotal");
        lendingTotal = getIntent().getStringExtra("lendingTotal");
        balance = (Integer.parseInt(loanTotal.toString()) - Integer.parseInt(lendingTotal.toString()));

        totalLoansTxt = (TextView) findViewById(R.id.totalLoansTxt);
        totalLoansTxt = (TextView) findViewById(R.id.totalLoansTxt);
        totalLendingsTxt = (TextView) findViewById(R.id.totalLendingsTxt);
        balanceTxt = (TextView) findViewById(R.id.balanceTxt);
        finalLoansBtn = (Button) findViewById(R.id.finalLoansBtn);
        finalLendingsBtn = (Button) findViewById(R.id.finalLendingsBtn);

        totalLoansTxt.setText("Total of Loans : Rs. " + loanTotal);
        totalLendingsTxt.setText("Total of Lendings : Rs. " + lendingTotal);
        balanceTxt.setText("Balance : Rs. " + Integer.toString(balance));

        finalLoansBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalActivity.this, LoanListActivity.class);
                startActivity(intent);
            }
        });
        finalLendingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FinalActivity.this, LendingListActivity.class);
                startActivity(intent);
            }
        });
    }
}