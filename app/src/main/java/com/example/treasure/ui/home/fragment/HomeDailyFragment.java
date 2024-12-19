package com.example.treasure.ui.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.treasure.R;
import com.example.treasure.service.TimeApiService;
import com.example.treasure.model.TimeApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeDailyFragment extends Fragment {

    private TextView dateTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_daily, container, false);
        dateTextView = view.findViewById(R.id.dateTextView);
        getCurrentTime();
        return view;
    }

    private void getCurrentTime() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://worldtimeapi.org/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TimeApiService apiService = retrofit.create(TimeApiService.class);
        Call<TimeApiResponse> call = apiService.getCurrentTime();
        call.enqueue(new Callback<TimeApiResponse>() {
            @Override
            public void onResponse(Call<TimeApiResponse> call, Response<TimeApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String currentTime = response.body().getDatetime();
                    dateTextView.setText(currentTime);
                } else {
                    Toast.makeText(getActivity(), "Errore nel recupero della data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TimeApiResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Errore di rete", Toast.LENGTH_SHORT).show();
            }
        });
    }
}