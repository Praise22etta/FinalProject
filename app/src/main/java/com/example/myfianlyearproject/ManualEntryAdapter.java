package com.example.myfianlyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManualEntryAdapter extends RecyclerView.Adapter<ManualEntryAdapter.ViewHolder> {

    private List<ManualItem> itemList;
    private OnDeleteClickListener onDeleteClickListener;

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public ManualEntryAdapter(List<ManualItem> itemList, OnDeleteClickListener listener) {
        this.itemList = itemList;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_manual_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ManualItem item = itemList.get(position);

        // Properly display item details
        String details = "Name: " + item.getName() + "\n"
                + "Quantity: " + item.getQuantity() + "\n"
                + "Expiry: " + item.getExpiryDate() + "\n"
                + "Category: " + item.getCategory() + "\n"
                + "Description: " + item.getDescription();

        holder.txtDetails.setText(details);

        holder.btnDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDetails;
        Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            txtDetails = itemView.findViewById(R.id.txtDetails);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
