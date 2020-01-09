package com.cranberryanalytics.techlabassignment.main;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cranberryanalytics.techlabassignment.R;
import com.cranberryanalytics.techlabassignment.main.model.MainItem;

import java.util.ArrayList;
import java.util.List;

public class MainItemsRecyclerViewAdapter extends
        RecyclerView.Adapter<MainItemsRecyclerViewAdapter.MyItemViewHolder> {

    List<MainItem> items = new ArrayList<>();

    public void setItems(List<MainItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_text, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_image, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_rating_bar, parent, false);
                break;
            case 3:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_radio_buttons, parent, false);
                break;
        }
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyItemViewHolder holder, int position) {

        //System.out.println("--------------------------->>>>"+type);
        final MainItem item = items.get(position);
        switch (item.getType()) {
            case 0:
                if (holder.textView != null) {
                    holder.textView.setText(item.getText());
                }
                break;
            case 1:
                // holder.imageView;
                break;
            case 2:
                if (holder.ratingBar != null) {

                    holder.ratingBar.setOnRatingBarChangeListener(null);
                    holder.ratingBar.setRating(item.getRate());
                    holder.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                            item.setRate(ratingBar.getRating());
                        }
                    });
                }

                break;
            case 3:
                if (holder.radioButton != null) {
                    holder.radioButton.setOnCheckedChangeListener(null);
                    holder.radioButton.setChecked(item.isEnabled());
                    holder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            item.setEnabled(b);
                        }
                    });
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        RatingBar ratingBar;
        RadioButton radioButton;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.item_textView);

            imageView = itemView.findViewById(R.id.item_imageView);

            ratingBar = itemView.findViewById(R.id.item_ratingBar);

            radioButton = itemView.findViewById(R.id.item_radioButton);

        }
    }

}
