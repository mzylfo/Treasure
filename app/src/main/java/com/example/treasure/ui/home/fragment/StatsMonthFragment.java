package com.example.treasure.ui.home.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.treasure.R;
import com.example.treasure.database.FeelingRoomDatabase;
import com.example.treasure.model.Feeling;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;


public class StatsMonthFragment extends Fragment {
    private Spinner spinnerMonth, spinnerYear;
    private Button btnGenerateStats;
    private TextView txtHappiness, txtSadness, txtNeutral;
    private int selectedMonth, selectedYear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats_month, container, false);

        // Inizializza UI
        spinnerMonth = view.findViewById(R.id.spinner_month);
        spinnerYear = view.findViewById(R.id.spinner_year);
        btnGenerateStats = view.findViewById(R.id.btn_generate_stats);
        txtHappiness = view.findViewById(R.id.happinessContainer);
        txtSadness = view.findViewById(R.id.sadnessContainer);
        txtNeutral = view.findViewById(R.id.neutralContainer);

        // Popoliamo gli Spinner
        setupMonthSpinner();
        setupYearSpinner();

        // Listener per il pulsante
        btnGenerateStats.setOnClickListener(v -> generateStats());

        return view;
    }

    private void setupMonthSpinner() {
        String[] months = new String[]{
                "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno",
                "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, months);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapter);

        selectedMonth = Calendar.getInstance().get(Calendar.MONTH);
        spinnerMonth.setSelection(selectedMonth);

        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupYearSpinner() {
        List<String> years = new ArrayList<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i > currentYear - 10; i--) {
            years.add(String.valueOf(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(yearAdapter);

        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = Integer.parseInt((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Nessuna azione necessaria
            }
        });

    }

    private void generateStats() {
        // Recupera il mese e l'anno selezionati
        int month = selectedMonth;
        int year = selectedYear;

        //counters
        AtomicInteger sad = new AtomicInteger();
        AtomicInteger happy = new AtomicInteger();
        AtomicInteger neutral = new AtomicInteger();

        // Crea una data con il mese e l'anno selezionati
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        // Formatta la data nel formato "MMMM yyyy"
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        List<Feeling> list = FeelingRoomDatabase.getDatabase(getContext()).feelingDAO().getAll();

        list.forEach(feeling -> {
            if(feeling.getDate().toString().contains(formattedDate)){
                if(feeling.getFace()==-1)
                    sad.getAndIncrement();
                else if(feeling.getFace()==0)
                    neutral.getAndIncrement();
                else
                    happy.getAndIncrement();
            }
        });

        int sum = sad.get()+neutral.get()+happy.get();

        int percentageHappy = percentage(sum, happy.get());
        int percentageNeutral = percentage(sum, neutral.get());
        int percentageSad = percentage(sum, sad.get());

        Log.e("happy", String.valueOf(sad.get()));

        txtHappiness.setText("You were happy the "+ percentageHappy + "% of the time");
        txtNeutral.setText("You were neutral the "+ percentageNeutral+ "% of the time");
        txtSadness.setText("You were sad the "+ percentageSad+ "% of the time");
        }

        private int percentage(int sum, int piece){
            if(sum>0)
                return (piece*100)/sum;
            return 0;
        }
    }
