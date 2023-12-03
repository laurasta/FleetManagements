package com.example.drivingsystem.model;

public class Destination {
    int id;
    String from;
    String to;
    String stop;
    int distance;
    String status;

    public Destination(int id, String from, String to, String stop, int distance, String status) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.stop = stop;
        this.distance = distance;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getStop() {
        return stop;
    }

    public void setStop(String stop) {
        this.stop = stop;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
