package com.example.oleg.slidemenu.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProgramRow {
    private Long id;
    private String title;
    private String desc;
    private List<String> tags;

    public ProgramRow(){
        tags = new ArrayList();
        title = "Default title";
        desc = "Description of current program";
    }
}
