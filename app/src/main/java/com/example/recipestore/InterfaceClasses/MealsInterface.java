package com.example.recipestore.InterfaceClasses;

import com.example.recipestore.AllClasses.MealsAll;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsInterface {
    @GET("filter.php")
    Call<MealsAll> getSearchedMeal(
            @Query("c") String food
    );
}
