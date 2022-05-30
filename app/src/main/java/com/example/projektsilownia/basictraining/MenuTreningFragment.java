package com.example.projektsilownia.basictraining;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projektsilownia.R;
import com.example.projektsilownia.basictraining.firstStep.FirstStepFragment;
import com.example.projektsilownia.mainMenu.HomeFragment;


public class MenuTreningFragment extends Fragment implements View.OnClickListener {

    private Button enduranceExercisesBtn, relaxationExercisesBtn, strengthExercisesBtn;
    private AppCompatImageButton arrowBack_button;
    private String firstStepbasic;
    private Fragment fragment = null;

    public MenuTreningFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_menu_trening, container, false);

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        enduranceExercisesBtn = rootView.findViewById(R.id.enduranceExercisesBtn);
        enduranceExercisesBtn.setOnClickListener(this);

        relaxationExercisesBtn = rootView.findViewById(R.id.relaxationExercisesBtn);
        relaxationExercisesBtn.setOnClickListener(this);

        strengthExercisesBtn = rootView.findViewById(R.id.strengthExercisesBtn);
        strengthExercisesBtn.setOnClickListener(this);

        return rootView;
    }

    public String getFirstStepbasic() {
        return firstStepbasic;
    }

    public void setFirstStepbasic(String firstStepbasic) {
        this.firstStepbasic = firstStepbasic;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enduranceExercisesBtn:
                setFirstStepbasic("EnduranceExercises");
                fragment = new FirstStepFragment(getFirstStepbasic());
                loadFragment(fragment);
                break;

            case R.id.relaxationExercisesBtn:
                setFirstStepbasic("RelaxationExercises");
                fragment = new FirstStepFragment(getFirstStepbasic());
                loadFragment(fragment);
                break;

            case R.id.strengthExercisesBtn:
                setFirstStepbasic("StrengthExercises");
                fragment = new FirstStepFragment(getFirstStepbasic());
                loadFragment(fragment);
                break;
            case R.id.arrowBackBtn:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    fragment = new HomeFragment();
                    loadFragment(fragment);
                }
                break;
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right,android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentBasic, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}