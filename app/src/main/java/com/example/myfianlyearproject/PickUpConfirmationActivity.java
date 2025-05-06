package com.example.myfianlyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class PickUpConfirmationActivity extends AppCompatActivity {

    private TextView txtPickupDetails, txtItems;
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickup_confirmation);

        txtPickupDetails = findViewById(R.id.txtPickupDetails);
        txtItems = findViewById(R.id.txtItems);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        PickUpRequest request = getIntent().getParcelableExtra("pickupRequest");

        if (request != null) {
            // Display pickup details
            String details = "📍 Address: " + request.getAddress() + "\n"
                    + "📞 Contact: " + request.getContactNumber() + "\n"
                    + "⏰ Time: " + request.getPickupTime();
            txtPickupDetails.setText(details);

            // Display all types of items
            StringBuilder itemList = new StringBuilder("📦 Items to be picked up:\n");

            ArrayList<ItemName> selected = request.getSelectedItems();
            ArrayList<ManualItem> manual = request.getManualItems();
            ArrayList<ScannedItemModel> scanned = request.getScannedItems();

            if (selected != null && !selected.isEmpty()) {
                itemList.append("🔹 Selected Items:\n");
                for (ItemName item : selected) {
                    itemList.append("• ").append(item.getName()).append("\n");
                }
            }

            if (manual != null && !manual.isEmpty()) {
                itemList.append("\n📝 Manual Entry:\n");
                for (ManualItem item : manual) {
                    itemList.append("• ").append(item.getName())
                            .append(" (Qty: ").append(item.getQuantity())
                            .append(", Exp: ").append(item.getExpiryDate())
                            .append(")\n");
                }
            }

            if (scanned != null && !scanned.isEmpty()) {
                itemList.append("\n📷 Scanned Items:\n");
                for (ScannedItemModel item : scanned) {
                    itemList.append("• ").append(item.getItemName()).append("\n");
                }
            }

            if (itemList.toString().trim().equals("📦 Items to be picked up:")) {
                txtItems.setText("No items selected.");
            } else {
                txtItems.setText(itemList.toString());
            }
        } else {
            txtPickupDetails.setText("❌ No pickup details received.");
            txtItems.setText("No items.");
        }

        btnBackToHome.setOnClickListener(v -> {
            Intent intent = new Intent(PickUpConfirmationActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
