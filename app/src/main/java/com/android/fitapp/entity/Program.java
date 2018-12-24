package com.android.fitapp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Program extends Article implements Serializable {
    private List<String> tags;
    private List<Exercise> exercises;

    public Program(String title, String text, String desc, List<String> tags, List<Exercise> exercises) {
        this.title = title;
        this.text = text;
        this.desc = desc;
        this.tags = tags;
        this.exercises = exercises;
    }

    public Program(){
        tags = new ArrayList();
        title = "Default title";
        desc = "Description of current program";
    }

    @Override
    public String toString(){
        return "\nTitle: " + title + "\nDescription: " + desc + "\nTags: " + tags;
    }

}
