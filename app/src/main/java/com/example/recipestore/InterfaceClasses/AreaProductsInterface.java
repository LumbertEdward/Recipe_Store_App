package com.example.recipestore.InterfaceClasses;

import com.example.recipestore.AllClasses.AreasAll;
import com.example.recipestore.AllClasses.MealsAll;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AreaProductsInterface {
    @GET("filter.php")
    Call<MealsAll> getAllAreas(
            @Query("a") String list
    );
}
