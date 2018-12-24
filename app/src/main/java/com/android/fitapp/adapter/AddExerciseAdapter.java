package com.android.fitapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.android.fitapp.R;
import com.android.fitapp.entity.Exercise;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class AddExerciseAdapter extends RecyclerView.Adapter<AddExerciseAdapter.ViewHolder> {
    Context context;
    public static ArrayList<Exercise> edits;

    public AddExerciseAdapter(Context context, ArrayList<Exercise> edits) {
        this.context = context;
        this.edits = edits;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_exercise_input, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        Exercise exercise = edits.get(i);

        if(exercise != null) {
            holder.name.setText(exercise.getEx_name());
            holder.name.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edits.get(i).setEx_name(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.reps.setText(exercise.getReps());
            holder.reps.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edits.get(i).setReps(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.rest.setText(exercise.getRest_time());
            holder.rest.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edits.get(i).setRest_time(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.desc.setText(exercise.getDescription());
            holder.desc.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    edits.get(i).setDescription(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return edits.size();
    }


    @Getter
    @Setter
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView reps;
        TextView rest;
        TextView desc;
        ImageButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            reps = itemView.findViewById(R.id.reps);
            rest = itemView.findViewById(R.id.rest);
            desc = itemView.findViewById(R.id.description);
            deleteBtn = itemView.findViewById(R.id.delete_btn);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    edits.remove(pos);
                    notifyItemRemoved(pos);
                }
            });
        }

    }

    public void addNewRow() {
        edits.add(new Exercise());
        notifyItemInserted(edits.size()-1);
    }
}
