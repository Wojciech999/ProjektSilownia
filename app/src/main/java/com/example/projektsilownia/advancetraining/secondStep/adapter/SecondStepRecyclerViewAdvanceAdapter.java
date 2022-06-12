package com.example.projektsilownia.advancetraining.secondStep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projektsilownia.R;
import com.example.projektsilownia.basictraining.model.GetTreningValueModel;

import java.util.ArrayList;

public class SecondStepRecyclerViewAdvanceAdapter extends RecyclerView.Adapter<SecondStepRecyclerViewAdvanceAdapter.SecondStepRecyclerViewHolder> {

    Context context;
    ArrayList<GetTreningValueModel> getTreningValueModels;

    public SecondStepRecyclerViewAdvanceAdapter(Context context, ArrayList<GetTreningValueModel> getTreningValueModels){
        this.context = context;
        this.getTreningValueModels = getTreningValueModels;
    }

    @NonNull
    @Override
    public SecondStepRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view_display_trening, parent, false);
        return new SecondStepRecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondStepRecyclerViewHolder holder, int position) {
        GetTreningValueModel getTreningValueModel = getTreningValueModels.get(position);

        Glide.with(context)
                .load(getTreningValueModel.giftrening)
                .into(holder.giftrening);

        holder.Desc.setText(String.valueOf(getTreningValueModel.getDesc()).toString());
        holder.Name.setText(String.valueOf(getTreningValueModel.getName()).toString());
        holder.NumberOfRepetitions.setText(String.valueOf(getTreningValueModel.getNumberOfRepetitions()).toString());
        holder.NumberOfSeries.setText(String.valueOf(getTreningValueModel.getNumberOfSeries()).toString());

    }

    @Override
    public int getItemCount() {
        return getTreningValueModels.size();
    }

    public static class SecondStepRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView Desc, Name, NumberOfRepetitions, NumberOfSeries;
        ImageView giftrening;

        public SecondStepRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            giftrening = itemView.findViewById(R.id.gif);
            Desc = itemView.findViewById(R.id.descTextView);
            Name = itemView.findViewById(R.id.nameTextView);
            NumberOfRepetitions = itemView.findViewById(R.id.numberOfRepetitionsTextView);
            NumberOfSeries = itemView.findViewById(R.id.numberOfSeriesTextView);
        }

    }
}
