package com.example.treasure.source.user;


import static com.example.treasure.util.Constants.*;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.treasure.model.User;
import com.example.treasure.util.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class that gets the user information using Firebase Realtime Database.
 */
public class UserFirebaseDataSource extends BaseUserRemoteDataSource {

    private static final String TAG = UserFirebaseDataSource.class.getSimpleName();

    private final DatabaseReference databaseReference;
    private final SharedPreferencesUtils sharedPreferencesUtil;

    public UserFirebaseDataSource(SharedPreferencesUtils sharedPreferencesUtil) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_REALTIME_DATABASE);
        databaseReference = firebaseDatabase.getReference().getRef();
        this.sharedPreferencesUtil = sharedPreferencesUtil;
    }

    @Override
    public void saveUserData(User user) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d(TAG, "User already present in Firebase Realtime Database");
                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                } else {
                    Log.d(TAG, "User not present in Firebase Realtime Database");
                    databaseReference.child(FIREBASE_USERS_COLLECTION).child(user.getIdToken()).setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    userResponseCallback.onSuccessFromRemoteDatabase(user);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    userResponseCallback.onFailureFromRemoteDatabase(e.getLocalizedMessage());
                                }
                            });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                userResponseCallback.onFailureFromRemoteDatabase(error.getMessage());
            }
        });
    }


    @Override
    public void getUserEvents(String idToken) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_EVENT_COLLECTION).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "Error getting data", task.getException());
                        userResponseCallback.onFailureFromRemoteDatabase(task.getException().getLocalizedMessage());
                    }
                    else {
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());

                        List<Event> eventsList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()) {
                            Event event = ds.getValue(Event.class);
                            eventsList.add(event);
                        }

                        userResponseCallback.onSuccessEventsFromRemoteDatabase(eventsList);
                    }

                });

    }

    @Override
    public void getUserFeelings(String idToken) {
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FEELING_COLLECTION).get().addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.d(TAG, "Error getting data", task.getException());
                        userResponseCallback.onFailureFromRemoteDatabase(task.getException().getLocalizedMessage());
                    }
                    else {
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());

                        List<Feeling> feelingsList = new ArrayList<>();
                        for(DataSnapshot ds : task.getResult().getChildren()) {
                            Feeling feeling = ds.getValue(Feeling.class);
                            feelingsList.add(feeling);
                        }

                        userResponseCallback.onSuccessFeelingsFromRemoteDatabase(feelingsList);
                    }
                });
    }

    @Override
    public void saveUserEvent(String title, String date, String time, String idToken) {

        Map<String, Object> eventValues = new HashMap<>();
        eventValues.put("title", title);
        eventValues.put("date", date);
        eventValues.put("time", time);

        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_EVENT_COLLECTION).setValue(eventValues);

    }

    @Override
    public void saveUserFeeling(int face, String text, String date, String time, String condition, String idToken) {

        Map<String, Object> feelingValues = new HashMap<>();
        feelingValues.put("face", face);
        feelingValues.put("title", text);
        feelingValues.put("date", date);
        feelingValues.put("time", time);
        feelingValues.put("condition", condition);

        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_FEELING_COLLECTION).setValue(feelingValues);

    }

}