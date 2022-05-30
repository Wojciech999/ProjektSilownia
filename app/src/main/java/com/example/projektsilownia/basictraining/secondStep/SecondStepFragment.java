package com.example.projektsilownia.basictraining.secondStep;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projektsilownia.R;
import com.example.projektsilownia.basictraining.model.GetTreningValueModel;
import com.example.projektsilownia.basictraining.secondStep.adapter.SecondStepRecyclerViewAdapter;
import com.example.projektsilownia.mainMenu.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class SecondStepFragment extends Fragment implements View.OnClickListener {

    private String typesOfExercises = "TypesOfExercises";
    private String typesOfTrening = "Basic";
    private String trening = "Trening";
    private String firstStepBasic;
    private String secondStepBasic;
    public Button button_endOfTrening;
    public HomeFragment homeFragment;
    private DatabaseReference rootRef;

    public String caloriesStatistic, timeStatistic;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int calories = 0;
    public int time = 0;

    private AppCompatImageButton arrowBack_button;
    public String userOnline;

    private RecyclerView recyclerViewDisplayTrening;
    DatabaseReference database;
    SecondStepRecyclerViewAdapter secondStepRecyclerViewAdapter;
    ArrayList<GetTreningValueModel> getTreningValueModels;

    public SecondStepFragment(String firstStepBasic, String secondStepBasic, String userOnline) {
        this.firstStepBasic = firstStepBasic;
        this.secondStepBasic = secondStepBasic;
        this.userOnline = userOnline;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getFirstStepBasic() {
        return firstStepBasic;
    }

    public String getSecondStepBasic() {
        return secondStepBasic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second_step, container, false);
        Log.d("SPRAWDZANIE", firstStepBasic + " " + secondStepBasic);

        arrowBack_button = (AppCompatImageButton) rootView.findViewById(R.id.arrowBackBtn);
        arrowBack_button.setOnClickListener(this);

        recyclerViewDisplayTrening = rootView.findViewById(R.id.recyclerViewDisplayTrening);
        database = FirebaseDatabase.getInstance().getReference(typesOfExercises).child(typesOfTrening)
                .child(firstStepBasic).child(secondStepBasic).child(trening);

        recyclerViewDisplayTrening.setHasFixedSize(true);
        recyclerViewDisplayTrening.setLayoutManager(new LinearLayoutManager(getContext()));

        getTreningValueModels = new ArrayList<>();
        secondStepRecyclerViewAdapter = new SecondStepRecyclerViewAdapter(getContext(), getTreningValueModels);
        recyclerViewDisplayTrening.setAdapter(secondStepRecyclerViewAdapter);

        button_endOfTrening = (Button) rootView.findViewById(R.id.button_endOfTrening);
        button_endOfTrening.setOnClickListener(this);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    GetTreningValueModel getTreningValueModel = dataSnapshot.getValue(GetTreningValueModel.class);
                    getTreningValueModels.add(getTreningValueModel);
                }
                secondStepRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendTrening(getFirstStepBasic(), getSecondStepBasic());

        Log.d("Log5", String.valueOf(userOnline));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference db = database.getReference().child("Users").child(String.valueOf(userOnline)).child("Statistic");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            public void onDataChange(DataSnapshot dataStats) {
                setTimeStatistic(String.valueOf(dataStats.child("time").getValue()));
                setCaloriesStatistic(String.valueOf(dataStats.child("calories").getValue()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.arrowBackBtn:
                if (getFragmentManager().getBackStackEntryCount() > 0) {
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.button_endOfTrening:
                sendTreningToDataBase(getCalories(), getTime());
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void sendTrening(String firstStepBasic, String secondStepBasic) {

        if (firstStepBasic.equals("EnduranceExercises") && secondStepBasic.equals("Time15Min")) {
            setTime(15);
            setCalories(250);
            button_endOfTrening.setText("Zakończ (+200 kcal, +15min)");
        } else if (firstStepBasic.equals("EnduranceExercises") && secondStepBasic.equals("Time30Min")) {
            setTime(30);
            setCalories(350);
            button_endOfTrening.setText("Zakończ (+350 kcal, +30min)");
        } else if (firstStepBasic.equals("EnduranceExercises") && secondStepBasic.equals("Time60Min")) {
            setTime(60);
            setCalories(750);
            button_endOfTrening.setText("Zakończ (+750 kcal, +60min)");
        } else if (firstStepBasic.equals("RelaxationExercises") && secondStepBasic.equals("Time15Min")) {
            setTime(15);
            setCalories(150);
            button_endOfTrening.setText("Zakończ (+150 kcal, +15min)");
        } else if (firstStepBasic.equals("RelaxationExercises") && secondStepBasic.equals("Time30Min")) {
            setTime(30);
            setCalories(250);
            button_endOfTrening.setText("Zakończ (+250 kcal, +30min)");
        } else if (firstStepBasic.equals("RelaxationExercises") && secondStepBasic.equals("Time60Min")) {
            setTime(60);
            setCalories(550);
            button_endOfTrening.setText("Zakończ (+550 kcal, +60min)");
        } else if (firstStepBasic.equals("StrengthExercises") && secondStepBasic.equals("Time15Min")) {
            setTime(15);
            setCalories(450);
            button_endOfTrening.setText("Zakończ (+450 kcal, +15min)");
        } else if (firstStepBasic.equals("StrengthExercises") && secondStepBasic.equals("Time30Min")) {
            setTime(30);
            setCalories(650);
            button_endOfTrening.setText("Zakończ (+650 kcal, +30min)");
        } else if (firstStepBasic.equals("StrengthExercises") && secondStepBasic.equals("Time60Min")) {
            setTime(60);
            setCalories(750);
            button_endOfTrening.setText("Zakończ (+750 kcal, +60min)");
        }
    }

    public String getCaloriesStatistic() {
        return caloriesStatistic;
    }

    public void setCaloriesStatistic(String caloriesStatistic) {
        this.caloriesStatistic = caloriesStatistic;
    }

    public String getTimeStatistic() {
        return timeStatistic;
    }

    public void setTimeStatistic(String timeStatistic) {
        this.timeStatistic = timeStatistic;
    }

    private void sendTreningToDataBase(int firstStepBasic, int secondStepBasic) {
        //pobranie danych /Users/bob/Statistic -calories -time
        Log.d("Log1", String.valueOf(firstStepBasic) + " " + String.valueOf(secondStepBasic));
        Log.d("Log6", String.valueOf(userOnline));
        Log.d("Log77", getCaloriesStatistic() + " " + getTimeStatistic());

        int caloryPush = Integer.parseInt(getCaloriesStatistic()) + getCalories();
        int timePush = Integer.parseInt(getTimeStatistic()) + getTime();

        Log.d("Log8", String.valueOf(caloryPush));
        Log.d("Log9", String.valueOf(timePush));

        Map<String, Object> statistic = new HashMap<String, Object>();
        statistic.put("calories", caloryPush);
        statistic.put("time", timePush);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(userOnline).child("Statistic")
                .setValue(statistic).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Gratulacje ukończonego treningu", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), "Nie udało się połączyć z bazą danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
