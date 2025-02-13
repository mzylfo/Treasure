package com.example.treasure.source.feeling;

import com.example.treasure.model.Feeling;
import com.example.treasure.repository.feeling.FeelingResponseCallback;
import com.example.treasure.repository.weather.WeatherResponseCallback;

import java.util.List;

public abstract class BaseFeelingLocalDataSource {
    protected FeelingResponseCallback feelingCallback;

    public void setFeelingCallback(FeelingResponseCallback feelingCallback) {
        this.feelingCallback = feelingCallback;
    }

    public abstract void getFeelings();

    public abstract void deleteFeeling(Feeling feeling);

    public abstract void insertFeeling(int face, String text, String date, String time, String condition);

    public abstract void insertFeelings(List<Feeling> feelingList);
}