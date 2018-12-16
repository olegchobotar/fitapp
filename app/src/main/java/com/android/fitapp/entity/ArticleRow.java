package com.android.fitapp.entity;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleRow implements Serializable {
    private Long id;
    private String title;
    private String desc;
    private List<String> tags;
}
