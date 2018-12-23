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
    private String photoUrl;
    private String rating;
    private String countOfPrograms;

    public User() {
        name = "Username";
        title = "There is a title";
        description = "Your description";
        email = "username@gmail.com";
        country = "Country";
        city = "City";
        phone = "ххх ххх хххх";
        photoUrl = "";
        rating = "5";
        countOfPrograms = "0";
    }

    public User(String name, String email, String country, String city, String phone) {
        this.name = name;
        this.title = "There is a title";
        this.description = "Your description";
        this.email = email;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.photoUrl = "";
        this.rating = "5";
        this.countOfPrograms = "0";
    }
}
