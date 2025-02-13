package com.example.treasure.repository.event;

import com.example.treasure.model.Event;

import java.util.List;

public interface EventResponseCallback {
    void onFailureFromRemote(Exception exception);
    void onSuccessEventFromLocal(List<Event> eventList);
    void onFailureEventFromLocal(Exception exception);
    void onDeleteEvent(Event e);
    void onInsertEvent(Event e);
}
