package com.example.projektsilownia.mainMenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projektsilownia.R;
import com.example.projektsilownia.advancetraining.AdvanceTrening;
import com.example.projektsilownia.basictraining.BasicTrening;
import com.example.projektsilownia.custom.CustomDialogLogOut;
import com.example.projektsilownia.custom.ProfilPictureDialog;
import com.example.projektsilownia.settings.SettingsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private CardView btn_logout, btn_settings, btn_basic, btn_advanced;
    private static TextView textViewName, textViewOld, textViewWeight, textViewHeight, textViewBMI, textViewtime, textViewkcal;
    private CircleImageView circleImageView;
    private DatabaseReference rootRef;
    private Fragment fragment = null;
    public String userOnlineNow;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        textViewName = (TextView) rootView.findViewById(R.id.textViewName);
        textViewOld = (TextView) rootView.findViewById(R.id.textViewOld);
        textViewWeight = (TextView) rootView.findViewById(R.id.textViewWeight);
        textViewHeight = (TextView) rootView.findViewById(R.id.textViewHeight);
        textViewBMI = (TextView) rootView.findViewById(R.id.textViewBMI);
        textViewtime = (TextView) rootView.findViewById(R.id.textViewtime);
        textViewkcal = (TextView) rootView.findViewById(R.id.textViewkcal);

        rootRef = FirebaseDatabase.getInstance().getReference();
        Query query = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnlineNow = ds.getKey();
                    if (!userOnlineNow.equals(null)) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference db = database.getReference().child("Users").child(userOnlineNow).child("Parameters");
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            public void onDataChange(DataSnapshot data) {
                                textViewName.setText(userOnlineNow.toString());
                                textViewOld.setText(String.valueOf(data.child("old").getValue()));
                                textViewWeight.setText(String.valueOf(data.child("weight").getValue()));
                                textViewHeight.setText(String.valueOf(data.child("height").getValue()));
                                textViewBMI.setText(String.valueOf(data.child("bmi").getValue()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }

                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        query.addListenerForSingleValueEvent(valueEventListener);

        Query queryStats = rootRef.child("Users").orderByChild("uid").equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
        ValueEventListener valueEventListenerStats = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    userOnlineNow = ds.getKey();
                    if (!userOnlineNow.equals(null)) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference db = database.getReference().child("Users").child(userOnlineNow).child("Statistic");
                        db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @SuppressLint("SetTextI18n")
                            public void onDataChange(DataSnapshot dataStats) {
                                textViewtime.setText(String.valueOf(dataStats.child("time").getValue()));
                                textViewkcal.setText(String.valueOf(dataStats.child("calories").getValue()));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }

                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        queryStats.addListenerForSingleValueEvent(valueEventListenerStats);

        btn_logout = (CardView) rootView.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(this);

        btn_settings = (CardView) rootView.findViewById(R.id.btn_settings);
        btn_settings.setOnClickListener(this);

        btn_basic = (CardView) rootView.findViewById(R.id.btn_basic);
        btn_basic.setOnClickListener(this);

        btn_advanced = (CardView) rootView.findViewById(R.id.btn_advanced);
        btn_advanced.setOnClickListener(this);

        circleImageView = (CircleImageView) rootView.findViewById(R.id.imageViewProfil);
        circleImageView.setOnClickListener(this);

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {

                        return true;
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        rootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = dataSnapshot.child("Users").child(userOnlineNow).child("UserPhoto").child("image").getValue(String.class);

                Picasso.get().load(url)
                        .placeholder(R.drawable.ic_baseline_person_24).error(R.drawable.ic_baseline_person_24)
                        .into(circleImageView);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getUserOnlineNow() {
        return userOnlineNow;
    }

    public void setUserOnlineNow(String userOnlineNow) {
        this.userOnlineNow = userOnlineNow;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                logout();
                break;
            case R.id.btn_settings:
                fragment = new SettingsFragment();
                loadFragment(fragment);
                break;
            case R.id.imageViewProfil:
                ProfilPictureDialog profilPictureDialog = new ProfilPictureDialog();
                profilPictureDialog.show(getActivity().getSupportFragmentManager(),"ProfilPictureDialog");
                break;
            case R.id.btn_basic:
                Intent intent2 = new Intent(getContext().getApplicationContext(), BasicTrening.class);
                startActivity(intent2);
                break;
            case R.id.btn_advanced:
                Intent intent3 = new Intent(getContext().getApplicationContext(), AdvanceTrening.class);
                startActivity(intent3);
                break;
        }
    }

    private void logout() {
        CustomDialogLogOut customDialogLogOut = new CustomDialogLogOut(getActivity(), "Czy chcesz się wylogować ?");
        customDialogLogOut.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialogLogOut.show();
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