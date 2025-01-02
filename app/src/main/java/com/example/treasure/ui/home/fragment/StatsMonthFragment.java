package com.example.treasure.ui.home.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewGroup;

import com.example.treasure.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsMonthFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsMonthFragment extends Fragment {

    //Codice completo gestione statistiche tramite intent solo che c'è un errore porcodue
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats_month, container, false);

        int counter0 = getArguments().getInt("counter0", 0);
        int counter1 = getArguments().getInt("counter1", 0);
        int counter2 = getArguments().getInt("counter2", 0);
        int totalClicks = counter0 + counter1 + counter2;

        double percentage0 = totalClicks > 0 ? (counter0 * 100.0 / totalClicks) : 0;
        double percentage1 = totalClicks > 0 ? (counter1 * 100.0 / totalClicks) : 0;
        double percentage2 = totalClicks > 0 ? (counter2 * 100.0 / totalClicks) : 0;

        TextView textView0 = view.findViewById(R.id.happinessTextView);
        TextView textView1 = view.findViewById(R.id.neutralityTextView);
        TextView textView2 = view.findViewById(R.id.sadnessTextView);

        textView0.setText(getString(R.string.monthly_happiness_str, percentage0));
        textView1.setText(getString(R.string.monthly_neutrality_str, percentage1));
        textView2.setText(getString(R.string.monthly_sadness_str, percentage2));

        return view;
    }

    //Codice da capire se serve (?)

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatsMonthFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsMonthFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsMonthFragment newInstance(String param1, String param2) {
        StatsMonthFragment fragment = new StatsMonthFragment();
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

}