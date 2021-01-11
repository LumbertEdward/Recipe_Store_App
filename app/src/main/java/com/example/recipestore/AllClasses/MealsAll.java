package com.example.recipestore.AllClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsAll implements Parcelable {
    @SerializedName("meals")
    private List<Meals> mealsList;

    protected MealsAll(Parcel in) {
        mealsList = in.createTypedArrayList(Meals.CREATOR);
    }

    public static final Creator<MealsAll> CREATOR = new Creator<MealsAll>() {
        @Override
        public MealsAll createFromParcel(Parcel in) {
            return new MealsAll(in);
        }

        @Override
        public MealsAll[] newArray(int size) {
            return new MealsAll[size];
        }
    };

    public List<Meals> getMealsList() {
        return mealsList;
    }

    public void setMealsList(List<Meals> mealsList) {
        this.mealsList = mealsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mealsList);
    }
}
