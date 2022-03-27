package com.example.projektsilownia.custom;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.projektsilownia.MainActivity;
import com.example.projektsilownia.R;
import com.google.firebase.auth.FirebaseAuth;

public class CustomDialogLogOut extends Dialog implements View.OnClickListener {

    Activity activity;
    Button btnYes, btnNo;
    String message;
    TextView textMessage;//textViewMessage

    public CustomDialogLogOut(Activity activity, String s) {
        super(activity);
        this.activity = activity;
        this.message= s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_logout);
        TextView textMessage = findViewById(R.id.textViewMessage);
        btnYes = (Button) findViewById(R.id.btnYes);
        btnNo = (Button) findViewById(R.id.btnNo);

        textMessage.setText(message);

        btnYes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;

        switch (view.getId()) {
            case R.id.btnYes:
                CustomProgressBar customProgressBar = new CustomProgressBar(activity, "Trwa wylogowanie");
                customProgressBar.startProgressBarDialog();

                FirebaseAuth.getInstance().signOut();

                if (FirebaseAuth.getInstance().getCurrentUser() == null) {

                    Intent welcome = new Intent(getContext(), MainActivity.class);
                    welcome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivity(welcome);

                    customProgressBar.dismissDialog();
                    Toast.makeText(getContext(), "Pomyślnie wylogowano", Toast.LENGTH_SHORT).show();
                } else {
                    customProgressBar.dismissDialog();
                    Toast.makeText(getContext(), "Nie udało się wylogować", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnNo:
                dismiss();
                break;
        }
    }
}
