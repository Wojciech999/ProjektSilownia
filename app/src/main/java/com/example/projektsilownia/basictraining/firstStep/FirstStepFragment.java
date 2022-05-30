package com.example.projektsilownia.basictraining.firstStep;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projektsilownia.R;
import com.example.projektsilownia.basictraining.secondStep.SecondStepFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class FirstStepFragment extends Fragment implements View.OnClickListener {

    private Button fifteenMinBtn, halfHourBtn, oneHourBtn;
    private String firstStepBasic, secondStepBasic;
    private AppCompatImageButton arrowBack_button;
    private Fragment fragment = null;

    public String userOnline;
    private DatabaseReference rootRef;

    public FirstStepFragment(String firstStepBasic) {
        this.firstStepBasic = firstStepBasic;
    }

    public String getSecondStepBasic() {
        return secondStepBasic;
    }

    public void setSecondStepBasic(String secondStepBasic) {
        this.secondStepBasic = secondStepBasic;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public String getUserOnline() {
        return userOnline;
    }

    public void setUserOnline(String userOnline) {
        this.userOnline = userOnline;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_first_step, container, false);

        Log.d("SPRAWDZANIE", firstStepBasic);

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        fifteenMinBtn = (Button) rootView.findViewById(R.id.fifteenMinBtn);
        fifteenMinBtn.setOnClickListener(this);

        halfHourBtn = (Button) rootView.findViewById(R.id.halfHourBtn);
        halfHourBtn.setOnClickListener(this);

        oneHourBtn = (Button) rootView.findViewById(R.id.oneHourBtn);
        oneHourBtn.setOnClickListener(this);

        rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnline = ds.getKey();
                    setUserOnline(ds.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.fifteenMinBtn:
                setSecondStepBasic("Time15Min");
                fragment = new SecondStepFragment(firstStepBasic, getSecondStepBasic(), getUserOnline());
                loadFragment(fragment);
                break;

            case R.id.halfHourBtn:
                setSecondStepBasic("Time30Min");
                fragment = new SecondStepFragment(firstStepBasic, getSecondStepBasic(), getUserOnline());
                loadFragment(fragment);
                break;

            case R.id.oneHourBtn:
                setSecondStepBasic("Time60Min");
                fragment = new SecondStepFragment(firstStepBasic, getSecondStepBasic(), getUserOnline());
                loadFragment(fragment);
                break;

            case R.id.arrowBackBtn:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
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
        fragmentTransaction.replace(R.id.fragmentBasic, fragment).commit();
        fragmentTransaction.addToBackStack(null);
    }
}