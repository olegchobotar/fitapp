package com.android.fitapp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Article {
    Long id = 0L;
    String title;
    String desc;
    String text;
}
