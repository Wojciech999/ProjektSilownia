package com.example.projektsilownia.custom;

import android.app.Activity;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.projektsilownia.R;

public class CustomProgressBar extends Dialog {

    private Activity activity;
    private TextView textViewProgressBarTitle;
    private ProgressBar progressBar;
    private String title;

    public CustomProgressBar(Activity myActivity, String title) {
        super(myActivity);
        activity = myActivity;

        this.title = title;
    }

    final Dialog dialog = new Dialog(getContext());

    public void startProgressBarDialog(){
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_progressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView text = (TextView) dialog.findViewById(R.id.textViewProgressBarTitle);
        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progresBar);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.GRAY));
        text.setText(title);
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
