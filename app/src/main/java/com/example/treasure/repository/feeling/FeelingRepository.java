package com.example.treasure.repository.feeling;

import androidx.lifecycle.MutableLiveData;

import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.example.treasure.model.Feeling;
import com.example.treasure.model.Result;
import com.example.treasure.model.User;
import com.example.treasure.repository.user.UserResponseCallback;
import com.example.treasure.source.feeling.BaseFeelingLocalDataSource;
import com.example.treasure.source.user.BaseUserRemoteDataSource;

import java.util.List;

public class FeelingRepository implements UserResponseCallback, FeelingResponseCallback {

    private static final long FRESH_TIMEOUT = 60000; // 1 minuto per esempio

    private final BaseFeelingLocalDataSource feelingLocalDataSource;
    private final BaseUserRemoteDataSource feelingRemoteDataSource;
    private final MutableLiveData<Result> feelingMutableLiveData;

    public FeelingRepository(BaseUserRemoteDataSource feelingRemoteDataSource,
                           BaseFeelingLocalDataSource feelingLocalDataSource) {
        this.feelingRemoteDataSource = feelingRemoteDataSource;
        this.feelingLocalDataSource = feelingLocalDataSource;
        this.feelingMutableLiveData = new MutableLiveData<>();
        this.feelingRemoteDataSource.setUserResponseCallback((UserResponseCallback) this);
        this.feelingLocalDataSource.setFeelingCallback(this);
    }

    public MutableLiveData<Result> fetchFeelings(String idToken, long lastUpdate) {
        long currentTime = System.currentTimeMillis();

        // Recupera le notizie dal Web Service se l'ultimo download
        // delle notizie è stato eseguito più di FRESH_TIMEOUT tempo fa
        if (currentTime - lastUpdate > FRESH_TIMEOUT) {
            feelingRemoteDataSource.getUserFeelings(idToken);
        } else {
            feelingLocalDataSource.getFeelings();
        }

        return feelingMutableLiveData;
    }


    @Override
    public void onFailureFromRemote(Exception exception) {

    }

    @Override
    public void onSuccessFeelingFromLocal(List<Feeling> feelingList) {
        feelingMutableLiveData.postValue(new Result.Feeling(feelingList.get(0)));
    }

    @Override
    public void onFailureFeelingFromLocal(Exception exception) {
        Result.Error result = new Result.Error(exception.getMessage());
        feelingMutableLiveData.postValue(result);
    }

    @Override
    public void onDeleteFeeling(Feeling e) {

    }

    @Override
    public void onInsertFeeling(Feeling e) {

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
    public void onSuccessFeelingsFromRemoteDatabase(List<Feeling> feelingsList) {

    }

    @Override
    public void onFailureFromRemoteDatabase(String message) {

    }

    @Override
    public void onSuccessLogout() {

    }
}