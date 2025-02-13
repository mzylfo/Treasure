package com.example.treasure.source.user;


import static com.example.treasure.util.Constants.*;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.treasure.database.EventRoomDatabase;
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
                            .addOnSuccessListener(aVoid -> userResponseCallback.onSuccessFromRemoteDatabase(user))
                            .addOnFailureListener(e -> userResponseCallback.onFailureFromRemoteDatabase(e.getLocalizedMessage()));
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
                    } else {
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());

                        List<Event> eventsList = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().getChildren()) {
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
                    } else {
                        Log.d(TAG, "Successful read: " + task.getResult().getValue());

                        List<Feeling> feelingsList = new ArrayList<>();
                        for (DataSnapshot ds : task.getResult().getChildren()) {
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

        Log.e("miao", "miao");

        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken).
                child(FIREBASE_EVENT_COLLECTION).push().setValue(eventValues)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Evento aggiunto con successo"))
                .addOnFailureListener(e -> Log.w(TAG, "Errore nell'aggiungere l'evento", e));
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
                child(FIREBASE_FEELING_COLLECTION).push().setValue(feelingValues)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Sentimento aggiunto con successo"))
                .addOnFailureListener(e -> Log.w(TAG, "Errore nell'aggiungere il sentimento", e));
    }

    @Override
    public void deleteUserEvent(Event e, String idToken){
        // Prima recuperiamo tutti gli eventi dell'utente
        databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken)
                .child(FIREBASE_EVENT_COLLECTION).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Se la lettura è riuscita, iteriamo attraverso gli eventi per trovare quello giusto
                        DataSnapshot eventsSnapshot = task.getResult();
                        for (DataSnapshot snapshot : eventsSnapshot.getChildren()) {
                            Event event = snapshot.getValue(Event.class);

                            // Qui confronti i dati dell'evento per identificare quello da eliminare.
                            // In questo esempio, stiamo confrontando con l'ID dell'evento.
                            if (event != null && event.getDate().equals(e.getDate()) && event.getTime().equals(e.getTime()) && event.getTitle().equals(e.getTitle())) {
                                // Se l'evento corrisponde a quello che vogliamo eliminare, rimuoviamo l'evento
                                String eventId = snapshot.getKey(); // Ottieni la chiave dell'evento (ID univoco generato da Firebase)
                                databaseReference.child(FIREBASE_USERS_COLLECTION).child(idToken)
                                        .child(FIREBASE_EVENT_COLLECTION).child(eventId).removeValue()
                                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Evento eliminato con successo"))
                                        .addOnFailureListener(ex -> Log.w(TAG, "Errore nell'eliminare l'evento", ex));
                                return; // Fermiamo il ciclo dopo aver trovato ed eliminato l'evento
                            }
                        }
                        Log.d(TAG, "Evento non trovato");
                    } else {
                        Log.w(TAG, "Errore nella lettura degli eventi", task.getException());
                    }
                });
}

}