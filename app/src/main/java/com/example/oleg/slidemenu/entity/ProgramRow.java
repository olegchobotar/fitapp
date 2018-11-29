package com.example.oleg.slidemenu.entity;

import java.util.ArrayList;
import java.util.List;

public class ProgramRow {
    private String title;
    private String desc;
    private List<String> tegs;

    public ProgramRow(){
        tegs = new ArrayList();
        title = "Default title";
        desc = "Description of current program";
    }

    public ProgramRow(String title, String desc, List tegs) {
        this.title = title;
        this.desc = desc;
        this.tegs = tegs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getTegs() {
        return tegs;
    }

    public void setTegs(ArrayList<String> tegs) {
        this.tegs = tegs;
    }
}
