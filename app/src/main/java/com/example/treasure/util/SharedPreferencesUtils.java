package com.example.treasure.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferencesUtils {
    private SharedPreferences sharedPreferences;

    public SharedPreferencesUtils(Context context) {
        sharedPreferences = context.getSharedPreferences("CalendarJournalPrefs", Context.MODE_PRIVATE);
    }

    public void saveCalendarEvent(String eventId, String eventDetails) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("calendar_event_" + eventId, eventDetails);
        editor.apply();
    }

    public String getCalendarEvent(String eventId) {
        return sharedPreferences.getString("calendar_event_" + eventId, null);
    }

    public void saveJournalEntry(String entryId, String entryDetails) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("journal_entry_" + entryId, entryDetails);
        editor.apply();
    }

    public String getJournalEntry(String entryId) {
        return sharedPreferences.getString("journal_entry_" + entryId, null);
    }

    public void deleteCalendarEvent(String eventId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("calendar_event_" + eventId);
        editor.apply();
    }

    public void deleteJournalEntry(String entryId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("journal_entry_" + entryId);
        editor.apply();
    }

    public Map<String, ?> getAllCalendarEvents() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        Map<String, String> calendarEvents = new HashMap<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("calendar_event_")) {
                calendarEvents.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return calendarEvents;
    }

    public Map<String, ?> getAllJournalEntries() {
        Map<String, ?> allEntries = sharedPreferences.getAll();
        Map<String, String> journalEntries = new HashMap<>();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().startsWith("journal_entry_")) {
                journalEntries.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return journalEntries;
    }

    /*
    private final Context context;

    public SharedPreferencesUtils(Context context) {
        this.context = context;
    }

    public void writeStringData(String sharedPreferencesFileName, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void writeStringSetData(String sharedPreferencesFileName, String key, Set<String> value) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    public String readStringData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    public Set<String> readStringSetData(String sharedPreferencesFileName, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(sharedPreferencesFileName,
                Context.MODE_PRIVATE);
        return sharedPref.getStringSet(key, null);
    }
     */
}