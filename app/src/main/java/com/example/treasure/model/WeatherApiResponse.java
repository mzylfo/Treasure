package com.example.treasure.model;

public class WeatherApiResponse {
    private Location location;
    private Current current;

    // Getters e Setters
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }
}
