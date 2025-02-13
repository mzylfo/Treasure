package com.example.treasure.repository.event;

import androidx.lifecycle.MutableLiveData;

import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.example.treasure.model.Result;
import com.example.treasure.model.User;
import com.example.treasure.repository.user.UserResponseCallback;
import com.example.treasure.source.event.BaseEventLocalDataSource;
import com.example.treasure.source.user.BaseUserRemoteDataSource;

import java.util.List;

public class EventRepository implements UserResponseCallback, EventResponseCallback {

    private static final long FRESH_TIMEOUT = 60000; // 1 minuto per esempio

    private final BaseEventLocalDataSource eventLocalDataSource;
    private final BaseUserRemoteDataSource eventRemoteDataSource;
    private final MutableLiveData<Result> eventMutableLiveData;

    public EventRepository(BaseUserRemoteDataSource eventRemoteDataSource,
                           BaseEventLocalDataSource eventLocalDataSource) {
        this.eventRemoteDataSource = eventRemoteDataSource;
        this.eventLocalDataSource = eventLocalDataSource;
        this.eventMutableLiveData = new MutableLiveData<>();
        this.eventRemoteDataSource.setUserResponseCallback((UserResponseCallback) this);
        this.eventLocalDataSource.setEventCallback(this);
    }

    public MutableLiveData<Result> fetchEvents(String idToken, long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        // Recupera le notizie dal Web Service se l'ultimo download
        // delle notizie è stato eseguito più di FRESH_TIMEOUT tempo fa
        if (currentTime - lastUpdate > FRESH_TIMEOUT) {
            eventRemoteDataSource.getUserEvents(idToken);
        } else {
            eventLocalDataSource.getEvents();
        }

        return eventMutableLiveData;
    }


    @Override
    public void onFailureFromRemote(Exception exception) {

    }

    @Override
    public void onSuccessEventFromLocal(List<Event> eventList) {
        eventMutableLiveData.postValue(new Result.Event(eventList.get(0)));
    }

    @Override
    public void onFailureEventFromLocal(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        eventMutableLiveData.postValue(result);
    }

    @Override
    public void onDeleteEvent(Event e) {

    }

    @Override
    public void onInsertEvent(Event e) {

    }

    @Override
    public void onSuccessFromAuthentication(User user) {

    }

    @Override
    public void onFailureFromAuthentication(String message) {

    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {

    }

    @Override
    public void onSuccessEventsFromRemoteDatabase(List<Event> eventsList) {

    }

    @Override
    public void onSuccessFeelingsFromRemoteDatabase(List<Feeling> feelingList) {

    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {

    }

    @Override
    public void onSuccessLogout() {

    }
}