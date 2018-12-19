package com.android.fitapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.android.fitapp.R;
import com.android.fitapp.entity.Exercise;

import java.util.List;

public class ExercisesAdpater extends ArrayAdapter<Exercise> {
    Context context;
    List<Exercise> exercises;

    public ExercisesAdpater(Context context, List<Exercise> exercises) {
        super(context, R.layout.program_exercise_layout, exercises);
        this.context = context;
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.program_exercise_layout, parent, false);

        if (position % 2 == 0) {
            view.setBackgroundResource(R.color.grayLight);
        } else {
            view.setBackgroundResource(R.color.colorWhite);
        }
        TextView name = view.findViewById(R.id.exercise_name),
                reps = view.findViewById(R.id.exercise_reps),
                desc = view.findViewById(R.id.exercise_desc);

        name.setText(exercises.get(position).getEx_name());
        reps.setText(exercises.get(position).getReps() + " reps " + exercises.get(position).getRest_time());
        desc.setText(exercises.get(position).getDescription());

        return view;
    }

}
