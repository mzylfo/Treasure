package com.example.treasure.source.event;

import com.example.treasure.model.Event;
import com.example.treasure.repository.event.EventResponseCallback;

import java.util.List;

public abstract class BaseEventLocalDataSource {
    protected EventResponseCallback eventCallback;

    public void setEventCallback(EventResponseCallback eventCallback) {
        this.eventCallback = eventCallback;
    }

    public abstract void getEvents();

    public abstract void deleteEvent(Event event);

    public abstract void insertEvent(String name, String date, String time);

    public abstract void insertEvents(List<Event> eList);
}