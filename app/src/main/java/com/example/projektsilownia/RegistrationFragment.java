package com.example.projektsilownia;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.projektsilownia.usermodel.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegistrationFragment extends Fragment implements View.OnClickListener {

    private Button arrowBack_button, register_button;
    private TextInputEditText login_editText, email_editText, password_editText;

    private FirebaseAuth mAuth;


    public RegistrationFragment() {
        // Required empty public constructor
    }


    public static RegistrationFragment newInstance(String param1, String param2) {
        RegistrationFragment fragment = new RegistrationFragment();
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_registration, container, false);
        mAuth = FirebaseAuth.getInstance();

        arrowBack_button = (Button) rootView.findViewById(R.id.arrowBack_button);
        arrowBack_button.setOnClickListener(this);
        register_button = (Button) rootView.findViewById(R.id.register_button);
        register_button.setOnClickListener(this);

        login_editText = (TextInputEditText) rootView.findViewById(R.id.login_editText);
        email_editText = (TextInputEditText) rootView.findViewById(R.id.email_editText);
        password_editText = (TextInputEditText) rootView.findViewById(R.id.password_editText);

        return rootView;

    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.arrowBack_button:
                fragment = new WelcomeFragment();
                loadFragment(fragment);
                break;
            case R.id.register_button:
                regUser();

                break;
        }
    }

    private void regUser() {
        String login = login_editText.getText().toString().trim();
        String email = email_editText.getText().toString().trim();
        String password = password_editText.getText().toString().trim();

        FirebaseDatabase.getInstance().getReference("Users")
                .child(login).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(getContext(), "login zajety", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "ładowanie", Toast.LENGTH_SHORT).show();

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Map<String, Object> uidd = new HashMap<String, Object>();
                                uidd.put("uid", uid);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(login)
                                        .setValue(uidd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            UserModel userModel = new UserModel(login, email);
                                            FirebaseDatabase.getInstance().getReference("Users")
                                                    .child(login).child("Settings")
                                                    .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Fragment fragment = null;
                                                        fragment = new LoginFragment();
                                                        loadFragment(fragment);
                                                    } else {
                                                        Toast.makeText(getContext(), "błąd 1", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getContext(), "błąd 2", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "błąd połaczenia", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "błąd bazy danych", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentMain, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}