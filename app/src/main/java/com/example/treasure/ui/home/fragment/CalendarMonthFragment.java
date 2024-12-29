package com.example.treasure.ui.home.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.treasure.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarMonthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar_month, container, false);

        CalendarView calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year, month, dayOfMonth);

            SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", java.util.Locale.getDefault());

            String formattedDate = dateFormat.format(selectedCalendar.getTime());
            DailyPageFragment dialogFragment = DailyPageFragment.newInstance(formattedDate);
            dialogFragment.show(getParentFragmentManager(), "DailyPageFragment"); // Ora il dialogo
        });

        return view;
    }
}
