package com.android.fitapp.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Comment {
    private String comment;
    private String publisher;
    //private Date timestamp;

    public Comment(){
        this.comment = "Message...";
        this.publisher = "4oN5WpUgARX9rwPY5RFQAvo68b52";
    }
}

