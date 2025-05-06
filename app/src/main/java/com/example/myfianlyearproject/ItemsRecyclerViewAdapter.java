package com.example.myfianlyearproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ViewHolder> {

    private List<ItemName> itemList;
    private ArrayList<ItemName> selectedItems;
    private int maxSelection;

    public ItemsRecyclerViewAdapter(List<ItemName> itemList, ArrayList<ItemName> selectedItems, int maxSelection) {
        this.itemList = itemList;
        this.selectedItems = selectedItems;
        this.maxSelection = maxSelection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemName item = itemList.get(position);
        holder.setData(item.getName());

        // Remove any existing listener before updating checkbox state
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(selectedItems.contains(item));

        // Checkbox click logic
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> toggleSelection(item, isChecked));

        // Click entire item to toggle selection
        holder.itemView.setOnClickListener(v -> holder.checkBox.performClick());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // **FIX: Properly handle item selection**
    private void toggleSelection(ItemName item, boolean isChecked) {
        if (isChecked) {
            if (!selectedItems.contains(item) && selectedItems.size() < maxSelection) {
                selectedItems.add(item);
            }
        } else {
            selectedItems.remove(item);
        }
        notifyDataSetChanged();
    }

    public ArrayList<ItemName> getSelectedItems() {
        return new ArrayList<>(selectedItems); // Return a copy to prevent accidental modifications
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            checkBox = itemView.findViewById(R.id.checkbox);
        }

        public void setData(String titleText) {
            title.setText(titleText);
        }
    }
}
