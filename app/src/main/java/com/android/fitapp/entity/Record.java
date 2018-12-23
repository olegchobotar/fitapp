package com.android.fitapp.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {
    Date date;
    int leftArm;
    int rightArm;
    int waist;
    int chest;
    int BMI;
    int height;
    int weight;
}
