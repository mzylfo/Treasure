package com.example.treasure.ui.home;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.treasure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Objects;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Disabilita il titolo predefinito della Toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView toolbarTitle = findViewById(R.id.toolbar_title);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().
                findFragmentById(R.id.fragmentContainerView);

        NavController navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dailyHomeFragment, R.id.calendarMonthFragment, R.id.statsMonthFragment, R.id.profileSettingsFragment
        ).build();

        NavigationUI.setupWithNavController(bottomNav, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Osserva i cambiamenti di destinazione e aggiorna il titolo della toolbar
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getLabel() != null) {
                toolbarTitle.setText(destination.getLabel());
            }

            // Cambia lo sfondo della toolbar in base al fragment corrente
            if (destination.getId() == R.id.profileSettingsFragment || destination.getId() == R.id.dailyHomeFragment) {
                toolbar.setBackgroundResource(R.color.yellow_app); // Colore senza bordi arrotondati
            } else {
                toolbar.setBackgroundResource(R.drawable.rounded_toolbar_corners); // Drawable con bordi arrotondati
            }
        });
    }


}