package com.example.treasure.ui.home.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.treasure.R;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.database.FeelingRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewFeelingFragment extends BottomSheetDialogFragment {
    private EditText feelingEntryEditText;
    private Button saveEntryButton, cancelButton;

    private static final String ARG_FEELING = "feeling";
    private static final String ARG_DATE = "date";

    public static NewFeelingFragment newInstance(int feeling, String date) {
        NewFeelingFragment fragment = new NewFeelingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_FEELING, feeling);
        args.putString(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_feeling, container, false);

        feelingEntryEditText = view.findViewById(R.id.feeling_entry);
        saveEntryButton = view.findViewById(R.id.save_entry_button);
        cancelButton = view.findViewById(R.id.cancel_button);


        saveEntryButton.setOnClickListener(v -> saveEvent());

        cancelButton.setOnClickListener(v -> {
            dismiss();  // Chiude il BottomSheetDialog
            // Torna al DailyPageFragment
            // Assicurati che DailyPageFragment sia già gestito nel tuo flusso di navigazione
            getParentFragmentManager().popBackStack(); // Togli il fragment corrente dallo stack di navigazione
        });

        return view;

    }

    private void saveEvent(){
        if (getArguments() != null) {
            int entryFace = getArguments().getInt(ARG_FEELING);
            String date = getArguments().getString(ARG_DATE);
            // Usa l'intero "feeling" come necessario

            String entryText = feelingEntryEditText.getText().toString();
            String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

            Feeling newFeeling = new Feeling(entryText, entryFace, date, time);
            // Ottieni l'istanza del database
            FeelingRoomDatabase db = FeelingRoomDatabase.getDatabase(getContext());

            // Inserisci l'evento nel database
            new Thread(() -> {
                db.feelingDAO().insert(newFeeling);
            }).start();
        }

        dismiss();
    }

    private String formatTime(String time) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("H:mm", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date date = inputFormat.parse(time);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return time; // Ritorna l'ora originale in caso di errore
        }
    }
}
