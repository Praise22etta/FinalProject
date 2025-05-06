package com.example.myfianlyearproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FoodBanksActivity extends AppCompatActivity {

    private RecyclerView recyclerViewFoodBanks;
    private LocationAdapter adapter;
    private List<LocationModel> foodBankList;
    private Button btnOpenLocation, btnBackToHome;

    private ArrayList<ItemName> selectedItems;
    private ArrayList<ManualItem> manualItems;
    private ArrayList<ScannedItemModel> scannedItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_banks);

        recyclerViewFoodBanks = findViewById(R.id.recyclerViewFoodBanks);
        btnOpenLocation = findViewById(R.id.btnOpenLocation);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        recyclerViewFoodBanks.setLayoutManager(new LinearLayoutManager(this));

        // Sample food banks
        foodBankList = new ArrayList<>();
        foodBankList.add(new LocationModel("Community Food Initiatives"));
        foodBankList.add(new LocationModel("Aberdeen Street Friends"));
        foodBankList.add(new LocationModel("Northfield Community Centre"));
        foodBankList.add(new LocationModel("Kings Church"));
        foodBankList.add(new LocationModel("Aberdeen Foyer"));
        foodBankList.add(new LocationModel("The Salvation Army"));

        adapter = new LocationAdapter(foodBankList);
        recyclerViewFoodBanks.setAdapter(adapter);

        // Get item lists from intent
        selectedItems = getIntent().getParcelableArrayListExtra("selectedItems");
        manualItems = getIntent().getParcelableArrayListExtra("manualItems");
        scannedItems = getIntent().getParcelableArrayListExtra("scannedItems");

        if (selectedItems == null) selectedItems = new ArrayList<>();
        if (manualItems == null) manualItems = new ArrayList<>();
        if (scannedItems == null) scannedItems = new ArrayList<>();

        // Open Maps + Log Drop-Off to Firestore
        btnOpenLocation.setOnClickListener(v -> {
            String selectedFoodBank = adapter.getSelectedFoodBank();
            if (selectedFoodBank != null) {
                DonationLogger.logDropOffToFirestore(
                        selectedFoodBank,
                        selectedItems,
                        manualItems,
                        scannedItems
                );
                openGoogleMaps(selectedFoodBank);
            } else {
                Toast.makeText(this, "Please select a food bank!", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(FoodBanksActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void openGoogleMaps(String location) {
        try {
            Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(location));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            } else {
                Uri webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(location));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
                startActivity(webIntent);
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error opening Maps!", Toast.LENGTH_SHORT).show();
            Log.e("MapsError", "Exception: " + e.getMessage());
        }
    }
}
