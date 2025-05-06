package com.example.myfianlyearproject;

import android.os.Parcel;
import android.os.Parcelable;

public class ManualItem implements Parcelable {
    private String name;
    private String expiryDate;
    private String quantity;
    private String category;
    private String description;

    // Constructor with all fields
    public ManualItem(String name, String expiryDate, String quantity, String category, String description) {
        this.name = name;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
        this.category = category;
        this.description = description;
    }

    // Parcelable constructor
    protected ManualItem(Parcel in) {
        name = in.readString();
        expiryDate = in.readString();
        quantity = in.readString();
        category = in.readString();
        description = in.readString();
    }

    public static final Creator<ManualItem> CREATOR = new Creator<ManualItem>() {
        @Override
        public ManualItem createFromParcel(Parcel in) {
            return new ManualItem(in);
        }

        @Override
        public ManualItem[] newArray(int size) {
            return new ManualItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(expiryDate);
        dest.writeString(quantity);
        dest.writeString(category);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
