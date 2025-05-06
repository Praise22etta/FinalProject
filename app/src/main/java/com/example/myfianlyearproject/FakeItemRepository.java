package com.example.myfianlyearproject;

import java.util.HashMap;
import java.util.Map;

public class FakeItemRepository implements ItemRepository {
    private Map<String, String> itemMap;

    public FakeItemRepository() {
        itemMap = new HashMap<>();
        // Dummy data for testing
        itemMap.put("123456789012", "Milk");
        itemMap.put("987654321098", "Bread");
        itemMap.put("111111111111", "Eggs");
        itemMap.put("5038512005041", "Juice Burst");
    }

    @Override
    public String getItemName(String barcode) {
        // Return the item name or "Unknown Item" if the barcode is not in our dummy data
        return itemMap.getOrDefault(barcode, "Unknown Item");
    }
}
