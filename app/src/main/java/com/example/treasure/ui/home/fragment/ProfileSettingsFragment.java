package com.example.treasure.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.treasure.R;
import com.example.treasure.repository.user.IUserRepository;
import com.example.treasure.ui.welcome.WelcomeActivity;
import com.example.treasure.ui.welcome.viewmodel.UserViewModel;
import com.example.treasure.ui.welcome.viewmodel.UserViewModelFactory;
import com.example.treasure.util.ServiceLocator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileSettingsFragment extends Fragment {
    private UserViewModel userViewModel;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        TextView userEmailTextView = view.findViewById(R.id.user_email);
        TextView userEmail2TextView = view.findViewById(R.id.user_email_2);
        TextView userNameTextView = view.findViewById(R.id.user_name);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            String name = "";
            if (!(email == null)) {
                name = email.substring(0, email.indexOf('@'));
            }
            userEmailTextView.setText("Hi " + name);
            userEmail2TextView.setText(email);
            userNameTextView.setText(name);
        }

        Button logoutButton = view.findViewById(R.id.log_out);
        logoutButton.setOnClickListener(v -> {
            // Log out the user
            mAuth.signOut();
            //userViewModel.setUser(null);

            // Navigate to WelcomeActivity
            goToNextPage();
        });

        return view;
    }

    private void goToNextPage() {
        Intent intent = new Intent(getActivity(), WelcomeActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}