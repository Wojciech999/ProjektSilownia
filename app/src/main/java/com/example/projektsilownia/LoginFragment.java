package com.example.projektsilownia;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.projektsilownia.custom.CustomProgressBar;
import com.example.projektsilownia.custom.CustomToast;
import com.example.projektsilownia.mainMenu.MainMenuActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private AppCompatImageButton arrowBack_button;
    private Button login_button;
    private TextInputEditText email_editText, password_editText;
    private FirebaseAuth mAuth;
    CustomToast customToast = new CustomToast();

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        login_button = (Button) rootView.findViewById(R.id.btnChangePassword);
        login_button.setOnClickListener(this);

        email_editText = (TextInputEditText) rootView.findViewById(R.id.email_editText);
        password_editText = (TextInputEditText) rootView.findViewById(R.id.editTextNewPassword);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.arrowBackBtn:
                fragment = new WelcomeFragment();
                loadFragment(fragment);
                break;
            case R.id.btnChangePassword:
                loginInApp();
            break;
        }
    }

    private void loginInApp() {

        String email = email_editText.getText().toString().trim();
        String password = password_editText.getText().toString().trim();

        if(email.isEmpty() && password.isEmpty()){
            Toast.makeText(getContext(), "Pola nie mogą być puste", Toast.LENGTH_LONG).show();
        }else {
            CustomProgressBar customProgressBar = new CustomProgressBar(getActivity(), "Trwa logowanie");
            customProgressBar.startProgressBarDialog();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        customProgressBar.dismissDialog();

                        Intent intent2 = new Intent(getContext().getApplicationContext(), MainMenuActivity.class);
                        startActivity(intent2);

                        customToast.showToast(getActivity(), "Pomyslnie zalogowano", R.drawable.ic_outline_done_24);

                    } else {
                        customProgressBar.dismissDialog();

                        customToast.showToast(getActivity(), "Logowanie nie powiodło się", R.drawable.ic_baseline_error_outline_24);
                    }
                }
            });
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentMain, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}