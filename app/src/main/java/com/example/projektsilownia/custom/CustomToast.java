package com.example.projektsilownia.custom;

import static com.example.projektsilownia.R.drawable.ic_icon_bmi_64dp;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projektsilownia.R;

public class CustomToast {

    Activity activity;
    String message;

/*    public CustomToast(Activity activity, String message) {
        this.activity = activity;
        this.message = message;
    }*/

    public void showToast(Activity activity, String message, int image) {

        Toast toast = new Toast(activity);

        View view = LayoutInflater.from(activity)
                .inflate(R.layout.custom_toast, null);

        TextView tvMessage = view.findViewById(R.id.textViewToast);
        tvMessage.setText(message);

        ImageView iconToast = view.findViewById(R.id.imageViewToast);
        iconToast.setImageResource(image);

        toast.setView(view);
        toast.show();
    }
}
