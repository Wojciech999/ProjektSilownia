package com.example.projektsilownia.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.projektsilownia.R;
import com.example.projektsilownia.custom.CustomProgressBar;
import com.example.projektsilownia.custom.CustomToast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangeLoginFragment extends Fragment implements View.OnClickListener {

    private AppCompatImageButton arrowBack_button;
    private TextInputEditText userName_editText, repeateUserName_editText, password_editText;
    private Button change_button;
    private String username, repeatUsername, password;
    private FirebaseAuth mAuth;
    CustomToast customToast = new CustomToast();

    public ChangeLoginFragment() {
        // Required empty public constructor
    }


    public static ChangeLoginFragment newInstance(String param1, String param2) {
        ChangeLoginFragment fragment = new ChangeLoginFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_change_login, container, false);

        mAuth = FirebaseAuth.getInstance();

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        userName_editText = (TextInputEditText) rootView.findViewById(R.id.UserName_editText);
        repeateUserName_editText = (TextInputEditText) rootView.findViewById(R.id.RepeateUserName_editText);
        password_editText = (TextInputEditText) rootView.findViewById(R.id.Password_editText);

        change_button = (Button) rootView.findViewById(R.id.change_button);
        change_button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.arrowBackBtn:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.change_button:
                changeUserName();
                break;
        }
    }

    public void changeUserName() {
        CustomProgressBar customProgressBar = new CustomProgressBar(getActivity(), "Trwa logowanie");
        customProgressBar.startProgressBarDialog();

        username = userName_editText.getText().toString().trim();
        repeatUsername = repeateUserName_editText.getText().toString().trim();
        password = password_editText.getText().toString().trim();

        if (username.isEmpty() || repeatUsername.isEmpty() || password.isEmpty()) {
            customToast.showToast(getActivity(), "Wszystkie pola muszą zostać wypełnione", R.drawable.ic_baseline_error_outline_24);
        } else {
            String email = mAuth.getCurrentUser().getEmail();
            if (!email.equals(username)) {
                customToast.showToast(getActivity(), "Twój email jest inny, poddaj prawidłowy email", R.drawable.ic_baseline_error_outline_24);
            } else {

                mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateEmail(repeatUsername)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                customToast.showToast(getActivity(),
                                                        "Udało się zmienić dane do logowania",
                                                        R.drawable.ic_outline_done_24);
                                                customProgressBar.dismissDialog();
                                            } else {
                                                customToast.showToast(getActivity(),
                                                        "Nie udało się zmienić danych logowania",
                                                        R.drawable.ic_baseline_error_outline_24);
                                                customProgressBar.dismissDialog();
                                            }
                                        }
                                    });
                        } else {
                            customToast.showToast(getActivity(), "Poddane dane są niepoprawne", R.drawable.ic_baseline_error_outline_24);
                            customProgressBar.dismissDialog();
                        }
                    }
                });

            }

        }

    }
}