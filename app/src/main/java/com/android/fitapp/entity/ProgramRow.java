package com.android.fitapp.entity;

import java.util.ArrayList;
import java.util.List;

public class ProgramRow {
    private String title;
    private String desc;
    private List<String> tags;

    public ProgramRow(){
        tags = new ArrayList();
        title = "Default title";
        desc = "Description of current program";
    }

    public ProgramRow(String title, String desc, List tegs) {
        this.title = title;
        this.desc = desc;
        this.tags = tegs;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tegs) {
        this.tags = tegs;
    }
}
