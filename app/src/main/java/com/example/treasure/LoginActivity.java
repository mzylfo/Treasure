package com.example.treasure;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import org.apache.commons.validator.routines.EmailValidator;


public class LoginActivity extends AppCompatActivity {

    public static final String TAG = LoginActivity.class.getName();

    private TextInputEditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.textInputEmail);
        editTextPassword = findViewById(R.id.textInputPassword);

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> {
            if (isEmailOk(editTextEmail.getText().toString()) && isPasswordOk(editTextPassword.getText().toString())) {
                Log.d(TAG, "Launch new activity");
            } else {
                Log.d(TAG, "Error");
            }
        });

    }

    private boolean isEmailOk(String email) {
        return EmailValidator.getInstance().isValid(email); //utilizzo la libreria di controllo
    }

    private boolean isPasswordOk(String password) {
        return password.length() > 7;
    }
}