package com.example.ticktick.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.Model.Expenses;
import com.example.ticktick.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;


public class ExpenseAdapter extends FirebaseRecyclerAdapter<Expenses, ExpenseAdapter.viewholder> {

    public ExpenseAdapter(@NonNull FirebaseRecyclerOptions<Expenses> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final viewholder holder, final int position, @NonNull final Expenses expenses) {
        //Showing the expense data in the expense view page
        holder.expenseName.setText(expenses.getExpenseName());
        holder.expenseAmount.setText(expenses.getExpenseAmount());
        holder.expenseNote.setText(expenses.getExpenseNote());


        //Expense update function
        holder.expenseEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.expenseName.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_modal_expense))
                        .setGravity(Gravity.CENTER)
                        .setExpanded(true, 1100)
                        .create();

                View updateView = dialogPlus.getHolderView();
                final EditText name = updateView.findViewById(R.id.expenseUpName);
                final EditText amount = updateView.findViewById(R.id.expenseUpAmount);
                final EditText note = updateView.findViewById(R.id.expenseUpNote);

                Button update = updateView.findViewById(R.id.expenseUpSubmit);

                name.setText(expenses.getExpenseName());
                amount.setText(expenses.getExpenseAmount());
                note.setText(expenses.getExpenseNote());

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("expenseName", name.getText().toString());
                        map.put("expenseAmount", amount.getText().toString());
                        map.put("expenseNote", note.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Expenses")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });


        //Expense delete function
        holder.expenseDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.expenseName.getContext());
                builder.setTitle("Delete Expense");
                builder.setMessage("Delete This Expense Record?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Expenses").child(getRef(position).getKey()).removeValue();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public ExpenseAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_expense, parent, false);
        return new ExpenseAdapter.viewholder(view);
    }


    class viewholder extends RecyclerView.ViewHolder {
        TextView expenseName, expenseAmount, expenseNote;
        ImageView expenseEditBtn, expenseDeleteBtn;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            expenseName = (TextView) itemView.findViewById(R.id.eName);
            expenseAmount = (TextView) itemView.findViewById(R.id.eAmount);
            expenseNote = (TextView) itemView.findViewById(R.id.eNote);
            expenseEditBtn = (ImageView) itemView.findViewById(R.id.editIconEx);
            expenseDeleteBtn = (ImageView) itemView.findViewById(R.id.deleteIconEx);
        }
    }
}
