package com.example.recipestore.AllClasses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Meals implements Parcelable {
    @SerializedName("strMeal")
    private String strMeal;
    @SerializedName("strMealThumb")
    private String strMealThumb;
    @SerializedName("idMeal")
    private String idMeal;

    public Meals(String strMeal, String strMealThumb, String idMeal) {
        this.strMeal = strMeal;
        this.strMealThumb = strMealThumb;
        this.idMeal = idMeal;
    }

    protected Meals(Parcel in) {
        strMeal = in.readString();
        strMealThumb = in.readString();
        idMeal = in.readString();
    }

    public static final Creator<Meals> CREATOR = new Creator<Meals>() {
        @Override
        public Meals createFromParcel(Parcel in) {
            return new Meals(in);
        }

        @Override
        public Meals[] newArray(int size) {
            return new Meals[size];
        }
    };

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(strMeal);
        dest.writeString(strMealThumb);
        dest.writeString(idMeal);
    }
}
