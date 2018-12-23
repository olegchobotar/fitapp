package com.android.fitapp.entity;

import lombok.Data;

@Data
public class Exercise {
    Long exercise_id;

    String ex_name;

    String reps;

    String rest_time;

    String description;
}
