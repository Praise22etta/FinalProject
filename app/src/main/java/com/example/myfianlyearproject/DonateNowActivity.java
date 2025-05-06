package com.example.myfianlyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import com.example.myfianlyearproject.DonationLogger;
import com.example.myfianlyearproject.PickUpRequest;

public class DonateNowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemsRecyclerViewAdapter adapter;
    private ArrayList<ItemName> selectedItems;
    private ArrayList<ScannedItemModel> scannedItems;
    private ArrayList<ManualItem> manualItems;
    private Button btnPickUp, btnDropOff, btnCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_now);

        recyclerView = findViewById(R.id.recyclerViewSelectedItems);
        btnPickUp = findViewById(R.id.btnPickUp);
        btnDropOff = findViewById(R.id.btnDropOff);
        btnCancel = findViewById(R.id.btnCancelDonation);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve items passed from previous activities
        selectedItems = getIntent().getParcelableArrayListExtra("selectedItems");
        scannedItems = getIntent().getParcelableArrayListExtra("scannedItems");
        manualItems = getIntent().getParcelableArrayListExtra("manualItems");

        if (selectedItems == null) selectedItems = new ArrayList<>();
        if (scannedItems == null) scannedItems = new ArrayList<>();
        if (manualItems == null) manualItems = new ArrayList<>();

        Log.d("DonateNowActivity", "Selected: " + selectedItems.size()
                + ", Scanned: " + scannedItems.size()
                + ", Manual: " + manualItems.size());

        // Display selected items only (you can expand this adapter to show scanned/manual items too)
        adapter = new ItemsRecyclerViewAdapter(selectedItems, new ArrayList<>(), selectedItems.size());
        recyclerView.setAdapter(adapter);

        // Pick Up Button - Go to PickUpActivity
        btnPickUp.setOnClickListener(v -> {
            if (selectedItems.isEmpty() && scannedItems.isEmpty() && manualItems.isEmpty()) {
                Toast.makeText(this, "No items selected for pick-up!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(DonateNowActivity.this, PickUpActivity.class);
                intent.putParcelableArrayListExtra("selectedItems", selectedItems);
                intent.putParcelableArrayListExtra("scannedItems", scannedItems);
                intent.putParcelableArrayListExtra("manualItems", manualItems);
                startActivity(intent);
            }
        });

        btnDropOff.setOnClickListener(v -> {
            Intent intent = new Intent(DonateNowActivity.this, FoodBanksActivity.class);

            intent.putParcelableArrayListExtra("selectedItems", selectedItems);
            intent.putParcelableArrayListExtra("manualItems", manualItems);
            intent.putParcelableArrayListExtra("scannedItems", scannedItems);

            startActivity(intent);
        });


        // Cancel Button - Return to previous screen
        btnCancel.setOnClickListener(v -> finish());
    }
}
