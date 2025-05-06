package com.example.myfianlyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerViewItems;
    private ItemsRecyclerViewAdapter adapter;
    private Button btnDonateNow, btnScanQR, btnManualEntry; // Added btnManualEntry
    private List<ItemName> itemList;
    private ArrayList<ItemName> selectedItems = new ArrayList<>();
    private int maxSelection = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerViewItems = findViewById(R.id.recyclerViewItems);
        btnDonateNow = findViewById(R.id.btnDonateNow);
        btnScanQR = findViewById(R.id.btnScanQR);
        btnManualEntry = findViewById(R.id.btnManualEntry); // FIXED: Initialize btnManualEntry

        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        // Sample food items
        itemList = new ArrayList<>();
        itemList.add(new ItemName("Rice"));
        itemList.add(new ItemName("Canned Beans"));
        itemList.add(new ItemName("Pasta"));
        itemList.add(new ItemName("Vegetables"));
        itemList.add(new ItemName("Milk Powder"));
        itemList.add(new ItemName("Flour"));
        itemList.add(new ItemName("Cereal"));

        // Initialize RecyclerView Adapter
        adapter = new ItemsRecyclerViewAdapter(itemList, selectedItems, maxSelection);
        recyclerViewItems.setAdapter(adapter);

        // Set Click Listeners
        btnDonateNow.setOnClickListener(view -> {
            if (!selectedItems.isEmpty()) {
                openDonateNowActivity();
            } else {
                Toast.makeText(HomeActivity.this, "No items selected!", Toast.LENGTH_SHORT).show();
            }
        });

        btnScanQR.setOnClickListener(view -> openScanActivity());

        btnManualEntry.setOnClickListener(view -> openManualEntryActivity()); // FIXED: Now initialized
    }

    private void openDonateNowActivity() {
        if (selectedItems.isEmpty()) {
            Toast.makeText(HomeActivity.this, "No items selected!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(HomeActivity.this, DonateNowActivity.class);
        intent.putParcelableArrayListExtra("selectedItems", selectedItems);
        startActivity(intent);
    }

    private void openScanActivity() {
        Intent intent = new Intent(HomeActivity.this, ScanItemsActivity.class);
        startActivity(intent);
    }

    private void openManualEntryActivity() {
        Intent intent = new Intent(HomeActivity.this, ManualEntryActivity.class);
        startActivity(intent);
    }
}
