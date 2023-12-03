package com.example.drivingsystem.model;

public class Order {
    int id;
    int destination_id;
    int cargo_id;
    int assignee_id;
    int vehicle_id;
    String status;

    public Order(int id, int destination_id, int cargo_id, int assignee_id, int vehicle_id, String status) {
        this.id = id;
        this.destination_id = destination_id;
        this.cargo_id = cargo_id;
        this.assignee_id = assignee_id;
        this.vehicle_id = vehicle_id;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(int destination_id) {
        this.destination_id = destination_id;
    }

    public int getCargo_id() {
        return cargo_id;
    }

    public void setCargo_id(int cargo_id) {
        this.cargo_id = cargo_id;
    }

    public int getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(int assignee_id) {
        this.assignee_id = assignee_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
