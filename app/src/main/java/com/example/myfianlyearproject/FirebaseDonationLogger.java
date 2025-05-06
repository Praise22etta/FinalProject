package com.example.myfianlyearproject;

import android.util.Log;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseDonationLogger {

    private static final String TAG = "DonationLogger";
    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    public FirebaseDonationLogger() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public void logDonation(List<String> itemNames, String donationType, String status) {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "anonymous";

        Map<String, Object> donation = new HashMap<>();
        donation.put("userId", userId);
        donation.put("items", itemNames);
        donation.put("type", donationType); // "pick-up" or "drop-off"
        donation.put("status", status);     //"pending", "confirmed", etc.
        donation.put("timestamp", Timestamp.now());

        db.collection("donations")
                .add(donation)
                .addOnSuccessListener(documentReference ->
                        Log.d(TAG, "Donation logged with ID: " + documentReference.getId()))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Error logging donation", e));
    }
}
