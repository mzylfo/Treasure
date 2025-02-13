package com.example.treasure.ui.welcome.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.treasure.model.Event;
import com.example.treasure.model.Result;
import com.example.treasure.model.User;
import com.example.treasure.repository.user.IUserRepository;

import java.util.Set;


public class UserViewModel extends ViewModel {
    private static final String TAG = UserViewModel.class.getSimpleName();

    private final IUserRepository userRepository;
    private MutableLiveData<Result> userMutableLiveData;
    private MutableLiveData<Result> userEventsMutableLiveData;
    private MutableLiveData<Result> userFeelingsMutableLiveData;
    private boolean authenticationError;

    public UserViewModel(IUserRepository userRepository) {
        this.userRepository = userRepository;
        authenticationError = false;
    }

    public MutableLiveData<Result> getUserMutableLiveData(
            String email, String password, boolean isUserRegistered) {
        if (userMutableLiveData == null) {
            getUserData(email, password, isUserRegistered);
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Result> getGoogleUserMutableLiveData(String token) {
        if (userMutableLiveData == null) {
            getUserData(token);
        }
        return userMutableLiveData;
    }

    /**EVENTS*/

    public MutableLiveData<Result> getUserEventsMutableLiveData(String idToken) {
        if (userEventsMutableLiveData == null) {
            getUserEvents(idToken);
        }
        return userEventsMutableLiveData;
    }

    public void saveUserEvent(String title, String date, String time, String idToken) {
        if (idToken != null) {
            userRepository.saveUserEvent(title, date, time, idToken);
        }
    }

    public MutableLiveData<Result> getUserEvents(String idToken) {
        if (idToken != null) {
            userEventsMutableLiveData = userRepository.getUserEvents(idToken);
        }
        return userEventsMutableLiveData;
    }

    /**FEELINGS*/
    public MutableLiveData<Result> getUserFeelingsMutableLiveData(String idToken) {
        if (userFeelingsMutableLiveData == null) {
            getUserFeelings(idToken);
        }
        return userFeelingsMutableLiveData;
    }

    public void saveUserFeeling(int face, String text, String date, String time, String condition, String idToken) {
        if (idToken != null) {
            userRepository.saveUserFeeling(face, text, date, time, condition, idToken);
        }
    }

    public void deleteUserEvent (Event event, String idToken) {
        if (idToken != null) {
            userRepository.deleteUserEvent(event, idToken);
        }
    }

    public MutableLiveData<Result> getUserFeelings(String idToken) {
        if (idToken != null) {
            userFeelingsMutableLiveData = userRepository.getUserFeelings(idToken);
        }
        return userFeelingsMutableLiveData;
    }


    public User getLoggedUser() {
        return userRepository.getLoggedUser();
    }

    public MutableLiveData<Result> logout() {
        if (userMutableLiveData == null) {
            userMutableLiveData = userRepository.logout();
        } else {
            userRepository.logout();
        }

        return userMutableLiveData;
    }


    public void getUser(String email, String password, boolean isUserRegistered) {
        userRepository.getUser(email, password, isUserRegistered);
    }

    public boolean isAuthenticationError() {
        return authenticationError;
    }

    public void setAuthenticationError(boolean authenticationError) {
        this.authenticationError = authenticationError;
    }

    private void getUserData(String email, String password, boolean isUserRegistered) {
        userMutableLiveData = userRepository.getUser(email, password, isUserRegistered);
    }

    private void getUserData(String token) {
        userMutableLiveData = userRepository.getGoogleUser(token);
    }
}