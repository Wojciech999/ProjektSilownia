package com.example.projektsilownia.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projektsilownia.R;


public class SettingsFragment extends Fragment implements View.OnClickListener {

    private AppCompatImageButton arrowBack_button;
    private LinearLayout btn_changeLoginName, btn_changePasswordName,
            btn_changeParameters, btn_aboutApp, btn_warning;

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

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        btn_changeLoginName = (LinearLayout) rootView.findViewById(R.id.btn_changeLoginEmail);
        btn_changeLoginName.setOnClickListener(this);

        btn_changePasswordName = (LinearLayout) rootView.findViewById(R.id.btn_changePasswordName);
        btn_changePasswordName.setOnClickListener(this);

        btn_changeParameters = (LinearLayout) rootView.findViewById(R.id.btn_changeParameters);
        btn_changeParameters.setOnClickListener(this);

        btn_aboutApp = (LinearLayout) rootView.findViewById(R.id.btn_aboutApp);
        btn_aboutApp.setOnClickListener(this);

        btn_warning = (LinearLayout) rootView.findViewById(R.id.btn_warning);
        btn_warning.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.arrowBackBtn:
                if (getFragmentManager().getBackStackEntryCount() > 0 ) {
                    getFragmentManager().popBackStack();
                }
                break;

            case R.id.btn_changeLoginEmail:
                fragment = new ChangeLoginFragment();
                loadFragment(fragment);
                break;

            case R.id.btn_changePasswordName:
                fragment = new ChangePasswordFragment();
                loadFragment(fragment);
                break;

            case R.id.btn_changeParameters:
                fragment = new ChangeParametersFragment();
                loadFragment(fragment);
                break;

            case R.id.btn_aboutApp:
                fragment = new AboutAppFragment();
                loadFragment(fragment);
                break;

            case R.id.btn_warning:
                fragment = new AvailabilityConditionsFragment();
                loadFragment(fragment);
                break;
            default:
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