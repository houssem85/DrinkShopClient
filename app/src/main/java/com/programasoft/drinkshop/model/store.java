package com.programasoft.drinkshop.model;

public class store {
    private int id;
    private String name;
    private double latitude;
    private double  longitude;
    private double distance_in_km;

    public double getDistance_in_km() {
        return distance_in_km;
    }

    public void setDistance_in_km(double distance_in_km) {
        this.distance_in_km = distance_in_km;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public store() {
    }
}
