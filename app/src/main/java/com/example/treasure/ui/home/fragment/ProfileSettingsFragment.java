package com.example.treasure.ui.home.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.treasure.R;
import com.example.treasure.model.Result;
import com.example.treasure.model.User;
import com.example.treasure.repository.user.IUserRepository;
import com.example.treasure.ui.home.HomeActivity;
import com.example.treasure.ui.welcome.WelcomeActivity;
import com.example.treasure.ui.welcome.fragment.LoginFragment;
import com.example.treasure.ui.welcome.viewmodel.UserViewModel;


import com.example.treasure.ui.welcome.viewmodel.UserViewModelFactory;
import com.example.treasure.util.ServiceLocator;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileSettingsFragment extends Fragment {
    private UserViewModel userViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileSettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileSettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileSettingsFragment newInstance(String param1, String param2) {
        ProfileSettingsFragment fragment = new ProfileSettingsFragment();
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

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());

        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_settings, container, false);

        /*
        Button changePasswordButton = view.findViewById(R.id.change_password);

        changePasswordButton.setOnClickListener(v -> {
            // IMPLEMENTARE APERTURA CAMBIO PASSWORD
            Snackbar.make(view, "Hai cliccato su Change Password", Snackbar.LENGTH_SHORT).show();
        });
        */

        view.findViewById(R.id.log_out).setOnClickListener(v -> {
            userViewModel.logout();
            goToNextPage(view);
        });

        return view;
    }

    private void goToNextPage(View view) {
        startActivity(new Intent(getContext(), LoginFragment.class));
    }
}