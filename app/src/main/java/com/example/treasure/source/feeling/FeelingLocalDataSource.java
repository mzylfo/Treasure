package com.example.treasure.source.feeling;

import static com.example.treasure.util.Constants.UNEXPECTED_ERROR;

import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.database.FeelingDAO;
import com.example.treasure.database.FeelingRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.example.treasure.source.feeling.BaseFeelingLocalDataSource;
import com.example.treasure.util.Constants;
import com.example.treasure.util.SharedPreferencesUtils;

import java.util.List;


/**
 * Class to get news from local database using Room.
 */
public class FeelingLocalDataSource extends BaseFeelingLocalDataSource {

    private final FeelingDAO feelingDAO;
    private final SharedPreferencesUtils sharedPreferencesUtil;

    public FeelingLocalDataSource(FeelingRoomDatabase feelingsRoomDatabase,
                                  SharedPreferencesUtils sharedPreferencesUtil) {
        this.feelingDAO = feelingsRoomDatabase.feelingDAO();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    /**
     * Gets the news from the local database.
     * The method is executed with an ExecutorService defined in NewsRoomDatabase class
     * because the database access cannot been executed in the main thread.
     */
    @Override
    public void getFeelings() {
        FeelingRoomDatabase.databaseWriteExecutor.execute(() -> {
            feelingCallback.onSuccessFeelingFromLocal(feelingDAO.getAll());
        });
    }

    @Override
    public void deleteFeeling(Feeling feeling) {
        FeelingRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Feeling> eList = feelingDAO.getAll();

            feelingDAO.delete(feeling);

            if (eList.size() != (feelingDAO.getAll().size())) {
                feelingCallback.onDeleteFeeling(feeling);
            } else {
                feelingCallback.onFailureFeelingFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    @Override
    public void insertFeeling(int face, String text, String date, String time, String condition) {
        Feeling e = new Feeling(face, text, date, time, condition);
        FeelingRoomDatabase.databaseWriteExecutor.execute(() -> {

            List<Feeling> eList = feelingDAO.getAll();

            feelingDAO.insert(e);

            if (eList.size() != (feelingDAO.getAll().size())) {
                feelingCallback.onInsertFeeling(e);
            } else {
                feelingCallback.onFailureFeelingFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });

        sharedPreferencesUtil.writeStringData(Constants.SHARED_PREFERENCES_FILENAME,
                Constants.SHARED_PREFERNECES_LAST_UPDATE, String.valueOf(System.currentTimeMillis()));

        feelingCallback.onInsertFeeling(e);
    }


    @Override
    public void insertFeelings(List<Feeling> feelingList) {
        FeelingRoomDatabase.databaseWriteExecutor.execute(() -> {
            feelingDAO.deleteAll();

            for (Feeling f : feelingList) {
                feelingDAO.insert(f);
            }
        });
    }
}



