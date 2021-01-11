package com.example.recipestore.InterfaceClasses;

import com.example.recipestore.AllClasses.CategoriesAll;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriesInterface {
    @GET("categories.php")
    Call<CategoriesAll> getAllCategories();
}
