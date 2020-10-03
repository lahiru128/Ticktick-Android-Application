package com.example.ticktick.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.Model.Incomes;
import com.example.ticktick.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DashboardIncomeAdapter extends FirebaseRecyclerAdapter<Incomes, DashboardIncomeAdapter.incomeviewholder> {

    public DashboardIncomeAdapter(@NonNull FirebaseRecyclerOptions<Incomes> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull final DashboardIncomeAdapter.incomeviewholder holder, final int position, @NonNull final Incomes incomes) {
        //Showing the income data in the income view page
        holder.incomeName.setText(incomes.getIncomeName());
        holder.incomeAmount.setText(incomes.getIncomeAmount());
    }

    @NonNull
    @Override
    public DashboardIncomeAdapter.incomeviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_cardview, parent, false);
        return new DashboardIncomeAdapter.incomeviewholder(view);
    }

    class incomeviewholder extends RecyclerView.ViewHolder {

        TextView incomeName, incomeAmount;

        public incomeviewholder(@NonNull View itemView) {
            super(itemView);
            incomeName = (TextView) itemView.findViewById(R.id.name);
            incomeAmount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
