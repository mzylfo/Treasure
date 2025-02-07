package com.example.treasure.ui.home.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.treasure.R;
import com.example.treasure.database.FeelingRoomDatabase;
import com.example.treasure.model.Feeling;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class CalendarMonthFragment extends Fragment {
    private SimpleDateFormat date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //andiamo a riprendere la data dal telefono
        date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        View view = inflater.inflate(R.layout.fragment_calendar_month, container, false);
        CalendarView calendarView = view.findViewById(R.id.calendarView);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year, month, dayOfMonth);

            String formattedDate = date.format(selectedCalendar.getTime());
            DailyPageFragment dialogFragment = DailyPageFragment.newInstance(formattedDate);
            dialogFragment.show(getParentFragmentManager(), "DailyPageFragment"); // Ora il dialogo
        });

        return view;
    }

}
