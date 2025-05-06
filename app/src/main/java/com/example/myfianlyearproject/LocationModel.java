package com.example.myfianlyearproject;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationModel implements Parcelable {

    private String name;
    private boolean isSelected;

    public LocationModel(String name) {
        this.name = name;
        this.isSelected = false; // Default not selected
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    // Parcelable implementation
    protected LocationModel(Parcel in) {
        name = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<LocationModel> CREATOR = new Parcelable.Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
