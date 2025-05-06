package com.example.myfianlyearproject;

import android.os.Parcel;
import android.os.Parcelable;

public class ScannedItemModel implements Parcelable {
    private String itemName;

    public ScannedItemModel(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    protected ScannedItemModel(Parcel in) {
        itemName = in.readString();
    }

    public static final Creator<ScannedItemModel> CREATOR = new Creator<ScannedItemModel>() {
        @Override
        public ScannedItemModel createFromParcel(Parcel in) {
            return new ScannedItemModel(in);
        }

        @Override
        public ScannedItemModel[] newArray(int size) {
            return new ScannedItemModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
    }
}
