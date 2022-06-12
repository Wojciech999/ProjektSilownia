package com.example.projektsilownia.advancetraining;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projektsilownia.R;
import com.example.projektsilownia.advancetraining.firstStep.FirstStepAdvanceFragment;
import com.example.projektsilownia.mainMenu.HomeFragment;
import com.google.android.material.card.MaterialCardView;




public class MenuAdvanceTreningFragment extends Fragment implements View.OnClickListener {

    private MaterialCardView advancEnduranceExercisesBtn, advancRelaxationExercisesBtn, advancStrengthExercisesBtn;
    private AppCompatImageButton arrowBack_button;
    private String firstStepadvance;
    private Fragment fragment = null;

    public MenuAdvanceTreningFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_menu_advance_trening, container, false);

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        advancEnduranceExercisesBtn = rootView.findViewById(R.id.advancEnduranceExercisesBtn);
        advancEnduranceExercisesBtn.setOnClickListener(this);

        advancRelaxationExercisesBtn = rootView.findViewById(R.id.advancRelaxationExercisesBtn);
        advancRelaxationExercisesBtn.setOnClickListener(this);

        advancStrengthExercisesBtn = rootView.findViewById(R.id.advancStrengthExercisesBtn);
        advancStrengthExercisesBtn.setOnClickListener(this);

        return rootView;
    }

    public String getFirstStepadvance() {
        return firstStepadvance;
    }

    public void setFirstStepadvance(String firstStepadvance) {
        this.firstStepadvance = firstStepadvance;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.advancEnduranceExercisesBtn:
                setFirstStepadvance("EnduranceExercises");
                fragment = new FirstStepAdvanceFragment(getFirstStepadvance());
                loadFragment(fragment);
                break;
            case R.id.advancRelaxationExercisesBtn:
                setFirstStepadvance("RelaxationExercises");
                fragment = new FirstStepAdvanceFragment(getFirstStepadvance());
                loadFragment(fragment);
                break;
            case R.id.advancStrengthExercisesBtn:
                setFirstStepadvance("StrengthExercises");
                fragment = new FirstStepAdvanceFragment(getFirstStepadvance());
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
                android.R.anim.slide_out_right, android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragmentAdvance, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}