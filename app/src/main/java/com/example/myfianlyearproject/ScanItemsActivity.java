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
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.journeyapps.barcodescanner.CaptureActivity;
import java.util.ArrayList;
import java.util.List;

public class ScanItemsActivity extends AppCompatActivity {

    private Button btnScan, btnProceed, btnCancel;
    private RecyclerView recyclerView;
    private ScannedItemsAdapter adapter;
    private List<ScannedItemModel> scannedItemsList;

    // Declare the repository
    private ItemRepository itemRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);

        btnScan = findViewById(R.id.btnScan);
        btnProceed = findViewById(R.id.btnProceed);
        btnCancel = findViewById(R.id.btnCancel);
        recyclerView = findViewById(R.id.recyclerViewScannedItems);

        scannedItemsList = new ArrayList<>();
        adapter = new ScannedItemsAdapter(scannedItemsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Initialize the repository with the fake implementation
        itemRepository = new FakeItemRepository();

        // Start the barcode scanner when btnScan is clicked
        btnScan.setOnClickListener(v -> startBarcodeScanner());

        // Proceed to the next activity if at least one item is scanned
        btnProceed.setOnClickListener(v -> {
            if (scannedItemsList.isEmpty()) {
                Toast.makeText(this, "No items scanned!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(ScanItemsActivity.this, DonateNowActivity.class);
                intent.putParcelableArrayListExtra("scannedItems", new ArrayList<>(scannedItemsList));
                startActivity(intent);
            }
        });

        // Cancel and return to the previous screen
        btnCancel.setOnClickListener(v -> finish());
    }

    // Barcode Scanner method
    private void startBarcodeScanner() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan an item");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(CaptureActivityAny.class);

        barcodeLauncher.launch(options);
    }

    // Handle Scan Results using the repository lookup
    private final androidx.activity.result.ActivityResultLauncher<ScanOptions> barcodeLauncher =
            registerForActivityResult(new ScanContract(), result -> {
                if (result.getContents() != null) {
                    String scannedCode = result.getContents();
                    Log.d("ScanItemsActivity", "Scanned Code: " + scannedCode);
                    // Get the item name from the repository based on the scanned barcode
                    String itemName = itemRepository.getItemName(scannedCode);
                    scannedItemsList.add(new ScannedItemModel(itemName));
                    adapter.notifyDataSetChanged();
                }
            });
}



