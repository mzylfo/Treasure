
package com.example.treasure.repository.user;

import androidx.lifecycle.MutableLiveData;

import com.example.treasure.model.Result;
import com.example.treasure.model.User;

import java.util.Set;

public interface IUserRepository {
    MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered);
    MutableLiveData<Result> getGoogleUser(String idToken);
    MutableLiveData<Result> getUserEvents(String idToken);
    MutableLiveData<Result> getUserFeelings(String idToken);
    MutableLiveData<Result> logout();
    User getLoggedUser();
    void signUp(String email, String password);
    void signIn(String email, String password);
    void signInWithGoogle(String token);
    void saveUserEvents(String title, String date, String time, String idToken);
    void saveUserFeelings(int face, String text, String date, String time, String condition);
}
