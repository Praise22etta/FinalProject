package com.example.myfianlyearproject;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfianlyearproject.Web3Provider;
import com.example.myfianlyearproject.CredentialsManager;
import com.example.myfianlyearproject.GasProvider;
import com.example.myfianlyearproject.BlockchainLogger;

import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PickUpActivity extends AppCompatActivity {

    private EditText etAddress, etContactNumber, etPickupTime;
    private Button btnConfirmPickup;

    private ArrayList<ItemName> selectedItems;
    private ArrayList<ManualItem> manualItems;
    private ArrayList<ScannedItemModel> scannedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);

        etAddress = findViewById(R.id.etAddress);
        etContactNumber = findViewById(R.id.etContactNumber);
        etPickupTime = findViewById(R.id.etPickupTime);
        btnConfirmPickup = findViewById(R.id.btnConfirmPickup);

        selectedItems = getIntent().getParcelableArrayListExtra("selectedItems");
        manualItems = getIntent().getParcelableArrayListExtra("manualItems");
        scannedItems = getIntent().getParcelableArrayListExtra("scannedItems");

        if (selectedItems == null) selectedItems = new ArrayList<>();
        if (manualItems == null) manualItems = new ArrayList<>();
        if (scannedItems == null) scannedItems = new ArrayList<>();

        etPickupTime.setOnClickListener(v -> showTimePicker());

        btnConfirmPickup.setOnClickListener(view -> confirmPickup());
    }

    private void showTimePicker() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (TimePicker view, int hourOfDay, int minute1) -> {
                    String time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                    etPickupTime.setText(time);
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }

    private void confirmPickup() {
        String address = etAddress.getText().toString().trim();
        String contactNumberRaw = etContactNumber.getText().toString().trim();
        String pickupTime = etPickupTime.getText().toString().trim();

        if (address.isEmpty()) {
            Toast.makeText(this, "Please enter your pick-up address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (contactNumberRaw.isEmpty()) {
            Toast.makeText(this, "Please enter your contact number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pickupTime.isEmpty()) {
            Toast.makeText(this, "Please select a pick-up time", Toast.LENGTH_SHORT).show();
            return;
        }

        String contactNumberFormatted = PhoneNumberUtils.formatNumber(contactNumberRaw, Locale.getDefault().getCountry());

        PickUpRequest request = new PickUpRequest(
                address,
                contactNumberFormatted,
                pickupTime,
                selectedItems,
                manualItems,
                scannedItems
        );

        FirebaseDonationLogger logger = new FirebaseDonationLogger();
        ArrayList<String> allItems = new ArrayList<>();
        for (ItemName item : selectedItems) allItems.add(item.getName());
        for (ManualItem manualItem : manualItems) allItems.add(manualItem.getName());
        for (ScannedItemModel scannedItem : scannedItems) allItems.add(scannedItem.getItemName());

        logger.logDonation(allItems, "pick-up", "pending");

        // Blockchain Logging
        String donorEmail = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getEmail() : "anonymous";

        String timestamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            timestamp = LocalDateTime.now().toString();
        }
        String itemsJson = new Gson().toJson(allItems);

        BlockchainLogger blockchainLogger = new BlockchainLogger(
                Web3Provider.getInstance(this),
                CredentialsManager.getCredentials(this),
                GasProvider.getInstance(),
                "0x5819b9891792e2d66e9bc8cc4beb4882703a749a"
                
        );

        blockchainLogger.logDonationAsync(donorEmail, "PickUp", timestamp, itemsJson)
                .thenAccept(receipt -> runOnUiThread(() ->
                        Toast.makeText(this, "Blockchain logged ✅", Toast.LENGTH_SHORT).show()
                ))
                .exceptionally(error -> {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Blockchain logged : " + error.getMessage(), Toast.LENGTH_LONG).show()
                    );
                    return null;
                });

        Intent intent = new Intent(PickUpActivity.this, PickUpConfirmationActivity.class);
        intent.putExtra("pickupRequest", request);
        startActivity(intent);
        finish();
    }
}
