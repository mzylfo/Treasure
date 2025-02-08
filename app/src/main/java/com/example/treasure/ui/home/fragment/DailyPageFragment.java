package com.example.treasure.ui.home.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasure.R;
import com.example.treasure.adapter.EventRecyclerAdapter;
import com.example.treasure.adapter.FeelingRecyclerAdapter;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.database.FeelingRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.model.Feeling;

import java.util.List;

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

        // Retrieve the selected date
        String date = getArguments() != null ? getArguments().getString(ARG_DATE) : "No Date";
        TextView dateTextView = view.findViewById(R.id.selectedDateTextView);
        dateTextView.setText(date);

        // RecyclerView per gli eventi
        RecyclerView eventsRecyclerView = view.findViewById(R.id.recyclerNextEvents);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Event> eventList = EventRoomDatabase.getDatabase(getContext()).eventDAO().getEventsByDate(date);
        EventRecyclerAdapter eventAdapter = new EventRecyclerAdapter(R.layout.card_event, eventList);
        eventsRecyclerView.setAdapter(eventAdapter);

        // Imposta il listener di clic
        eventAdapter.setOnItemClickListener(event -> {
            // Mostra l'AlertDialog quando un evento viene cliccato
            showDeleteDialog(event);
        });

        // RecyclerView per feelings
        RecyclerView feelingsRecyclerView = view.findViewById(R.id.recyclerFeelings);
        feelingsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        List<Feeling> feelingList = FeelingRoomDatabase.getDatabase(view.getContext()).feelingDAO().getFeelingsByDate(date);
        FeelingRecyclerAdapter adapterFeeling = new FeelingRecyclerAdapter(R.layout.card_feeling, feelingList);
        feelingsRecyclerView.setAdapter(adapterFeeling);

        // Azione per il pulsante indietro
        view.findViewById(R.id.back_button).setOnClickListener(v -> {
            dismiss();
        });

        // Azione per il pulsante di aggiunta di un nuovo evento
        /*view.findViewById(R.id.button_plus).setOnClickListener(v -> {
            NewEventFragment newEventFragment = NewEventFragment.newInstance(date);
            newEventFragment.show(getParentFragmentManager(), newEventFragment.getTag());
        });*/

        return view;
    }

    // Metodo per visualizzare il dialog di eliminazione evento
    private void showDeleteDialog(Event event) {
        new AlertDialog.Builder(getContext())
                .setTitle("Vuoi eliminare questo evento?")
                .setMessage("Sei sicuro di voler eliminare questo evento?")
                .setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Elimina l'evento dal database
                        EventRoomDatabase.getDatabase(getContext()).eventDAO().delete(event);
                        // Mostra un messaggio di conferma
                        Toast.makeText(getContext(), "Evento eliminato", Toast.LENGTH_SHORT).show();
                        // Ricarica la lista degli eventi
                        reloadEventList();
                        //Ricarica i next Events
                        requireActivity().getSupportFragmentManager().setFragmentResult("event_deleted", new Bundle());
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss(); // Chiudi il dialog senza fare nulla
                    }
                })
                .show();
    }

    // Metodo per ricaricare la lista degli eventi dopo l'eliminazione
    private void reloadEventList() {
        String date = getArguments() != null ? getArguments().getString(ARG_DATE) : "No Date";
        List<Event> updatedEventList = EventRoomDatabase.getDatabase(getContext()).eventDAO().getEventsByDate(date);

        // Recupera la RecyclerView e aggiorna il suo adattatore
        RecyclerView eventsRecyclerView = getView().findViewById(R.id.recyclerNextEvents);
        EventRecyclerAdapter updatedAdapter = new EventRecyclerAdapter(R.layout.card_event, updatedEventList);
        eventsRecyclerView.setAdapter(updatedAdapter);
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