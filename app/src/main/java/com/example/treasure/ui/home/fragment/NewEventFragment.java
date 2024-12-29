package com.example.treasure.ui.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.treasure.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class NewEventFragment extends BottomSheetDialogFragment {

    private EditText eventNameEditText;
    private Button selectDateButton, selectTimeButton, saveEventButton, cancelButton;
    private TextView selectedDateTextView, selectedTimeTextView;
    private Calendar eventCalendar = Calendar.getInstance();

    public static NewEventFragment newInstance() {
        return new NewEventFragment();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        // Inizializzazione dei componenti
        eventNameEditText = view.findViewById(R.id.event_name);
        selectDateButton = view.findViewById(R.id.select_date_button);
        selectTimeButton = view.findViewById(R.id.select_time_button);
        saveEventButton = view.findViewById(R.id.save_event_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        selectedDateTextView = view.findViewById(R.id.event_date_label);
        selectedTimeTextView = view.findViewById(R.id.event_time_label);

        // Selettore della data con MaterialDatePicker
        selectDateButton.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build();

            datePicker.addOnPositiveButtonClickListener(selection -> {
                eventCalendar.setTimeInMillis(selection);
                selectedDateTextView.setText(eventCalendar.get(Calendar.DAY_OF_MONTH) + "/" + (eventCalendar.get(Calendar.MONTH) + 1) + "/" + eventCalendar.get(Calendar.YEAR));
            });

            datePicker.show(getParentFragmentManager(), "DATE_PICKER");
        });

        // Selettore dell'ora con MaterialTimePicker
        selectTimeButton.setOnClickListener(v -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(eventCalendar.get(Calendar.HOUR_OF_DAY))
                    .setMinute(eventCalendar.get(Calendar.MINUTE))
                    .setTitleText("Select Time")
                    .build();

            timePicker.addOnPositiveButtonClickListener(v1 -> {
                eventCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                eventCalendar.set(Calendar.MINUTE, timePicker.getMinute());
                selectedTimeTextView.setText(timePicker.getHour() + ":" + timePicker.getMinute());
            });

            timePicker.show(getParentFragmentManager(), "TIME_PICKER");
        });

        // Salvataggio evento
        saveEventButton.setOnClickListener(v -> {
            String eventName = eventNameEditText.getText().toString();

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

    @Override
    public void onStart() {
        super.onStart();

        // Forzare l'apertura della tastiera
        if (eventNameEditText != null) {
            eventNameEditText.requestFocus();  // Focalizza il campo EditText per nome evento
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(eventNameEditText, InputMethodManager.SHOW_IMPLICIT);  // Mostra la tastiera
        }
    }
}
