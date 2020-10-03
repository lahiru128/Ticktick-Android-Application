package com.example.ticktick.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.Model.Expenses;
import com.example.ticktick.Model.Incomes;
import com.example.ticktick.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DashboardExpenseAdapter extends FirebaseRecyclerAdapter<Expenses, DashboardExpenseAdapter.expenseviewholder> {


    public DashboardExpenseAdapter(@NonNull FirebaseRecyclerOptions<Expenses> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull DashboardExpenseAdapter.expenseviewholder holder, int position, @NonNull Expenses expenses) {
                //Showing the income data in the income view page
                     holder.expenseName.setText(expenses.getExpenseName());
                     holder.expenseAmount.setText(expenses.getExpenseAmount());
    }

    @NonNull
    @Override
    public DashboardExpenseAdapter.expenseviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_horizontal_cardview, parent, false);
        return new DashboardExpenseAdapter.expenseviewholder(view);
    }


    class expenseviewholder extends RecyclerView.ViewHolder {

        TextView expenseName, expenseAmount;

        public expenseviewholder(@NonNull View itemView) {
            super(itemView);
            expenseName = (TextView) itemView.findViewById(R.id.eName);
            expenseAmount = (TextView) itemView.findViewById(R.id.eAmount);
        }
    }


}
