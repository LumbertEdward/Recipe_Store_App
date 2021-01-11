package com.example.recipestore.AllClasses;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AreasAll implements Parcelable {
    @SerializedName("meals")
    private List<Areas> areasList;

    protected AreasAll(Parcel in) {
        areasList = in.createTypedArrayList(Areas.CREATOR);
    }

    public static final Creator<AreasAll> CREATOR = new Creator<AreasAll>() {
        @Override
        public AreasAll createFromParcel(Parcel in) {
            return new AreasAll(in);
        }

        @Override
        public AreasAll[] newArray(int size) {
            return new AreasAll[size];
        }
    };

    public List<Areas> getAreasList() {
        return areasList;
    }

    public void setAreasList(List<Areas> areasList) {
        this.areasList = areasList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(areasList);
    }
}
