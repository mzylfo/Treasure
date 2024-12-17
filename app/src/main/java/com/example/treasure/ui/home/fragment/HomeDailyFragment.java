package com.example.treasure.ui.home.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.treasure.R;
import com.example.treasure.ui.home.HomeActivity;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeDailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeDailyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeDailyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeDailyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeDailyFragment newInstance(String param1, String param2) {
        HomeDailyFragment fragment = new HomeDailyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_daily, container, false);
        //potrebbe essere un problema perchè vado a sincronizzarmi col telefono e non con una api esterna
        // Ottieni e formatta la data di oggi
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ITALIAN);
        String formattedDate = today.format(formatter);

        // Imposta il testo nella TextView
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        dateTextView.setText(formattedDate);

        // Trova gli ImageView dal layout
        ImageView happyImageView = view.findViewById(R.id.happy);
        ImageView neutralImageView = view.findViewById(R.id.neutral);
        ImageView sadImageView = view.findViewById(R.id.sad);

        // Aggiungi i listener per il click
        happyImageView.setOnClickListener(v -> {
            // IMPLEMENTARE APERTURA SCRITTURA
            Snackbar.make(view, "Hai cliccato su Happy", Snackbar.LENGTH_SHORT).show();
        });

        neutralImageView.setOnClickListener(v -> {
            // IMPLEMENTARE APERTURA SCRITTURA
            Snackbar.make(view, "Hai cliccato su Neutral", Snackbar.LENGTH_SHORT).show();
        });

        sadImageView.setOnClickListener(v -> {
            // IMPLEMENTARE APERTURA SCRITTURA
            Snackbar.make(view, "Hai cliccato su Sad", Snackbar.LENGTH_SHORT).show();
        });

        // Trova il RelativeLayout "nextup"
        RelativeLayout nextUpLayout = view.findViewById(R.id.nextup);

        // Aggiungi il listener per il click al RelativeLayout
        nextUpLayout.setOnClickListener(v -> {
            //IMPLEMENTARE APERTURA NUOVA ACTIVITY
            Snackbar.make(view, "Hai cliccato su Next Up", Snackbar.LENGTH_SHORT).show();
        });

        return view;
    }
}