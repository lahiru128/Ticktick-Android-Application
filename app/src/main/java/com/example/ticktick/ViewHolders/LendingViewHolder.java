package com.example.ticktick.ViewHolders;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticktick.Interface.ItemClickListener;
import com.example.ticktick.R;

public class LendingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txtLendingDate, txtLendingName, txtLendingAmount;
    private ItemClickListener itemClickListner;

    public LendingViewHolder(@NonNull View itemView) {
        super(itemView);
        txtLendingDate = itemView.findViewById(R.id.updatedLendingDateTxt);
        txtLendingName = itemView.findViewById(R.id.updatedLendingNameTxt);
        txtLendingAmount = itemView.findViewById(R.id.updatedLendingAmountTxt);
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


// This ViewHolder is used for retrieving data to the recyclerview in the 'LendingListActivity' from the firebase database