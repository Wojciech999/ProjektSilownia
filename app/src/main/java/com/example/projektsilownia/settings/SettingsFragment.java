package com.example.projektsilownia.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projektsilownia.R;
import com.example.projektsilownia.mainMenu.HomeFragment;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Button arrowBack_button;
    private Fragment fragment = null;

    public SettingsFragment() {
        // Required empty public constructor
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
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        arrowBack_button = (Button) rootView.findViewById(R.id.arrowBack_button);
        arrowBack_button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.arrowBack_button:
                if (getFragmentManager().getBackStackEntryCount() > 0 ) {
                    getFragmentManager().popBackStack();
                }
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentMainMenu, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }

}