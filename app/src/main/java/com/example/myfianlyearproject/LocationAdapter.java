package com.example.myfianlyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<LocationModel> locationList;
    private int selectedPosition = RecyclerView.NO_POSITION; // Default: No selection

    public LocationAdapter(List<LocationModel> locationList) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_bank_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationModel location = locationList.get(position);
        holder.foodBankName.setText(location.getName());

        // Ensure only one item can be selected
        holder.radioButton.setChecked(position == selectedPosition);

        // Handle selection logic
        holder.itemView.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition != selectedPosition) {
                notifyItemChanged(selectedPosition); // Uncheck previous selection
                selectedPosition = adapterPosition;
                notifyItemChanged(selectedPosition); // Check new selection
            }
        });

        holder.radioButton.setOnClickListener(v -> {
            int adapterPosition = holder.getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION && adapterPosition != selectedPosition) {
                notifyItemChanged(selectedPosition);
                selectedPosition = adapterPosition;
                notifyItemChanged(selectedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public String getSelectedFoodBank() {
        return (selectedPosition != RecyclerView.NO_POSITION) ? locationList.get(selectedPosition).getName() : null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodBankName;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodBankName = itemView.findViewById(R.id.foodBankName);
            radioButton = itemView.findViewById(R.id.radioButton);
        }
    }
}
