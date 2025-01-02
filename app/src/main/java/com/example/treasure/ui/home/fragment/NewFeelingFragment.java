package com.example.treasure.ui.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.treasure.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewFeelingFragment extends BottomSheetDialogFragment {
    private EditText feelingEntryEditText;
    private Button saveEntryButton, cancelButton;

    public static NewFeelingFragment newInstance() { return new NewFeelingFragment(); }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_feeling, container, false);

        feelingEntryEditText = view.findViewById(R.id.feeling_entry);
        saveEntryButton = view.findViewById(R.id.save_entry_button);
        cancelButton = view.findViewById(R.id.cancel_button);

        saveEntryButton.setOnClickListener(v -> {
            String entry = feelingEntryEditText.getText().toString();

            // DA IMPLEMENTARE IL SALVATAGGIO DELL'EVENTO

            dismiss();  // Chiude il BottomSheetDialog
        });

        cancelButton.setOnClickListener(v -> {
            dismiss();  // Chiude il BottomSheetDialog
            // Torna al DailyPageFragment
            // Assicurati che DailyPageFragment sia già gestito nel tuo flusso di navigazione
            getParentFragmentManager().popBackStack(); // Togli il fragment corrente dallo stack di navigazione
        });

        return view;

    }
}
