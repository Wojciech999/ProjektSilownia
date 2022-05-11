package com.example.projektsilownia.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.projektsilownia.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ChangePasswordFragment extends Fragment implements View.OnClickListener {

    private AppCompatImageButton arrowBack_button;
    private Button btnChangePassword;
    private EditText editTextOldPassword, editTextNewPassword, editTextRepeatPassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static ChangePasswordFragment newInstance(String param1, String param2) {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        btnChangePassword = (Button) rootView.findViewById(R.id.btnChangePassword);
        btnChangePassword.setOnClickListener(this);

        editTextOldPassword = (EditText) rootView.findViewById(R.id.editTextOldPassword);
        editTextNewPassword = (EditText) rootView.findViewById(R.id.editTextNewPassword);
        editTextRepeatPassword = (EditText) rootView.findViewById(R.id.editTextRepeatPassword);

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
            case R.id.btnChangePassword:
                changePasswordMethod();
                break;
        }
    }

    public void changePasswordMethod() {
        String oldPassword = editTextOldPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();
        String repeatPassword = editTextRepeatPassword.getText().toString();

        if (newPassword.equals(repeatPassword)) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), oldPassword);

            user.reauthenticate(credential)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(getContext(), "Password updated", Toast.LENGTH_SHORT).show();

                                        } else {

                                            Toast.makeText(getContext(), "Error password not updated", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });
                            } else {

                                Toast.makeText(getContext(), "Error auth failed", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        } else {
            Toast.makeText(getContext(), "Hasła się nie zgadzają", Toast.LENGTH_SHORT).show();
        }
    }
}