package com.example.ticktick.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ticktick.Model.Todo;
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

public class TodoAdapter extends FirebaseRecyclerAdapter<Todo,TodoAdapter.myviewholder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TodoAdapter(@NonNull FirebaseRecyclerOptions<Todo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, final int position, @NonNull final Todo todo) {
        holder.todoTitle.setText(todo.getTodotitle());
        holder.todoDate.setText(todo.getTododate());
        holder.todoNote.setText(todo.getTodonote());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.todoTitle.getContext())
                        .setContentHolder(new ViewHolder(R.layout.todo_update_modal))
                        .setExpanded(true,1100)
                        .create();


                View updateView =dialogPlus.getHolderView();
                final EditText title = updateView.findViewById(R.id.upTitle);
                final EditText date = updateView.findViewById(R.id.upDate);
                final EditText note = updateView.findViewById(R.id.upNote);

                Button update= updateView.findViewById(R.id.uSubmit);

                title.setText(todo.getTodotitle());
                date.setText(todo.getTododate());
                note.setText(todo.getTodonote());

                dialogPlus.show();

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("todotitle",title.getText().toString());
                        map.put("tododate",date.getText().toString());
                        map.put("todonote",note.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Todo")
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


        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.todoTitle.getContext());
                builder.setTitle("Delete Todo");
                builder.setMessage("Delete This Todo Record?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Todo").child(getRef(position).getKey()).removeValue();

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_todo_cardview,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        TextView todoTitle, todoDate, todoNote;
        ImageView editBtn,deleteBtn;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            todoTitle =(TextView)itemView.findViewById(R.id.iTitle);
            todoDate =(TextView)itemView.findViewById(R.id.iDate);
            todoNote =(TextView)itemView.findViewById(R.id.iNote);
            editBtn=(ImageView)itemView.findViewById(R.id.editIcon);
            deleteBtn=(ImageView)itemView.findViewById(R.id.deleteIcon);
        }
    }
}
