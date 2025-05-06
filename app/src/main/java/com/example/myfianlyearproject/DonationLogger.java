package com.example.myfianlyearproject;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.*;

public class DonationLogger {

    // Get current user ID
    private static String getCurrentUserId() {
        return FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : "anonymous_user";
    }

    // Timestamp format
    private static String getCurrentTimestamp() {
        return new SimpleDateFormat("d MMMM yyyy 'at' HH:mm:ss z", Locale.getDefault()).format(new Date());
    }

    // Shared Firestore instance
    private static final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    // Log Pick-Up Donation
    public static void logPickUpToFirestore(String address, String contact, String time,
                                            List<ItemName> selected, List<ManualItem> manual, List<ScannedItemModel> scanned) {

        String userId = getCurrentUserId();
        String timestamp = getCurrentTimestamp();

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("type", "pick-up");
        data.put("status", "pending");
        data.put("address", address);
        data.put("contactNumber", contact);
        data.put("pickupTime", time);
        data.put("timestamp", timestamp);

        List<String> itemList = new ArrayList<>();
        for (ItemName item : selected) {
            itemList.add(item.getName());
        }
        data.put("selectedItems", itemList);

        List<Map<String, String>> manualList = new ArrayList<>();
        for (ManualItem item : manual) {
            Map<String, String> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("quantity", item.getQuantity());
            map.put("expiryDate", item.getExpiryDate());
            manualList.add(map);
        }
        data.put("manualItems", manualList);

        List<String> scannedList = new ArrayList<>();
        for (ScannedItemModel item : scanned) {
            scannedList.add(item.getItemName());
        }
        data.put("scannedItems", scannedList);

        firestore.collection("donations")
                .add(data)
                .addOnSuccessListener(docRef -> Log.d("FirestoreLog", "Pick-up logged: " + docRef.getId()))
                .addOnFailureListener(e -> Log.e("FirestoreLog", "Failed to log pick-up", e));
    }

    // Log Drop-Off Donation
    public static void logDropOffToFirestore(String foodBank,
                                             List<ItemName> selected, List<ManualItem> manual, List<ScannedItemModel> scanned) {

        String userId = getCurrentUserId();
        String timestamp = getCurrentTimestamp();

        Map<String, Object> data = new HashMap<>();
        data.put("userId", userId);
        data.put("type", "drop-off");
        data.put("status", "pending");
        data.put("foodBank", foodBank);
        data.put("timestamp", timestamp);

        List<String> itemList = new ArrayList<>();
        for (ItemName item : selected) {
            itemList.add(item.getName());
        }
        data.put("selectedItems", itemList);

        List<Map<String, String>> manualList = new ArrayList<>();
        for (ManualItem item : manual) {
            Map<String, String> map = new HashMap<>();
            map.put("name", item.getName());
            map.put("quantity", item.getQuantity());
            map.put("expiryDate", item.getExpiryDate());
            manualList.add(map);
        }
        data.put("manualItems", manualList);

        List<String> scannedList = new ArrayList<>();
        for (ScannedItemModel item : scanned) {
            scannedList.add(item.getItemName());
        }
        data.put("scannedItems", scannedList);

        firestore.collection("donations")
                .add(data)
                .addOnSuccessListener(docRef -> Log.d("FirestoreLog", "Drop-off logged: " + docRef.getId()))
                .addOnFailureListener(e -> Log.e("FirestoreLog", "Failed to log drop-off", e));
    }
}
