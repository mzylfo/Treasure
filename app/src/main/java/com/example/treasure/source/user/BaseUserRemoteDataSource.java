package com.example.treasure.source.user;


import com.example.treasure.model.User;
import com.example.treasure.repository.user.UserResponseCallback;

public abstract class BaseUserRemoteDataSource {
    protected UserResponseCallback userResponseCallback;

    public void setUserResponseCallback(UserResponseCallback userResponseCallback) {
        this.userResponseCallback = userResponseCallback;
    }

    public abstract void saveUserData(User user);

    public abstract void getUserEvents(String idToken);

    public abstract void getUserFeelings(String idToken);

    public abstract void saveUserEvent(String title, String date, String time, String idToken) ;

    public abstract void saveUserFeeling(int face, String text, String date, String time, String condition, String idToken);
}