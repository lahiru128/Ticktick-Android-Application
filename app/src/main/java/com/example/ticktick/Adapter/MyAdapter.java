package com.example.ticktick.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticktick.Model.Incomes;
import com.example.ticktick.R;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class MyAdapter extends FirebaseRecyclerAdapter<Incomes,MyAdapter.myviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<Incomes> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull Incomes incomes) {
        holder.iName.setText(incomes.getIncomeName());
        holder.iAmount.setText(incomes.getIncomeAmount());
        holder.iNote.setText(incomes.getIncomeNote());

                holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(holder.iName.getContext());
                        builder.setTitle("Delete Income");
                        builder.setMessage("Delete This Income Record?");

                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase.getInstance().getReference().child("Incomes").child(getRef(position).getKey()).removeValue();

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
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView iName,iAmount,iNote;
        ImageView editBtn,deleteBtn;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            iName=(TextView)itemView.findViewById(R.id.iName);
            iAmount=(TextView)itemView.findViewById(R.id.iAmount);
            iNote=(TextView)itemView.findViewById(R.id.iNote);
            editBtn=(ImageView)itemView.findViewById(R.id.editIcon);
            deleteBtn=(ImageView)itemView.findViewById(R.id.deleteIcon);

        }
    }
}
