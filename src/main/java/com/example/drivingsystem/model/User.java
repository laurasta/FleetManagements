package com.example.drivingsystem.model;

import java.time.LocalDate;

public class User {
    private int user_id;
    private String name;
    private String surname;
    private LocalDate birth_date;
    private String mobile_number;
    private String work_number;
    private String username;
    private String password;
    private String type;

    public User(int user_id, String name, String surname, LocalDate birth_date, String mobile_number, String work_number, String username, String password, String type) {
        this.user_id = user_id;
        this.name = name;
        this.surname = surname;
        this.birth_date = birth_date;
        this.mobile_number = mobile_number;
        this.work_number = work_number;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getWork_number() {
        return work_number;
    }

    public void setWork_number(String work_number) {
        this.work_number = work_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}