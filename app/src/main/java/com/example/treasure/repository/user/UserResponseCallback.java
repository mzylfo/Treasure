package com.example.treasure.repository.user;

import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.example.treasure.model.Weather;
import com.example.treasure.model.User;

import java.util.List;

public interface UserResponseCallback {
    void onSuccessFromAuthentication(User user);
    void onFailureFromAuthentication(String message);
    void onSuccessFromRemoteDatabase(User user);
    void onSuccessEventsFromRemoteDatabase(List<Event> eventsList);
    void onSuccessFeelingsFromRemoteDatabase(List<Feeling> feelingList);
    //void onSuccessFromGettingUserPreferences();
    void onFailureFromRemoteDatabase(String message);
    void onSuccessLogout();
}