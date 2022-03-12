package com.example.projektsilownia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class WelcomeFragment extends Fragment implements View.OnClickListener {

    private Button login_button, register_button;

    public WelcomeFragment() {
        // Required empty public constructor
    }


    public static WelcomeFragment newInstance(String param1, String param2) {
        WelcomeFragment fragment = new WelcomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_welcome, container, false);

        login_button = (Button) rootView.findViewById(R.id.login_button);
        login_button.setOnClickListener(this);

        register_button = (Button) rootView.findViewById(R.id.register_button);
        register_button.setOnClickListener(this);



        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()){
            case R.id.login_button:
                fragment = new LoginFragment();
                loadFragment(fragment);
                break;

            case R.id.register_button:
                fragment = new RegistrationFragment();
                loadFragment(fragment);
                break;
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