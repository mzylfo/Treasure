package com.example.treasure.ui.home.fragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasure.R;
import com.example.treasure.adapter.EventRecyclerAdapter;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.ui.home.HomeActivity;
import com.example.treasure.util.JSONParserUtils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;
import java.util.Objects;

public class DailyPageFragment extends DialogFragment {

    private static final String ARG_DATE = "date";

    public static DailyPageFragment newInstance(String selectedDate) {
        DailyPageFragment fragment = new DailyPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATE, selectedDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_page, container, false);

        /* RIGUARDARE PER BORDI ARROTONDATI
        View bottomSheet = view.findViewById(R.id.daily_page);
        if (bottomSheet != null) {
            bottomSheet.setBackgroundResource(R.drawable.rounded_corners_fragment);
        }
        */

        // Retrieve the selected date
        String date = getArguments() != null ? getArguments().getString(ARG_DATE) : "No Date";
        TextView dateTextView = view.findViewById(R.id.selectedDateTextView);
        dateTextView.setText(date);

        JSONParserUtils jsonParserUtils = new JSONParserUtils(getContext());

        RecyclerView eventsRecyclerView = view.findViewById(R.id.recyclerNextEvents);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        List<Event> eventList = EventRoomDatabase.getDatabase(view.getContext()).eventDAO().getAll();

        EventRecyclerAdapter adapter = new EventRecyclerAdapter(R.layout.card_event, eventList);

        eventsRecyclerView.setAdapter(adapter);

        view.findViewById(R.id.back_button).setOnClickListener(v -> {
            // Quando il pulsante "indietro" viene premuto, semplicemente chiudi il dialogo
            dismiss();
        });

        view.findViewById(R.id.button_plus).setOnClickListener(v -> {
            NewEventFragment newEventFragment = NewEventFragment.newInstance();
            newEventFragment.show(getParentFragmentManager(), newEventFragment.getTag());
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.9);
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
            getDialog().getWindow().setBackgroundDrawableResource(R.drawable.rounded_dialog);

        }
    }

}