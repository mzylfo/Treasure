package com.example.treasure.ui.welcome.fragment;

import static android.content.ContentValues.TAG;
import static com.example.treasure.util.Constants.USER_COLLISION_ERROR;
import static com.example.treasure.util.Constants.WEAK_PASSWORD_ERROR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.treasure.R;
import com.example.treasure.model.Result;
import com.example.treasure.model.User;
import com.example.treasure.repository.user.IUserRepository;
import com.example.treasure.ui.home.HomeActivity;
import com.example.treasure.ui.home.fragment.HomeDailyFragment;
import com.example.treasure.ui.welcome.viewmodel.UserViewModel;
import com.example.treasure.ui.welcome.viewmodel.UserViewModelFactory;
import com.example.treasure.util.Constants;
import com.example.treasure.util.ServiceLocator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.validator.routines.EmailValidator;

public class SignupFragment extends Fragment {

    private UserViewModel userViewModel;
    private TextInputEditText textInputEmail, textInputPassword;
    private FirebaseAuth mAuth;

    public SignupFragment() {
        // Required empty public constructor
    }

    public static SignupFragment newInstance() {
        return new SignupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        IUserRepository userRepository = ServiceLocator.getInstance().getUserRepository(requireActivity().getApplication());
        userViewModel = new ViewModelProvider(requireActivity(), new UserViewModelFactory(userRepository)).get(UserViewModel.class);
        userViewModel.setAuthenticationError(false);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textInputEmail = view.findViewById(R.id.textInputNewEmail);
        textInputPassword = view.findViewById(R.id.textInputNewPassword);

        view.findViewById(R.id.signUpButton).setOnClickListener(v -> {
            String email = textInputEmail.getText().toString().trim();
            String password = textInputPassword.getText().toString().trim();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                goToNextPage();
                            } else {
                                Log.e(HomeDailyFragment.TAG, "Login failed", task.getException());
                            }
                        }
                    });
        });

        Button backLogin = view.findViewById(R.id.backLoginButton);
        backLogin.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_signupFragment_to_loginFragment));
    }
    private boolean isEmailOk(String email) {
        if (!EmailValidator.getInstance().isValid(email)) {
            textInputEmail.setError(getString(R.string.error_email_login));
            return false;
        } else {
            textInputEmail.setError(null);
            return true;
        }
    }

    private boolean isPasswordOk(String password) {
        if (password.isEmpty() || password.length() < Constants.MINIMUM_LENGTH_PASSWORD) {
            textInputPassword.setError(getString(R.string.error_password_login));
            return false;
        } else {
            textInputPassword.setError(null);
            return true;
        }
    }

    private void goToNextPage() {
        startActivity(new Intent(getContext(), HomeActivity.class));
        requireActivity().finish();
    }

    private String getErrorMessage(String message) {
        switch (message) {
            case WEAK_PASSWORD_ERROR:
                return requireActivity().getString(R.string.error_password_login);
            case USER_COLLISION_ERROR:
                return requireActivity().getString(R.string.error_collision_user);
            default:
                return requireActivity().getString(R.string.error_unexpected);
        }
    }
}