package com.android.fitapp.entity;

public class User {
    private String name;
    private String email;
    private String country;
    private String city;
    private String phone;

    public User(){
        name = "Username";
        email = "username@gmail.com";
        country = "Ukraine";
        city = "Lviv";
        phone = "0997764357";
    }

    public User(String name, String email, String country, String city, String phone){
        this.name = name;
        this.email = email;
        this.country = country;
        this.city = city;
        this.phone = phone;
    }
}
