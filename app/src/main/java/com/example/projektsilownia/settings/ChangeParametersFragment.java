package com.example.projektsilownia.settings;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ChangeParametersFragment extends Fragment implements View.OnClickListener {

    private AppCompatImageButton arrowBack_button;
    private DatabaseReference rootRef;
    private Button btn_plus_old, btn_minus_old, changePmtrsButton;
    private Button btn_plus_height, btn_minus_height;
    private Button btn_plus_weight, btn_minus_weight;
    private FirebaseAuth mAuth;
    private double bmi = 0;
    private EditText number_old_ediText, number_height_ediText, number_weight_ediText;
    public String userOnline;
    int i = 0, old = 0, height = 0, weight = 0;

    private double valueHeight = 0;
    private double valueWeight = 0;

    public ChangeParametersFragment() {
        // Required empty public constructor
    }

    public static ChangeParametersFragment newInstance(String param1, String param2) {
        ChangeParametersFragment fragment = new ChangeParametersFragment();
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

        View rootView = inflater.inflate(R.layout.fragment_change_parameters, container, false);

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        changePmtrsButton = (Button) rootView.findViewById(R.id.change_pmtrs_button);
        changePmtrsButton.setOnClickListener(this);

        number_old_ediText = (EditText) rootView.findViewById(R.id.number_old_ediText);
        number_height_ediText = (EditText) rootView.findViewById(R.id.number_height_ediText);
        number_weight_ediText = (EditText) rootView.findViewById(R.id.number_weight_ediText);

        btn_plus_old = (Button) rootView.findViewById(R.id.btn_plus_old);
        btn_plus_old.setOnClickListener(this);
        btn_minus_old = (Button) rootView.findViewById(R.id.btn_minus_old);
        btn_minus_old.setOnClickListener(this);

        btn_plus_height = (Button) rootView.findViewById(R.id.btn_plus_height);
        btn_plus_height.setOnClickListener(this);
        btn_minus_height = (Button) rootView.findViewById(R.id.btn_minus_height);
        btn_minus_height.setOnClickListener(this);

        btn_plus_weight = (Button) rootView.findViewById(R.id.btn_plus_weight);
        btn_plus_weight.setOnClickListener(this);
        btn_minus_weight = (Button) rootView.findViewById(R.id.btn_minus_weight);
        btn_minus_weight.setOnClickListener(this);

        rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnline = ds.getKey();
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
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.arrowBackBtn:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.change_pmtrs_button:
                changeParameters();
                break;
            case R.id.btn_plus_old://+
                old++;
                number_old_ediText.setText(String.valueOf(old));
                break;
            case R.id.btn_minus_old://-
                if (String.valueOf(number_old_ediText.getText()).equals("0")) {
                } else {
                    old--;
                    number_old_ediText.setText(String.valueOf(old));
                }
                break;
            case R.id.btn_plus_height://+
                height++;
                number_height_ediText.setText(String.valueOf(height));
                break;
            case R.id.btn_minus_height://-
                if (String.valueOf(number_height_ediText.getText()).equals("0")) {
                } else {
                    height--;
                    number_height_ediText.setText(String.valueOf(height));
                }
                break;
            case R.id.btn_plus_weight://+
                weight++;
                number_weight_ediText.setText(String.valueOf(weight));
                break;
            case R.id.btn_minus_weight://-
                if (String.valueOf(number_weight_ediText.getText()).equals("0")) {
                } else {
                    weight--;
                    number_weight_ediText.setText(String.valueOf(weight));
                }
                break;
        }

    }

    @SuppressLint("DefaultLocale")
    private void changeParameters() {
        String old = number_old_ediText.getText().toString().trim();
        valueHeight = Integer.parseInt(number_height_ediText.getText().toString());
        valueWeight = Integer.parseInt(number_weight_ediText.getText().toString());
        bmi = (valueWeight / (valueHeight * valueHeight)) * 10000;
        String resultbmi = String.format("%.1f", bmi);

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("old", old);
        parameters.put("weight", valueWeight);
        parameters.put("height", valueHeight);
        parameters.put("bmi", resultbmi);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("Parameters")
                .setValue(parameters).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Pomyślnie zaktualizowano dane", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Nie udało się zaktualizowac danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}