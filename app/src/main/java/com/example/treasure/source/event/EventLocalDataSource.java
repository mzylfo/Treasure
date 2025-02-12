package com.example.treasure.source.event;

import static com.example.treasure.util.Constants.UNEXPECTED_ERROR;

import static java.security.AccessController.getContext;

import android.os.Bundle;

import com.example.treasure.database.EventDAO;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.util.Constants;
import com.example.treasure.util.SharedPreferencesUtils;

import java.util.List;


/**
 * Class to get news from local database using Room.
 */
public class EventLocalDataSource extends BaseEventLocalDataSource {

    private final EventDAO eventDAO;
    private final SharedPreferencesUtils sharedPreferencesUtil;

    public EventLocalDataSource(EventRoomDatabase eventsRoomDatabase,
                               SharedPreferencesUtils sharedPreferencesUtil) {
        this.eventDAO = eventsRoomDatabase.eventDAO();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    /**
     * Gets the news from the local database.
     * The method is executed with an ExecutorService defined in NewsRoomDatabase class
     * because the database access cannot been executed in the main thread.
     */
    @Override
    public void getEvents() {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            eventCallback.onSuccessFromLocal(eventDAO.getAll());
        });
    }

    @Override
    public void deleteEvent(Event event) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Event> eList = eventDAO.getAll();

            eventDAO.delete(event);

            if (eList.size() != (eventDAO.getAll().size())){
                eventCallback.onDeleteEvent(event);
            } else {
                eventCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });
    }

    @Override
    public void insertEvent(String name, String date, String time) {
        Event e = new Event(name, date, time);
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            List<Event> eList = eventDAO.getAll();

            eventDAO.insert(e);

            if (eList.size() != (eventDAO.getAll().size())){
                eventCallback.onInsertEvent(e);
            } else {
                eventCallback.onFailureFromLocal(new Exception(UNEXPECTED_ERROR));
            }
        });

            sharedPreferencesUtil.writeStringData(Constants.SHARED_PREFERENCES_FILENAME,
                        Constants.SHARED_PREFERNECES_LAST_UPDATE, String.valueOf(System.currentTimeMillis()));

                eventCallback.onInsertEvent(e);
    }

    @Override
    public void insertEvents(List<Event> eventList){
        EventRoomDatabase.databaseWriteExecutor.execute(()->{
            eventDAO.deleteAll();

            for (Event e : eventList) {
                eventDAO.insert(e);
            }
        });

        eventCallback.onSuccessFromLocal(eventList);
    }
}

