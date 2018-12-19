package com.android.fitapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Exercise {
    Long exercise_id;

    String ex_name;

    String reps;

    String rest_time;

    String description;
}
