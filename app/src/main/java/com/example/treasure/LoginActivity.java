package com.example.treasure;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
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
            if (isEmailOk(editTextEmail.getText().toString())){
                if(isPasswordOk(editTextPassword.getText().toString())){
                    // INTENT ESPLICITA
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);

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
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Check your password", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
            else{
                editTextEmail.setError("Check your email");
                Snackbar.make(findViewById(android.R.id.content), "Insert correct email", Snackbar.LENGTH_SHORT)
                        .show();
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