package com.example.ticktick.ViewHolders;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticktick.Interface.ItemClickListener;
import com.example.ticktick.R;

public class LoanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtLoanDate, txtLoanName, txtLoanAmount;
    private ItemClickListener itemClickListner;


    public LoanViewHolder(@NonNull View itemView) {
        super(itemView);
        txtLoanDate = itemView.findViewById(R.id.updatedLoanDateTxt);
        txtLoanName = itemView.findViewById(R.id.updatedLoanNameTxt);
        txtLoanAmount = itemView.findViewById(R.id.updatedLoanAmountTxt);
    }

    @Override
    public void onClick(View view) {
        itemClickListner.onClick(view, getAdapterPosition(), false);
    }

    public void setItemClickListner(ItemClickListener itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}


// This ViewHolder is used for retrieving data to the recyclerview in the 'LoanListActivity' from the firebase database