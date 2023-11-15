package com.enfotrix.unibooking.Ui;

public class HallModel {
    String hallId;

    public String getHallId() {
        return hallId;
    }

    public void setHallId(String hallId) {
        this.hallId = hallId;
    }

    public HallModel(String hallId, String hallCapacity, String hallVenue, String hallType) {
        this.hallId = hallId;
        this.hallCapacity = hallCapacity;
        this.hallVenue = hallVenue;
        this.hallType = hallType;
    }

    public HallModel(String hallId) {
        this.hallId = hallId;
    }

    private String hallCapacity;
    private String hallVenue;
    private String hallType;
    private String hallName;

    public HallModel(String hallId, String hallCapacity, String hallVenue, String hallType, String hallName) {
        this.hallId = hallId;
        this.hallCapacity = hallCapacity;
        this.hallVenue = hallVenue;
        this.hallType = hallType;
        this.hallName = hallName;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    // Add constructors, getters, setters, if needed

    public HallModel() {
        // Default constructor required for Firestore
    }

    public String getHallCapacity() {
        return hallCapacity;
    }

    public void setHallCapacity(String hallCapacity) {
        this.hallCapacity = hallCapacity;
    }

    public String getHallVenue() {
        return hallVenue;
    }

    public void setHallVenue(String hallVenue) {
        this.hallVenue = hallVenue;
    }

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public HallModel(String hallCapacity, String hallVenue, String hallType) {
        this.hallCapacity = hallCapacity;
        this.hallVenue = hallVenue;
        this.hallType = hallType;
    }
}
