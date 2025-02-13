package com.example.treasure.ui.welcome.fragment;

import static com.example.treasure.ui.home.fragment.HomeDailyFragment.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.treasure.R;
import com.example.treasure.repository.user.IUserRepository;
import com.example.treasure.ui.home.HomeActivity;
import com.example.treasure.ui.welcome.viewmodel.UserViewModel;
import com.example.treasure.ui.welcome.viewmodel.UserViewModelFactory;
import com.example.treasure.util.ServiceLocator;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import org.apache.commons.validator.routines.EmailValidator;

public class LoginFragment extends Fragment {
    private TextInputEditText editTextEmail, editTextPassword;
    private SignInClient oneTapClient;
    private FirebaseAuth mAuth;
    private BeginSignInRequest signInRequest;
    private ActivityResultLauncher<IntentSenderRequest> activityResultLauncher;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(
                requireActivity(),
                new UserViewModelFactory(userRepository)).get(UserViewModel.class);

        oneTapClient = Identity.getSignInClient(requireActivity());
        signInRequest = BeginSignInRequest.builder()
                .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
                        .setSupported(true)
                        .build())
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .setAutoSelectEnabled(true)
                .build();

        mAuth = FirebaseAuth.getInstance();

        /*
        // Check if user is already signed in
        if (mAuth.getCurrentUser() != null) {
            goToNextPage();
        }
         */

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(), activityResult -> {
                    if (activityResult.getResultCode() == Activity.RESULT_OK) {
                        try {
                            SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(activityResult.getData());
                            String idToken = credential.getGoogleIdToken();

                            if (idToken != null) {
                                firebaseAuthWithGoogle(idToken);
                            } else {
                                Log.e(TAG, "ID Token is null");
                            }
                        } catch (ApiException e) {
                            Log.e(TAG, "Google Sign-In failed", e);
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.textInputEmail);
        editTextPassword = view.findViewById(R.id.textInputPassword);
        Button loginButton = view.findViewById(R.id.loginButton);
        Button signUpButton = view.findViewById(R.id.outlinedButton);
        Button loginGoogleButton = view.findViewById(R.id.logInGoogle);

        loginButton.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();
            if (isEmailOk(email) && isPasswordOk(password)) {
                loginUser(email, password);
            } else {
                Snackbar.make(view, "Email o password non valide", Snackbar.LENGTH_SHORT).show();
            }
        });

        loginGoogleButton.setOnClickListener(v -> oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity(), result -> {
                    IntentSenderRequest intentSenderRequest =
                            new IntentSenderRequest.Builder(result.getPendingIntent()).build();
                    activityResultLauncher.launch(intentSenderRequest);
                })
                .addOnFailureListener(requireActivity(), e -> Log.e(TAG, "Google Sign-In failed", e)));

        signUpButton.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment));
    }

    private boolean isEmailOk(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isPasswordOk(String password) {
        return password.length() > 7;
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        retrieveUserInformation(userViewModel.getLoggedUser().getIdToken());
                        goToNextPage();
                    } else {
                        Log.e(TAG, "Login failed", task.getException());
                    }
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "Google login successful: " + (user != null ? user.getEmail() : "null"));
                        retrieveUserInformation(userViewModel.getLoggedUser().getIdToken());
                        goToNextPage();
                    } else {
                        Log.e(TAG, "Firebase authentication with Google failed", task.getException());
                    }
                });
    }

    private void goToNextPage() {
        startActivity(new Intent(getContext(), HomeActivity.class));
        requireActivity().finish();
    }

    private void retrieveUserInformation(String user) {
        userViewModel.getUserEvents(user);
        userViewModel.getUserFeelings(user);
    }
}