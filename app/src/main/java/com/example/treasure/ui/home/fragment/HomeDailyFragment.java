package com.example.treasure.ui.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.treasure.R;

import java.util.Date;
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
    private View nextUpView;

    private String formatDate(String date) {
        // Definisci il formato della data in ingresso
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Modifica se necessario in base al formato che ricevi dall'API
        // Definisci il formato della data in uscita
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MM yyyy");

        try {
            Date parsedDate = inputFormat.parse(date); // Parsea la data in ingresso
            return outputFormat.format(parsedDate); // Ritorna la data nel formato desiderato
        } catch (Exception e) {
            return date; // Se c'è un errore, ritorna la data originale
        }
    }

    private TextView dateTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_daily, container, false);
        dateTextView = view.findViewById(R.id.dateTextView);
        nextUpView = view.findViewById(R.id.nextup);

        nextUpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDailyPageFragment();
            }
        });

        getCurrentTime();
        return view;
    }

    private void showDailyPageFragment() {
        DialogFragment dailyPageFragment = new DailyPageFragment();
        dailyPageFragment.show(getFragmentManager(), "DailyPageFragment");
    }
    private void getCurrentTime() {
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
                    String formattedDateWithNewFormat = formatDate(formattedDate);
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

    public static class TimeZoneResponse {
        private String status;
        private String message;
        private String countryCode;
        private String countryName;
        private String regionName;
        private String cityName;
        private String zoneName;
        private String abbreviation;
        private int gmtOffset;
        private String dst;
        private long zoneStart;
        private long zoneEnd;
        private String nextAbbreviation;
        private long timestamp;
        private String formatted;

        // Getters e Setters
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }

        public String getCountryCode() { return countryCode; }
        public void setCountryCode(String countryCode) { this.countryCode = countryCode; }

        public String getCountryName() { return countryName; }
        public void setCountryName(String countryName) { this.countryName = countryName; }

        public String getRegionName() { return regionName; }
        public void setRegionName(String regionName) { this.regionName = regionName; }

        public String getCityName() { return cityName; }
        public void setCityName(String cityName) { this.cityName = cityName; }

        public String getZoneName() { return zoneName; }
        public void setZoneName(String zoneName) { this.zoneName = zoneName; }

        public String getAbbreviation() { return abbreviation; }
        public void setAbbreviation(String abbreviation) { this.abbreviation = abbreviation; }

        public int getGmtOffset() { return gmtOffset; }
        public void setGmtOffset(int gmtOffset) { this.gmtOffset = gmtOffset; }

        public String getDst() { return dst; }
        public void setDst(String dst) { this.dst = dst; }

        public long getZoneStart() { return zoneStart; }
        public void setZoneStart(long zoneStart) { this.zoneStart = zoneStart; }

        public long getZoneEnd() { return zoneEnd; }
        public void setZoneEnd(long zoneEnd) { this.zoneEnd = zoneEnd; }

        public String getNextAbbreviation() { return nextAbbreviation; }
        public void setNextAbbreviation(String nextAbbreviation) { this.nextAbbreviation = nextAbbreviation; }

        public long getTimestamp() { return timestamp; }
        public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

        public String getFormatted() { return formatted; }
        public void setFormatted(String formatted) { this.formatted = formatted; }
    }
}
