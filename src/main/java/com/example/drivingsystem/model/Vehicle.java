package com.example.drivingsystem.model;

public class Vehicle {
    int id;
    String make;
    String model;
    String plate;
    String state;

    public Vehicle(int id, String make, String model, String plate, String state) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.plate = plate;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
