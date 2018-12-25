package com.android.fitapp.entity;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record implements Comparable{
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


    @Override
    public int compareTo(Object o) {
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");//or your pattern
        try {
            return f.parse(this.getDate()).compareTo(f.parse(((Record)o).getDate()));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
