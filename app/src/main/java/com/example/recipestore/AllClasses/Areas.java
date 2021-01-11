package com.example.recipestore.AllClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Areas implements Parcelable {
    @SerializedName("strArea")
    private String strArea;

    public Areas(String strArea) {
        this.strArea = strArea;
    }

    protected Areas(Parcel in) {
        strArea = in.readString();
    }

    public static final Creator<Areas> CREATOR = new Creator<Areas>() {
        @Override
        public Areas createFromParcel(Parcel in) {
            return new Areas(in);
        }

        @Override
        public Areas[] newArray(int size) {
            return new Areas[size];
        }
    };

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strArea);
    }
}
