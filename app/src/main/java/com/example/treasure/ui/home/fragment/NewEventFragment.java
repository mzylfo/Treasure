package com.example.treasure.ui.home.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasure.R;
import com.example.treasure.adapter.EventRecyclerAdapter;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.repository.user.IUserRepository;
import com.example.treasure.ui.welcome.viewmodel.UserViewModel;
import com.example.treasure.ui.welcome.viewmodel.UserViewModelFactory;
import com.example.treasure.util.DateParser;
import com.example.treasure.util.JSONParserUtils;
import com.example.treasure.util.ServiceLocator;
import com.example.treasure.util.TimeParser;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewEventFragment extends BottomSheetDialogFragment {

    private static final String ARG_DATE = "date";
    private String selectedDate;
    private EditText eventNameEditText;
    private Button selectDateButton, selectTimeButton, saveEventButton, cancelButton;
    private TextView selectedDateTextView, selectedTimeTextView;
    private Calendar eventCalendar = Calendar.getInstance();

    private UserViewModel userViewModel;

    public static NewEventFragment newInstance(String date) {
        NewEventFragment fragment = new NewEventFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            selectedDate = getArguments().getString(ARG_DATE);
        }

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        eventNameEditText = view.findViewById(R.id.event_name);
        selectDateButton = view.findViewById(R.id.select_date_button);
        selectTimeButton = view.findViewById(R.id.select_time_button);
        saveEventButton = view.findViewById(R.id.save_event_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        selectedDateTextView = view.findViewById(R.id.event_date_label);
        selectedDateTextView.setText(selectedDate);
        selectedTimeTextView = view.findViewById(R.id.event_time_label);

        selectDateButton.setOnClickListener(v -> showDatePicker());
        selectTimeButton.setOnClickListener(v -> showTimePicker());
        saveEventButton.setOnClickListener(v -> saveEvent());
        cancelButton.setOnClickListener(v -> dismiss());

        return view;
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker().build();
        datePicker.addOnPositiveButtonClickListener(selection -> {
            eventCalendar.setTimeInMillis(selection);
            selectedDateTextView.setText(datePicker.getHeaderText());
        });
        datePicker.show(getParentFragmentManager(), "datePicker");
    }

    private void showTimePicker() {
        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build();
        timePicker.addOnPositiveButtonClickListener(v -> {
            eventCalendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
            eventCalendar.set(Calendar.MINUTE, timePicker.getMinute());
            selectedTimeTextView.setText(String.format(Locale.getDefault(), "%02d:%02d", timePicker.getHour(), timePicker.getMinute()));
        });
        timePicker.show(getParentFragmentManager(), "timePicker");
    }

    private void saveEvent() {
        String eventName = eventNameEditText.getText().toString();
        String eventDate = selectedDateTextView.getText().toString();
        String eventTime = selectedTimeTextView.getText().toString();

        // Formattiamo la data nel formato desiderato
        String formattedDate = DateParser.formatDate(eventDate);
        Log.e("DATE", formattedDate);

        // Formatta l'ora nel formato desiderato
        String formattedTime = TimeParser.formatTime(eventTime);
        Log.e("TIME", formattedTime);

        Event newEvent = new Event(eventName, formattedDate, formattedTime);

        // Ottieni l'istanza del database
        EventRoomDatabase db = EventRoomDatabase.getDatabase(getContext());

        // Inserisci l'evento nel database
        new Thread(() -> {
            db.eventDAO().insert(newEvent);
            requireActivity().getSupportFragmentManager().setFragmentResult("event_added", new Bundle());
        }).start();

        userViewModel.saveUserEvent(
                eventName, eventDate, eventTime,
                userViewModel.getLoggedUser().getIdToken());



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