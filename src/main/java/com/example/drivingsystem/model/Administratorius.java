package com.example.drivingsystem.model;

import java.time.LocalDate;

public class Administratorius extends User{
    public Administratorius(int user_id, String name, String surname, LocalDate birth_date, String mobile_number, String work_number, String username, String password, String type) {
        super( user_id, name, surname, birth_date, mobile_number, work_number, username, password, type);
    }
}
