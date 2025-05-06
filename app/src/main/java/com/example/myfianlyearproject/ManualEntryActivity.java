package com.example.myfianlyearproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ManualEntryActivity extends AppCompatActivity {

    private EditText etItemName, etExpiryDate, etQuantity, etDescription;
    private Spinner spinnerCategory;
    private Button btnAddItem, btnPickUp, btnDropOff;
    private RecyclerView recyclerViewItems;
    private ManualEntryAdapter adapter;
    private List<ManualItem> itemList;

    private static final String PREFS_NAME = "ManualEntryPrefs";
    private static final String ITEMS_KEY = "SavedItems";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_entry);

        etItemName = findViewById(R.id.etItemName);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        etQuantity = findViewById(R.id.etQuantity);
        etDescription = findViewById(R.id.etDescription);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnAddItem = findViewById(R.id.btnAddItem);
        btnPickUp = findViewById(R.id.btnPickUp);
        btnDropOff = findViewById(R.id.btnDropOff);
        recyclerViewItems = findViewById(R.id.recyclerViewItems);

        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));
        itemList = loadItems();

        adapter = new ManualEntryAdapter(itemList, this::deleteItem);
        recyclerViewItems.setAdapter(adapter);

        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(this,
                R.array.food_categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        btnAddItem.setOnClickListener(view -> addItem());
        btnPickUp.setOnClickListener(view -> goToPickUp());
        btnDropOff.setOnClickListener(view -> goToDropOff());
    }

    private void addItem() {
        String name = etItemName.getText().toString().trim();
        String expiry = etExpiryDate.getText().toString().trim();
        String quantity = etQuantity.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String description = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(expiry) || TextUtils.isEmpty(quantity)) {
            Toast.makeText(this, "Please fill all required fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        ManualItem newItem = new ManualItem(name, quantity, expiry, category, description);
        itemList.add(newItem);
        adapter.notifyDataSetChanged();
        saveItems();

        etItemName.setText("");
        etExpiryDate.setText("");
        etQuantity.setText("");
        etDescription.setText("");
    }

    private void deleteItem(int position) {
        itemList.remove(position);
        adapter.notifyDataSetChanged();
        saveItems();
    }

    private void saveItems() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = new Gson().toJson(itemList);
        editor.putString(ITEMS_KEY, json);
        editor.apply();
    }

    private List<ManualItem> loadItems() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(ITEMS_KEY, null);
        Type type = new TypeToken<ArrayList<ManualItem>>() {}.getType();
        return json == null ? new ArrayList<>() : new Gson().fromJson(json, type);
    }

    private void goToPickUp() {
        Intent intent = new Intent(this, PickUpActivity.class);
        intent.putParcelableArrayListExtra("manualItems", new ArrayList<>(itemList));
        startActivity(intent);
    }

    private void goToDropOff() {
        Intent intent = new Intent(this, FoodBanksActivity.class);
        intent.putParcelableArrayListExtra("manualItems", new ArrayList<>(itemList));
        startActivity(intent);
    }
}
