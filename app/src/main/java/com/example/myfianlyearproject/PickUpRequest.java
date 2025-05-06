package com.example.myfianlyearproject;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class PickUpRequest implements Parcelable {
    private String address;
    private String contactNumber;
    private String pickupTime;
    private ArrayList<ItemName> selectedItems;
    private ArrayList<ManualItem> manualItems;
    private ArrayList<ScannedItemModel> scannedItems;

    public PickUpRequest(String address, String contactNumber, String pickupTime,
                         ArrayList<ItemName> selectedItems,
                         ArrayList<ManualItem> manualItems,
                         ArrayList<ScannedItemModel> scannedItems) {
        this.address = address;
        this.contactNumber = contactNumber;
        this.pickupTime = pickupTime;
        this.selectedItems = selectedItems;
        this.manualItems = manualItems;
        this.scannedItems = scannedItems;
    }

    protected PickUpRequest(Parcel in) {
        address = in.readString();
        contactNumber = in.readString();
        pickupTime = in.readString();
        selectedItems = in.createTypedArrayList(ItemName.CREATOR);
        manualItems = in.createTypedArrayList(ManualItem.CREATOR);
        scannedItems = in.createTypedArrayList(ScannedItemModel.CREATOR);
    }

    public static final Creator<PickUpRequest> CREATOR = new Creator<PickUpRequest>() {
        @Override
        public PickUpRequest createFromParcel(Parcel in) {
            return new PickUpRequest(in);
        }

        @Override
        public PickUpRequest[] newArray(int size) {
            return new PickUpRequest[size];
        }
    };

    public String getAddress() {
        return address;
    }
    public String getContactNumber() {
        return contactNumber;
    }
    public String getPickupTime() {
        return pickupTime;
    }
    public ArrayList<ItemName> getSelectedItems() {
        return selectedItems;
    }
    public ArrayList<ManualItem> getManualItems() {
        return manualItems;
    }
    public ArrayList<ScannedItemModel> getScannedItems() {
        return scannedItems;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(contactNumber);
        dest.writeString(pickupTime);
        dest.writeTypedList(selectedItems);
        dest.writeTypedList(manualItems);
        dest.writeTypedList(scannedItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
