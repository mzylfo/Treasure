package com.example.treasure.repository.feeling;

import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;

import java.util.List;

public interface FeelingResponseCallback {
    void onFailureFromRemote(Exception exception);
    void onSuccessFeelingFromLocal(List<Feeling> feelingList);
    void onFailureFeelingFromLocal(Exception exception);
    void onDeleteFeeling(Feeling f);
    void onInsertFeeling(Feeling f);
}
