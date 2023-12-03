package com.example.drivingsystem.model;

public class Cargo {
    int id;
    String description;
    int weight;
    String status;

    public Cargo(int id, String description, int weight, String status) {
        this.id = id;
        this.description = description;
        this.weight = weight;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
