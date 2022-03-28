package com.example.projektsilownia.settings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projektsilownia.R;


public class AvailabilityConditionsFragment extends Fragment implements View.OnClickListener {

    private Button arrowBack_button;


    public AvailabilityConditionsFragment() {
        // Required empty public constructor
    }

    public static AvailabilityConditionsFragment newInstance(String param1, String param2) {
        AvailabilityConditionsFragment fragment = new AvailabilityConditionsFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_availability_conditions, container, false);

        arrowBack_button = (Button) rootView.findViewById(R.id.arrowBack_button);
        arrowBack_button.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.arrowBack_button:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                break;
        }

    }
}