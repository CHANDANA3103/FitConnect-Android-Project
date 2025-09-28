package com.example.fitconnect;

import android.location.Location;

// This is a simple data class (a "POJO") to hold information about a single gym.
public class Gym {
    private String name;
    private double latitude;
    private double longitude;
    private float distance = 0; // The calculated distance from the user.

    // Constructor to create a new Gym object.
    public Gym(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // "Getter" methods to access the private variables.
    public String getName() {
        return name;
    }

    public float getDistance() {
        return distance;
    }

    public Location getLocation() {
        Location location = new Location("");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return location;
    }

    // "Setter" method to update the distance after we calculate it.
    public void setDistance(float distance) {
        this.distance = distance;
    }
}