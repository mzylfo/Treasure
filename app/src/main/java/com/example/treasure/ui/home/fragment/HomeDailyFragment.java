package com.example.treasure.ui.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasure.R;
import com.example.treasure.adapter.EventRecyclerAdapter;
import com.example.treasure.database.EventDAO;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.model.EventApiResponse;
import com.example.treasure.util.Constants;
import com.example.treasure.util.DateParser;
import com.example.treasure.model.TimeZoneResponse;
import com.example.treasure.util.JSONParserUtils;
import com.example.treasure.util.TimeParser;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeDailyFragment extends Fragment {
    public static final String TAG = HomeDailyFragment.class.getName();
    private View nextUpView;
    private TextView dateTextView;
    private ImageView happyImageView, neutralImageView, sadImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_daily, container, false);

        JSONParserUtils jsonParserUtils = new JSONParserUtils(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewEventNextUp);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        happyImageView = view.findViewById(R.id.happy);
        neutralImageView = view.findViewById(R.id.neutral);
        sadImageView = view.findViewById(R.id.sad);

        // Trova la view "nextup"
        nextUpView = view.findViewById(R.id.nextup);

        // Trova il TextView che contiene la data
        dateTextView = view.findViewById(R.id.dateTextView);

        //settiamo il text della data in modo formato
        try {
            TimeZoneResponse responseDate = jsonParserUtils.parseJSONFileWithGSon(Constants.SAMPLE_JSON_FILENAME);
            String formattedDateWithNewFormat = DateParser.formatDate(responseDate.getFormatted());
            dateTextView.setText(formattedDateWithNewFormat);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Event> eventList = EventRoomDatabase.getDatabase(view.getContext()).eventDAO().getAll();

        EventRecyclerAdapter adapter = new EventRecyclerAdapter(R.layout.card_event, eventList);

        recyclerView.setAdapter(adapter);

        // Aggiungi un listener di click alla view "nextup"
        nextUpView.setOnClickListener(v -> {
            // Ottieni la data dal TextView
            String date = dateTextView.getText().toString();

            // Mostra il DailyPageFragment con la data ottenuta
            DailyPageFragment dialogFragment = DailyPageFragment.newInstance(date);
            dialogFragment.show(getParentFragmentManager(), "DailyPageFragment");
        });

        view.findViewById(R.id.happy).setOnClickListener(v -> {
            NewFeelingFragment newFeelingFragment = NewFeelingFragment.newInstance();
            newFeelingFragment.show(getParentFragmentManager(), newFeelingFragment.getTag());
        });

        view.findViewById(R.id.neutral).setOnClickListener(v -> {
            NewFeelingFragment newFeelingFragment = NewFeelingFragment.newInstance();
            newFeelingFragment.show(getParentFragmentManager(), newFeelingFragment.getTag());
        });

        view.findViewById(R.id.sad).setOnClickListener(v -> {
            NewFeelingFragment newFeelingFragment = NewFeelingFragment.newInstance();
            newFeelingFragment.show(getParentFragmentManager(), newFeelingFragment.getTag());
        });

        return view;
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
}
