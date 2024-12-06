package com.example.treasure;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.validator.routines.EmailValidator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class LoginFragment extends Fragment {
    private TextInputEditText editTextEmail, editTextPassword;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.textInputEmail);
        editTextPassword = view.findViewById(R.id.textInputPassword);

        Button loginButton = view.findViewById(R.id.loginButton);
        Button signUpButton = view.findViewById(R.id.outlinedButton);

        loginButton.setOnClickListener(v -> {
            if (isEmailOk(editTextEmail.getText().toString())){
                if(isPasswordOk(editTextPassword.getText().toString())){
                    Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_mainActivity3);
                    // INTENT ESPLICITA
                    /*Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);*/

                    /*INTENT IMPLICITA
                    Intent intent = new Intent();
                    intent.setAction(intent.ACTION_SEND); //action send è l'azione di invio / inoltro verso un'altra applicazione
                    intent.putExtra(intent.EXTRA_TEXT, "hello"); //definiamo il messaggio
                    intent.setType("text/plain");

                    // Try to invoke the intent.
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        // Define what your app should do if no activity can handle the intent.
                    }
                    */
                }
               else {
                Snackbar.make(view, "Check your password", Snackbar.LENGTH_SHORT).show();
            }
        } else {
            editTextEmail.setError("Check your email");
            Snackbar.make(view, "Insert correct email", Snackbar.LENGTH_SHORT).show();
        }
    });

        signUpButton.setOnClickListener(v2 -> {
            Navigation.findNavController(v2).navigate(R.id.action_loginFragment_to_signupFragment);
        });
    }

    private boolean isEmailOk(String email) {
        return EmailValidator.getInstance().isValid(email); //utilizzo la libreria di controllo
    }

    private boolean isPasswordOk(String password) {
        return password.length() > 7;
    }

}