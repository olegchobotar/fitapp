package com.android.fitapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String title;
    private String description;
    private String email;
    private String country;
    private String city;
    private String phone;
    private float rating;
    private int countOfPrograms;

    public User() {
        name = "Username";
        title = "Strong man";
        description = "Your description";
        email = "username@gmail.com";
        country = "Country";
        city = "City";
        phone = "ххх ххх хххх";
        rating = 5f;
        countOfPrograms = 0;
    }

    public User(String name, String email, String country, String city, String phone) {
        this.name = name;
        this.email = email;
        this.country = country;
        this.city = city;
        this.phone = phone;
    }
}
