package com.example.treasure.ui.home.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.treasure.R;
import com.example.treasure.util.Constants;
import com.example.treasure.util.DateParser;
import com.example.treasure.model.TimeZoneResponse;
import com.example.treasure.util.JSONParserUtils;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/*import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query; */

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;

public class HomeDailyFragment extends Fragment {
    public static final String TAG = HomeDailyFragment.class.getName();
    private View nextUpView;
    private TextView dateTextView;

    //Codice che serve al contatore mensile di click umore
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_COUNTER_0 = "counter_happy";
    private static final String KEY_COUNTER_1 = "counter_neutral";
    private static final String KEY_COUNTER_2 = "counter_sad";
    private static final String KEY_LAST_SAVED_MONTH = "last_saved_month";
    private SharedPreferences sharedPreferences;
    private int counter0, counter1, counter2;
    //fine di field declaration

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_daily, container, false);

        JSONParserUtils jsonParserUtils = new JSONParserUtils(getContext());

        // Trova la view "nextup"
        nextUpView = view.findViewById(R.id.nextup);

        // Trova il TextView che contiene la data
        dateTextView = view.findViewById(R.id.dateTextView);

        //settiamo il text della data in modo formato
        try {
            TimeZoneResponse response = jsonParserUtils.parseJSONFileWithGSon(Constants.SAMPLE_JSON_FILENAME);
            String formattedDate = response.getFormatted();
            String formattedDateWithNewFormat = DateParser.formatDate(formattedDate);
            dateTextView.setText(formattedDateWithNewFormat);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Aggiungi un listener di click alla view "nextup"
        nextUpView.setOnClickListener(v -> {
            // Ottieni la data dal TextView
            String date = dateTextView.getText().toString();

            // Mostra il DailyPageFragment con la data ottenuta
            DailyPageFragment dialogFragment = DailyPageFragment.newInstance(date);
            dialogFragment.show(getParentFragmentManager(), "DailyPageFragment");
        });

        //Inizio del codice per contatore mensile click umore

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        counter0 = sharedPreferences.getInt(KEY_COUNTER_0, 0);
        counter1 = sharedPreferences.getInt(KEY_COUNTER_1, 0);
        counter2 = sharedPreferences.getInt(KEY_COUNTER_2, 0);

        int lastSavedMonth = sharedPreferences.getInt(KEY_LAST_SAVED_MONTH, -1);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        if (currentMonth != lastSavedMonth) { //blocco che resetta ogni mese il contatore
            counter0 = 0;
            counter1 = 0;
            counter2 = 0;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(KEY_COUNTER_0, counter0);
            editor.putInt(KEY_COUNTER_1, counter1);
            editor.putInt(KEY_COUNTER_2, counter2);

            editor.putInt(KEY_LAST_SAVED_MONTH, currentMonth);
            editor.apply();
        }

        ImageView imageView0 = view.findViewById(R.id.happy);
        ImageView imageView1 = view.findViewById(R.id.neutral);
        ImageView imageView2 = view.findViewById(R.id.sad);

        //implementazione dei singoli contatori

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter1++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_COUNTER_1, counter1);
                editor.apply();
                Toast.makeText(requireActivity(), "ImageView 1 cliccata: " + counter1 + " volte", Toast.LENGTH_SHORT).show();
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter2++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_COUNTER_2, counter2);
                editor.apply();
                Toast.makeText(requireActivity(), "ImageView 2 cliccata: " + counter2 + " volte", Toast.LENGTH_SHORT).show();
            }
        });

        imageView0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter0++;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_COUNTER_0, counter0);
                editor.apply();
                Toast.makeText(requireActivity(), "ImageView 3 cliccata: " + counter0 + " volte", Toast.LENGTH_SHORT).show();
            }
        });

        //fine del codice relativo ai contatori mensili, la gestione delle ambiguità è lasciata alla pagina delle stats

        //TODO i messaggi lasciati dai contatori sono lì solo per controllarne il corretto funzionamento in fase di sviluppo

        return view;

    }

    //TODO: Codice per la condivisione con la schermata delle statistiche via intent
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu); // Usa il nome corretto del file di menu
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.statsMonthFragment) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt("counter0", counter0);
                bundle.putInt("counter1", counter1);
                bundle.putInt("counter2", counter2);

                StatsMonthFragment statsMonthFragment = new StatsMonthFragment();
                statsMonthFragment.setArguments(bundle);

                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, statsMonthFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            } catch (Exception e) {
                Log.e("HomeDailyFragment", "Error opening StatsMonthFragment", e);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}


    /*private void getCurrentTime() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.timezonedb.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<TimeZoneResponse> call = apiService.getTimeZone("EFI0PITZ4VQC", "json", "zone", "Europe/Rome");
        call.enqueue(new Callback<TimeZoneResponse>() {
            @Override
            public void onResponse(@NonNull Call<TimeZoneResponse> call, @NonNull Response<TimeZoneResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String formattedDate = response.body().getFormatted();
                    String formattedDateWithNewFormat = DateParser.formatDate(formattedDate);
                    dateTextView.setText(formattedDateWithNewFormat);
                } else {
                    dateTextView.setText("Errore nella risposta");
                    Log.e("HomeDailyFragment", "Response error: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<TimeZoneResponse> call, @NonNull Throwable t) {
                dateTextView.setText("Errore di rete: " + t.getMessage());
                Log.e("HomeDailyFragment", "Network error", t);
            }
        });
    }

    public interface ApiService {
        @GET("v2.1/get-time-zone")
        Call<TimeZoneResponse> getTimeZone(
                @Query("key") String key,
                @Query("format") String format,
                @Query("by") String by,
                @Query("zone") String zone
        );
    }
    */

