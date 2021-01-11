package com.example.recipestore.AllClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesAll implements Parcelable {
    @SerializedName("categories")
    private List<Categories> categoriesList;

    protected CategoriesAll(Parcel in) {
        categoriesList = in.createTypedArrayList(Categories.CREATOR);
    }

    public static final Creator<CategoriesAll> CREATOR = new Creator<CategoriesAll>() {
        @Override
        public CategoriesAll createFromParcel(Parcel in) {
            return new CategoriesAll(in);
        }

        @Override
        public CategoriesAll[] newArray(int size) {
            return new CategoriesAll[size];
        }
    };

    public List<Categories> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Categories> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(categoriesList);
    }
}
