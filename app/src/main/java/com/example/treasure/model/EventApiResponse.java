package com.example.treasure.model;

import java.util.List;

public class EventApiResponse {
    private List<Event> events;

    public EventApiResponse(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}