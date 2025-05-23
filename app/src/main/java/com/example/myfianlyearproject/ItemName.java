package com.example.myfianlyearproject;

import android.os.Parcel;
import android.os.Parcelable;

public class ItemName implements Parcelable {
    private String name;

    public ItemName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // Parcelable implementation
    protected ItemName(Parcel in) {
        name = in.readString();
    }

    public static final Creator<ItemName> CREATOR = new Creator<ItemName>() {
        @Override
        public ItemName createFromParcel(Parcel in) {
            return new ItemName(in);
        }

        @Override
        public ItemName[] newArray(int size) {
            return new ItemName[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
