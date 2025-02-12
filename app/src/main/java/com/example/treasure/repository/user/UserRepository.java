
package com.example.treasure.repository.user;

import androidx.lifecycle.MutableLiveData;

import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.example.treasure.model.Weather;
import com.example.treasure.model.Result;
import com.example.treasure.model.User;
import com.example.treasure.repository.event.EventResponseCallback;
import com.example.treasure.repository.feeling.FeelingResponseCallback;
import com.example.treasure.repository.weather.WeatherResponseCallback;
import com.example.treasure.source.event.BaseEventLocalDataSource;
import com.example.treasure.source.feeling.BaseFeelingLocalDataSource;
import com.example.treasure.source.user.BaseUserAuthenticationRemoteDataSource;
import com.example.treasure.source.user.BaseUserDataRemoteDataSource;
import com.example.treasure.source.weather.BaseWeatherLocalDataSource;

import java.util.List;


/**
 * Repository class to get the user information.
 */
public class UserRepository implements IUserRepository, UserResponseCallback, WeatherResponseCallback, EventResponseCallback, FeelingResponseCallback {

    private static final String TAG = UserRepository.class.getSimpleName();

    private final BaseUserAuthenticationRemoteDataSource userRemoteDataSource;
    private final BaseUserDataRemoteDataSource userDataRemoteDataSource;
    private final BaseEventLocalDataSource eventLocalDataSource;
    private final BaseFeelingLocalDataSource feelingLocalDataSource;

    private final BaseWeatherLocalDataSource weatherLocalDataSource;
    private final MutableLiveData<Result> userMutableLiveData;
    private final MutableLiveData<Result> userEventsMutableLiveData;
    private final MutableLiveData<Result> userFeelingsMutableLiveData;

    public UserRepository(BaseUserAuthenticationRemoteDataSource userRemoteDataSource,
                          BaseUserDataRemoteDataSource userDataRemoteDataSource,
                          BaseWeatherLocalDataSource weatherLocalDataSource,
                          BaseEventLocalDataSource eventLocalDataSource,
                          BaseFeelingLocalDataSource feelingLocalDataSource) {
        this.userRemoteDataSource = userRemoteDataSource;
        this.userDataRemoteDataSource = userDataRemoteDataSource;
        this.weatherLocalDataSource = weatherLocalDataSource;
        this.userMutableLiveData = new MutableLiveData<>();
        this.userEventsMutableLiveData = new MutableLiveData<>();
        this.userFeelingsMutableLiveData = new MutableLiveData<>();
        this.eventLocalDataSource = eventLocalDataSource;
        this.feelingLocalDataSource = feelingLocalDataSource;
        this.userRemoteDataSource.setUserResponseCallback(this);
        this.userDataRemoteDataSource.setUserResponseCallback(this);
        this.weatherLocalDataSource.setWeatherResponseCallback(this);
        this.eventLocalDataSource.setEventCallback(this);
        this.feelingLocalDataSource.setFeelingCallback(this);
    }

    @Override
    public MutableLiveData<Result> getUser(String email, String password, boolean isUserRegistered) {
        if (isUserRegistered) {
            signIn(email, password);
        } else {
            signUp(email, password);
        }
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getGoogleUser(String idToken) {
        signInWithGoogle(idToken);
        return userMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getUserEvents(String idToken) {
        userDataRemoteDataSource.getUserEvents(idToken);
        return userEventsMutableLiveData;
    }

    @Override
    public MutableLiveData<Result> getUserFeelings(String idToken) {
        userDataRemoteDataSource.getUserFeelings(idToken);
        return userFeelingsMutableLiveData;
    }

    @Override
    public User getLoggedUser() {
        return userRemoteDataSource.getLoggedUser();
    }

    @Override
    public MutableLiveData<Result> logout() {
        userRemoteDataSource.logout();
        return userMutableLiveData;
    }

    @Override
    public void signUp(String email, String password) {
        userRemoteDataSource.signUp(email, password);
    }

    @Override
    public void signIn(String email, String password) {
        userRemoteDataSource.signIn(email, password);
    }

    @Override
    public void signInWithGoogle(String token) {
        userRemoteDataSource.signInWithGoogle(token);
    }

    @Override
    public void saveUserEvent(String title, String date, String time, String idToken) {
        userDataRemoteDataSource.saveUserEvent(title, date, time, idToken);
    }

    @Override
    public void saveUserFeeling(int face, String text, String date, String time, String condition, String idToken) {
        userDataRemoteDataSource.saveUserFeeling(face, text, date, time, condition, idToken);
    }

    @Override
    public void onSuccessFromAuthentication(User user) {
        if (user != null) {
            userDataRemoteDataSource.saveUserData(user);
        }
    }

    @Override
    public void onFailureFromAuthentication(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(User user) {
        Result.UserSuccess result = new Result.UserSuccess(user);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessFromRemoteDatabase(List<Event> eventList, List<Feeling> feelingList) {
        eventLocalDataSource.insertEvents(eventList);
        feelingLocalDataSource.insertFeelings(feelingList);
    }

    /*@Override
    public void onSuccessFromGettingUserPreferences() {
        userPreferencesMutableLiveData.postValue(new Result.UserSuccess(null));
    }*/

    @Override
    public void onFailureFromRemoteDatabase(String message) {
        Result.Error result = new Result.Error(message);
        userMutableLiveData.postValue(result);
    }

    @Override
    public void onSuccessLogout() {

    }

    @Override
    public void onSuccessFromRemote(Weather articleAPIResponse, long lastUpdate) {

    }

    @Override
    public void onFailureFromRemote(Exception exception) {

    }

    @Override
    public void onSuccessFromLocal(List<Feeling> feelingList) {

    }

    @Override
    public void onSuccessFromLocal(List<Event> eventList) {

    }

    @Override
    public void onSuccessFromLocal(Weather weather) {

    }

    @Override
    public void onFailureFromLocal(Exception exception) {

    }

    @Override
    public void onDeleteFeeling(Feeling f) {

    }

    @Override
    public void onInsertFeeling(Feeling f) {

    }

    @Override
    public void onDeleteEvent(Event e) {

    }

    @Override
    public void onInsertEvent(Event e) {

    }

    @Override
    public void onWeatherStatusChanged(Weather weather) {

    }

}
