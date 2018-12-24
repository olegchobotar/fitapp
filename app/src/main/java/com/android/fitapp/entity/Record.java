package com.android.fitapp.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    Long id;
    String date;
    double leftArm;
    double rightArm;
    double waist;
    double chest;
    double bmi;
    double height;
    double weight;
    String uid;
}
