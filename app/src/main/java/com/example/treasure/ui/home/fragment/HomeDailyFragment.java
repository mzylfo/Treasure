package com.example.treasure.ui.home.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasure.R;
import com.example.treasure.adapter.EventRecyclerAdapter;
import com.example.treasure.database.EventRoomDatabase;
import com.example.treasure.model.Event;
import com.example.treasure.model.Result;
import com.example.treasure.model.Weather;
import com.example.treasure.util.Constants;
import com.example.treasure.util.NetworkUtil;
import com.example.treasure.ui.home.viewmodel.WeatherViewModelFactory;
import com.example.treasure.repository.weather.WeatherRepository;
import com.example.treasure.ui.home.viewmodel.WeatherViewModel;
import com.example.treasure.util.DateUtils;
import com.example.treasure.util.JSONParserUtils;
import com.example.treasure.util.ServiceLocator;
import com.example.treasure.util.SharedPreferencesUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeDailyFragment extends Fragment {
    public static final String TAG = HomeDailyFragment.class.getName();
    private View nextUpView;
    private TextView dateTextView;
    private ImageView happyImageView, neutralImageView, sadImageView;
    private TextView city, degrees, information;
    private CircularProgressIndicator progressIndicator;
    private Weather weather;
    private String forecast;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private WeatherViewModel weatherViewModel;
    private FrameLayout noInternetView;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        sharedPreferencesUtils = new SharedPreferencesUtils(requireActivity().getApplication());

        WeatherRepository weatherRepository =
                ServiceLocator.getInstance().getWeatherRepository(
                        requireActivity().getApplication(),
                        requireActivity().getApplication().getResources().getBoolean(R.bool.debug_mode)
                );

        weatherViewModel = new ViewModelProvider(
                requireActivity(),
                new WeatherViewModelFactory(weatherRepository)).get(WeatherViewModel.class);

    }

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
        noInternetView = view.findViewById(R.id.noInternetMessage);

        city = view.findViewById(R.id.weather_city);
        degrees = view.findViewById(R.id.weather_degrees);
        information = view.findViewById(R.id.weather_info);

        //definizione dei due bottoni
        FloatingActionButton fabLeft = view.findViewById(R.id.fab_left);
        FloatingActionButton fabRight = view.findViewById(R.id.fab_right);

        progressIndicator = view.findViewById(R.id.progressIndicator);

        String lastUpdate = "0";

        sharedPreferencesUtils = new SharedPreferencesUtils(getContext());
        if (sharedPreferencesUtils.readStringData(
                Constants.SHARED_PREFERENCES_FILENAME, Constants.SHARED_PREFERNECES_LAST_UPDATE) != null) {
            lastUpdate = sharedPreferencesUtils.readStringData(
                    Constants.SHARED_PREFERENCES_FILENAME, Constants.SHARED_PREFERNECES_LAST_UPDATE);
        }


        if (!NetworkUtil.isInternetAvailable(getContext())) {
            noInternetView.setVisibility(View.VISIBLE);

            //Trick to avoid doing the API call
            lastUpdate = System.currentTimeMillis() + "";
        }


        weatherViewModel.getWeather("Milan", "no", Long.parseLong(lastUpdate)).observe(getViewLifecycleOwner(),
                result -> {
                    if (result.isSuccess()) {
                        weather = (((Result.Weather) result).getData());
                        Log.d(TAG, "Weather data: " + weather.toString()); // Aggiungi questo log
                        if (weather != null) {
                            String apiResponse = weather.toString();

                            // Usa espressioni regolari per estrarre i valori
                            Pattern locationPattern = Pattern.compile("location=Location\\{name='([^']*)'\\}");
                            Pattern tempPattern = Pattern.compile("temp_c=([0-9.]+)");
                            Pattern conditionPattern = Pattern.compile("condition=Condition\\{text='([^']*)'\\}");

                            Matcher locationMatcher = locationPattern.matcher(apiResponse);
                            Matcher tempMatcher = tempPattern.matcher(apiResponse);
                            Matcher conditionMatcher = conditionPattern.matcher(apiResponse);

                            String location = "";
                            String temperature = "";
                            String condition = "";

                            if (locationMatcher.find()) {
                                location = locationMatcher.group(1);
                            }
                            if (tempMatcher.find()) {
                                temperature = tempMatcher.group(1);
                            }
                            if (conditionMatcher.find()) {
                                condition = conditionMatcher.group(1);
                            }

                            Log.d(TAG, "Location: " + location);
                            Log.d(TAG, "Temperature: " + temperature);
                            Log.d(TAG, "Condition: " + condition);

                            city.setText(location);
                            degrees.setText(temperature + " °");
                            information.setText(condition);

                            forecast = condition;
                            city.setVisibility(View.VISIBLE);
                            degrees.setVisibility(View.VISIBLE);
                            information.setVisibility(View.VISIBLE);
                            progressIndicator.setVisibility(View.GONE);


                        } else {
                            if (weather == null) {
                                Log.e(TAG, "Weather is null");
                            } else if (weather.getLocation() == null) {
                                Log.e(TAG, "Location is null");
                            }
                        }
                    } else {
                        // Log the error message
                        if (result instanceof Result.Error) {
                            Log.e(TAG, "Error: " + ((Result.Error) result).getMessage());
                        }
                    }
                });


        // Trova la view "nextup"
        nextUpView = view.findViewById(R.id.nextup);

        // Trova il TextView che contiene la data
        dateTextView = view.findViewById(R.id.dateTextView);

        //settiamo il text della data in modo formato
        String currentDate = DateUtils.getCurrentDate();
        dateTextView.setText(currentDate);

        // Ottieni la data dal TextView
        String date = dateTextView.getText().toString();

        // Aggiungi un listener di click alla view "nextup"
        nextUpView.setOnClickListener(v -> {


            // Mostra il DailyPageFragment con la data ottenuta
            DailyPageFragment dialogFragment = DailyPageFragment.newInstance(date);
            dialogFragment.show(getParentFragmentManager(), "DailyPageFragment");
        });

        view.findViewById(R.id.happy).setOnClickListener(v -> {
            NewFeelingFragment newFeelingFragment = NewFeelingFragment.newInstance(1, date, forecast);
            newFeelingFragment.show(getParentFragmentManager(), newFeelingFragment.getTag());
        });

        view.findViewById(R.id.neutral).setOnClickListener(v -> {
            NewFeelingFragment newFeelingFragment = NewFeelingFragment.newInstance(0, date, forecast);
            newFeelingFragment.show(getParentFragmentManager(), newFeelingFragment.getTag());
        });

        view.findViewById(R.id.sad).setOnClickListener(v -> {
            NewFeelingFragment newFeelingFragment = NewFeelingFragment.newInstance(-1, date, forecast);
            newFeelingFragment.show(getParentFragmentManager(), newFeelingFragment.getTag());
        });

        // Listener per fab_left
        fabLeft.setOnClickListener(v -> {
            // Mostra il DailyPageFragment con la data ottenuta
            DailyPageFragment dialogFragment = DailyPageFragment.newInstance(date);
            dialogFragment.show(getParentFragmentManager(), "DailyPageFragment");
        });

        // Listener per fab_right
        fabRight.setOnClickListener(v -> {
                NewEventFragment newEventFragment = NewEventFragment.newInstance(date);
                newEventFragment.show(getParentFragmentManager(), newEventFragment.getTag());
        });


        // DA CAMBIARE!!!!
        requireActivity().getSupportFragmentManager().setFragmentResultListener(
                "event_added", this, (requestKey, result) -> {// Quando un evento viene eliminato, ricarica la lista degli eventi
                    reloadEventList();
                }
        );

        requireActivity().getSupportFragmentManager().setFragmentResultListener(
                "event_deleted", this, (requestKey, result) -> {// Quando un evento viene eliminato, ricarica la lista degli eventi
                    reloadEventList();
                }
        );

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Ora è sicuro chiamare reloadEventList()
        reloadEventList();
    }

    // Metodo per ricaricare la lista degli eventi dopo l'eliminazione
    public void reloadEventList() {
        if (getContext() == null || getView() == null) {
            return;
        }

        new Thread(() -> {
            // Recupera la data attuale dalla TextView
            String date = dateTextView.getText().toString();
            List<Event> updatedEventList = EventRoomDatabase.getDatabase(getContext()).eventDAO().getEventsByDate(date);

            // Filtra solo gli eventi nell'arco di trenta minuti
            List<Event> upcomingEvents = new ArrayList<>();
            Calendar now = Calendar.getInstance();
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String currentTimeString = timeFormat.format(now.getTime());

            for (Event event : updatedEventList) {
                try {
                    Date eventTime = timeFormat.parse(event.getTime());
                    Date currentTime = timeFormat.parse(currentTimeString);
                    long diffMinutes = (eventTime.getTime() - currentTime.getTime()) / (60 * 1000);

                    if (diffMinutes >= -30 && diffMinutes <= 30) {
                        upcomingEvents.add(event);
                    }
                } catch (ParseException e) {
                    Log.e(TAG, "Errore nel parsing del tempo", e);
                }
            }

            // Aggiorna la UI sul thread principale
            getActivity().runOnUiThread(() -> {
                RecyclerView eventsRecyclerView = getView().findViewById(R.id.recyclerViewEventNextUp);
                EventRecyclerAdapter updatedAdapter = new EventRecyclerAdapter(R.layout.card_event, upcomingEvents);
                eventsRecyclerView.setAdapter(updatedAdapter);

                // Mostra o nasconde il messaggio "Nessun evento prossimo"
                TextView noEventsTextView = getView().findViewById(R.id.noEventsTextView);
                if (upcomingEvents.isEmpty()) {
                    noEventsTextView.setVisibility(View.VISIBLE);
                } else {
                    noEventsTextView.setVisibility(View.GONE);
                }
            });
        }).start();
    }
}
